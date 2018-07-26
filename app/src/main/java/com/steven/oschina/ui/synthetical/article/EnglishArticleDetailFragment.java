package com.steven.oschina.ui.synthetical.article;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.Translate;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.OWebView;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.utils.DataFormat;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.utils.TypeFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnglishArticleDetailFragment extends BaseRecyclerFragment {
    private View mHeaderView;
    private Article mArticle;
    private String mIdent;
    private String mKey;
    private List<Article> mArticles;
    private OWebView mOWebView;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_pub_date;
    private TextView tv_origin;
    private TextView mTextCount;
    private TextView mTextTime;
    boolean isEnglish;

    public static EnglishArticleDetailFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        EnglishArticleDetailFragment fragment = new EnglishArticleDetailFragment();
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
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.layout_english_article_detail_header, null);
        mOWebView = mHeaderView.findViewById(R.id.webView);
        tv_title = mHeaderView.findViewById(R.id.tv_title);
        tv_name = mHeaderView.findViewById(R.id.tv_name);
        tv_pub_date = mHeaderView.findViewById(R.id.tv_pub_date);
        tv_origin = mHeaderView.findViewById(R.id.tv_origin);
        mTextCount = mHeaderView.findViewById(R.id.tv_text_count);
        mTextTime = mHeaderView.findViewById(R.id.tv_text_time);
        mArticles = new ArrayList<>();
        super.initData();
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        if (!TextUtils.isEmpty(mArticle.getTitleTranslated())) {
            isEnglish = true;
            getEnglishDetailCN();
        } else {
            isEnglish = false;
            getEnglishDetailEN();
        }
        getArticleRecommends(nextPageToken);

    }


    private void getEnglishDetailCN() {
        HttpUtils._get(RetrofitClient.getServiceApi().article_translate(mKey, Article.TYPE_ENGLISH), new HttpCallback<Article>() {
            @Override
            public void onSuccess(Article article) {
                showArticle(article);
                parseTranslate(article);

            }
        });
    }

    private void getEnglishDetailEN() {
        HttpUtils._get(RetrofitClient.getServiceApi().getEnglishArticleDetailEN(mIdent, mKey, Article.TYPE_ENGLISH), new HttpCallback<Article>() {
            @Override
            public void onSuccess(Article article) {
                showArticle(article);
                mOWebView.loadDetailDataAsync(article.getContent(), null);
                mTextCount.setText(StringUtils.formatTextCount(article.getWordCount()));
                mTextTime.setText(StringUtils.formatTime(article.getReadTime()));
            }
        });
    }

    private void getArticleRecommends(String nextPageToken) {
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
        mTextCount.setText(StringUtils.formatTextCount(article.getWordCount()));
        mTextTime.setText(StringUtils.formatTime(article.getReadTime()));
        tv_title.setText(article.getTitle());
        tv_name.setText(TextUtils.isEmpty(article.getAuthorName()) ? "匿名" : article.getAuthorName());
        tv_pub_date.setText(DataFormat.parsePubDate(article.getPubDate()));
        tv_origin.setText(article.getSource());
        if (TextUtils.isEmpty(article.getSource())) {
            tv_origin.setVisibility(View.GONE);
        }
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
                    //     WebActivity.show(mContext, TypeFormat.formatUrl(top));
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
                        //    NewsDetailActivity.show(mContext, id, News.TYPE_TRANSLATE);
                        break;
                    case News.TYPE_EVENT:
                        //     EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        NewsDetailActivity.show(mContext, id);
                        break;
                    case Article.TYPE_ENGLISH:
                        EnglishArticleDetailActivity.show(mContext, top);
                        break;
                    default:
                        //       UIHelper.showUrlRedirect(mContext, top.getUrl());
                        break;
                }
            }
        });
    }

    /**
     * 解析翻译结果
     */
    private void parseTranslate(Article article) {
        Type type = new TypeToken<List<Translate>>() {
        }.getType();
        List<Translate> result = new Gson().fromJson(article.getContent(), type);
        if (result == null || result.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Translate translate : result) {
            sb.append(translate.getSrc());
            sb.append("\n");
            sb.append(translate.getDest());
        }
        showTranslateSuccess(article, sb.toString());
    }

    private void showTranslateSuccess(Article article, String content) {
        if (mContext == null)
            return;
        mOWebView.loadDetailDataAsync(content);
        mTextCount.setText(StringUtils.formatTextCount(article.getWordCount()));
        mTextTime.setText(StringUtils.formatTime(article.getReadTime()));
    }
}
