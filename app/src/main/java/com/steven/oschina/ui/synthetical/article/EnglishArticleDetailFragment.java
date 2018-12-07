package com.steven.oschina.ui.synthetical.article;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Translate;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.OWebView;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleViewModel;
import com.steven.oschina.utils.DataFormat;
import com.steven.oschina.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnglishArticleDetailFragment extends BaseRecyclerFragment1<Article, ArticleListViewModel> {
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

    private ArticleViewModel mArticleViewModel;

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
        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        super.initData();
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        if (!TextUtils.isEmpty(mArticle.getTitleTranslated())) {
            isEnglish = true;
            getTranslateArticle();
        } else {
            isEnglish = false;
            getEnglishArticle();
        }
        getArticleRecommends();

    }


    private void getTranslateArticle() {
        mArticleViewModel.getTranslateArticle(mKey, Article.TYPE_ENGLISH).observe(this, article -> {
            if (article != null) {
                showArticle(article);
                parseTranslate(article);

            }
        });
    }

    private void getEnglishArticle() {
        mArticleViewModel.getEnglishArticle(mIdent, mKey, Article.TYPE_ENGLISH).observe(this, article -> {
            if (article != null) {
                showArticle(article);
                showArticleEnglish(article);
            }
        });
    }

    private void getArticleRecommends() {
        Map<String, Object> params = new HashMap<>();
        params.put("key", mKey);
        params.put("ident", mIdent);
        if (!TextUtils.isEmpty(mNextPageToken)) {
            params.put("pageToken", mNextPageToken);
        }
        //类似的文章推荐
        mViewModel.getArticleRecommends(params).observe(this, result -> {
            if (result != null) {
                mNextPageToken = result.getNextPageToken();
                if (result.getItems().size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showArticleList(result.getItems());
            }
        });
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
        if (mSwipeRefreshRv.isRefreshing()) {
            mSwipeRefreshRv.setRefreshing(false);
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

    private void showArticleEnglish(Article article) {
        mOWebView.loadDetailDataAsync(article.getContent(), null);
        mTextCount.setText(StringUtils.formatTextCount(article.getWordCount()));
        mTextTime.setText(StringUtils.formatTime(article.getReadTime()));
    }
}
