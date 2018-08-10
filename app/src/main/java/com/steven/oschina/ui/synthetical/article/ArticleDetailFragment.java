package com.steven.oschina.ui.synthetical.article;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.utils.DataFormat;
import com.steven.oschina.dialog.DialogHelper;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.utils.TypeFormat;
import com.steven.oschina.utils.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Data：7/25/2018-1:42 PM
 *
 * @author yanzhiwen
 */
public class ArticleDetailFragment extends BaseRecyclerFragment implements View.OnClickListener {
    private View mHeaderView;
    private Article mArticle;
    private String mIdent;
    private String mKey;
    private List<Article> mArticles;

    public static ArticleDetailFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mArticle = ( Article ) bundle.getSerializable("article");
        mIdent = OSCSharedPreference.getInstance().getDeviceUUID();
        mKey = mArticle.getKey();
    }

    @Override
    public void initData() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_header, null);
        mArticles = new ArrayList<>();
        mHeaderView.findViewById(R.id.btn_read_all).setOnClickListener(this);
        super.initData();
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils._get(RetrofitClient.getServiceApi().getArticleDetail(mIdent, mKey), new HttpCallback<Article>() {
            @Override
            public void onSuccess(Article article) {
                showArticle(article);
            }
        });

        Map<String, Object> params = new HashMap<>();
        params.put("key", mKey);
        params.put("ident", mIdent);
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pageToken", nextPageToken);
        }

        HttpUtils.get(RetrofitClient.getServiceApi().getArticleRecommends(params), new HttpCallback<Article>() {
            @Override
            public void onSuccess(List<Article> result, String nextPageToken) {
                super.onSuccess(result, nextPageToken);
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (result.size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showArticleList(result);
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onRequestData("");
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        onRequestData(mNextPageToken);
    }

    private void showArticle(Article article) {
        TextView tv_title = mHeaderView.findViewById(R.id.tv_title);
        TextView tv_name = mHeaderView.findViewById(R.id.tv_name);
        TextView tv_pub_date = mHeaderView.findViewById(R.id.tv_pub_date);
        TextView tv_origin = mHeaderView.findViewById(R.id.tv_origin);
        TextView tv_detail_content = mHeaderView.findViewById(R.id.tv_detail_content);
        TextView tv_text_count = mHeaderView.findViewById(R.id.tv_text_count);
        TextView tv_text_time = mHeaderView.findViewById(R.id.tv_text_time);
        tv_title.setText(article.getTitle());
        tv_name.setText(TextUtils.isEmpty(article.getAuthorName()) ? "匿名" : article.getAuthorName());
        tv_pub_date.setText(DataFormat.parsePubDate(article.getPubDate()));
        tv_detail_content.setText(TextUtils.isEmpty(article.getDesc()) ? article.getDesc() :
                article.getDesc().replaceFirst("\\s*|\t|\n", ""));
        tv_origin.setText(article.getSource());
        if (TextUtils.isEmpty(article.getSource())) {
            tv_origin.setVisibility(View.GONE);
        }
        tv_text_count.setText(StringUtils.formatTextCount(article.getWordCount()));
        tv_text_time.setText(StringUtils.formatTime(article.getReadTime()));
    }

    private void showArticleList(List<Article> articles) {
        if (mRefreshing) {
            mArticles.clear();
        }
        mArticles.addAll(articles);
        if (mAdapter == null) {
            mAdapter = new ArticleAdapter(this.getActivity(), mArticles, (article, position) -> {
                String[] images = article.getImgs();
                if (images == null || images.length == 0) {
                    return R.layout.item_article_not_img;
                }
                if (images.length < 3)
                    return R.layout.item_article_one_img;
                return R.layout.item_article_three_img;
            });
            mSwipeRefreshRv.setAdapter(mAdapter);
            mSwipeRefreshRv.addHeaderView(mHeaderView);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
            Article top = mArticles.get(position);
            if (!TDevice.hasWebView(mContext))
                return;
            if (top.getType() == 0) {
                if (TypeFormat.isGit(top)) {
                    WebActivity.show(mContext, TypeFormat.formatUrl(top));
                } else {
                    ArticleDetailActivity.show(mContext, top);
                }
            } else {
                int type = top.getType();
                long id = top.getOscId();
                switch (type) {
                    case News.TYPE_SOFTWARE:
                        //   SoftwareDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_QUESTION:
                        QuestionDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_BLOG:
                        BlogDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_TRANSLATE:
                        NewsDetailActivity.show(mContext, id, News.TYPE_TRANSLATE);
                        break;
                    case News.TYPE_EVENT:
                        //  EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        NewsDetailActivity.show(mContext, id);
                        break;
                    case Article.TYPE_ENGLISH:
                        EnglishArticleDetailActivity.show(mContext, top);
                        break;
                    default:
                        UIHelper.showUrlRedirect(mContext, top.getUrl());
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (OSCSharedPreference.getInstance().isFirstOpenUrl()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_liability, null);
            final CheckBox checkBox = view.findViewById(R.id.cb_url);
            DialogHelper.getConfirmDialog(mContext,
                    "温馨提醒",
                    view,
                    "继续访问",
                    "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArticleWebActivity.show(mContext, mArticle);
                            if (checkBox.isChecked()) {
                                OSCSharedPreference.getInstance().putFirstOpenUrl();
                            }
                        }
                    }).show();
        } else {
            ArticleWebActivity.show(mContext, mArticle);
        }
    }

}