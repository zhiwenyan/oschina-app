package com.steven.oschina.ui.synthetical.article;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.oschina.client.recyclerview.adapter.MultiTypeSupport;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.dialog.DialogHelper;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleViewModel;
import com.steven.oschina.utils.DataFormat;
import com.steven.oschina.utils.StringUtils;

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
public class RecommendArticleDetailFragment extends BaseRefreshFragment<Article, ArticleListViewModel> implements View.OnClickListener {
    private View mHeaderView;
    private Article mArticle;
    private String mIdent;
    private String mKey;
    private List<Article> mArticles = new ArrayList<>();
    private ArticleViewModel mArticleViewModel;
    protected Observer<PageBean<Article>> mObserver;

    public static RecommendArticleDetailFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        RecommendArticleDetailFragment fragment = new RecommendArticleDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mArticle = (Article) bundle.getSerializable("article");
        mIdent = OSCSharedPreference.getInstance().getDeviceUUID();
        mKey = mArticle.getKey();
    }

    @Override
    public void initView() {
        super.initView();
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_header, null);
        mHeaderView.findViewById(R.id.btn_read_all).setOnClickListener(this);
        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
    }

    @Override
    public void initData() {
        mAdapter = new ArticleAdapter(mContext, mArticles, mMultiTypeSupport);
        mSwipeRefreshRv.setAdapter(mAdapter);
        mSwipeRefreshRv.addHeaderView(mHeaderView);
        super.initData();
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        mArticleViewModel.getArticle(mIdent, mKey).observe(this, this::showArticle);
        Map<String, Object> params = new HashMap<>();
        params.put("key", mKey);
        params.put("ident", mIdent);
        if (!TextUtils.isEmpty(mNextPageToken)) {
            params.put("pageToken", mNextPageToken);
        }
        if (mObserver == null) {
            mObserver = result -> {
                assert result != null;
                mNextPageToken = result.getNextPageToken();
                showArticleList(result.getItems());
            };
        }
        mViewModel.getEnglishArticles(params).observe(this, mObserver);
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
        if (mSwipeRefreshRv.isRefreshing()) {
            mSwipeRefreshRv.setRefreshing(false);
            mArticles.clear();
        }
        if (articles.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mArticles.addAll(articles);
        mSwipeRefreshRv.setLoading(false);
        mAdapter.notifyDataSetChanged();

    }

    private MultiTypeSupport<Article> mMultiTypeSupport = (article, position) -> {
        String[] images = article.getImgs();
        if (images == null || images.length == 0) {
            return R.layout.item_article_not_img;
        }
        if (images.length < 3)
            return R.layout.item_article_one_img;
        return R.layout.item_article_three_img;

    };


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
                    (dialog, which) -> {
                        ArticleWebActivity.show(mContext, mArticle);
                        if (checkBox.isChecked()) {
                            OSCSharedPreference.getInstance().putFirstOpenUrl();
                        }
                    }).show();
        } else {
            ArticleWebActivity.show(mContext, mArticle);
        }
    }

}