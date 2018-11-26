package com.steven.oschina.ui.synthetical.article;


import android.arch.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;

import com.oschina.client.base_library.banner.BannerView;
import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;
import com.steven.oschina.ui.synthetical.viewmodel.BannerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐的文章
 */
public class RecommendArticleFragment extends BaseRecyclerFragment1<Article, ArticleListViewModel> {
    private List<Article> mArticles;
    private BannerView mBannerView;
    private static final int CATALOG = 1;
    private static final String BANNER_CACHE_NAME = "article_banner";
    private static final String CACHE_NAME = "article_list";
    private BannerViewModel mBannerViewModel;

    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
        mBannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);
        super.initData();
    }

    @Override
    public void requestCacheData() {
        List<Article> articles = CacheManager.readListJson(OSCApplication.getInstance(), CACHE_NAME, Article.class);
        if (articles != null) {
            showArticleList(articles);
        }
        List<Banner> banners = CacheManager.readListJson(OSCApplication.getInstance(), BANNER_CACHE_NAME, Banner.class);
        if (banners != null) {
            mBannerViewModel.showBanner(mContext, mBannerView, banners);
        }
    }

    private void requestBanner() {
        if (mBannerView == null) {
            mBannerView = ( BannerView ) LayoutInflater.from(mContext)
                    .inflate(R.layout.news_banner_layout, mSwipeRefreshRv, false);
            mSwipeRefreshRv.addHeaderView(mBannerView);
            mBannerViewModel.getBanner(CATALOG).observe(this, banners -> {
                assert banners != null;
                mBannerViewModel.showBanner(mContext, mBannerView, banners.getItems());
            });
        }
    }


    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        mViewModel.getArticles(OSCSharedPreference.getInstance().getDeviceUUID(), nextPageToken).observe(this, result -> {
            mNextPageToken = result.getNextPageToken();
            if (result.getItems().size() == 0) {
                mSwipeRefreshRv.showLoadComplete();
                return;
            }
            showArticleList(result.getItems());
            CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, result.getItems());
        });

    }

    private void showArticleList(List<Article> articles) {
        if (mRefreshing) {
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
            requestBanner();
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }
}
