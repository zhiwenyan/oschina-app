package com.steven.oschina.ui.synthetical.article;


import com.oschina.client.recyclerview.adapter.MultiTypeSupport;
import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 英文类型的文章
 */
public class EnglishArticleFragment extends BaseRefreshFragment<Article, ArticleListViewModel> {
    //英文类型的文章
    private static final int TYPE_ENGLISH = 8000;
    private List<Article> mArticles = new ArrayList<>();
    private static final String CACHE_NAME = "english_article_list";

    public static EnglishArticleFragment newInstance() {
        return new EnglishArticleFragment();
    }

    @Override
    public void initData() {
        mAdapter = new ArticleAdapter(mContext, mArticles, mMultiTypeSupport);
        mSwipeRefreshRv.setAdapter(mAdapter);
        super.initData();
    }

    @Override
    public void readCacheData() {
        List<Article> articles = CacheManager.readListJson(mContext, CACHE_NAME, Article.class);
        if (articles != null) {
            showArticleList(articles);
        }
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        Map<String, Object> params = new HashMap<>();
        params.put("ident", OSCSharedPreference.getInstance().getDeviceUUID());
        params.put("type", TYPE_ENGLISH);
        params.put("pageToken", mNextPageToken);

        if (mObserver == null) {
            mObserver = result -> {
                mNextPageToken = result.getNextPageToken();
                showArticleList(result.getItems());
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, result.getItems());
            };
        }
        mViewModel.getEnglishArticles(params).observe(this, mObserver);

    }

    private void showArticleList(List<Article> articles) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mArticles.clear();
            mSwipeRefreshRv.setRefreshing(false);
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
}
