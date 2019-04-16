package com.steven.oschina.ui.synthetical.article;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.oschina.client.base_library.banner.BannerView;
import com.oschina.client.recyclerview.adapter.MultiTypeSupport;
import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
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
public class RecommendArticleFragment extends BaseRefreshFragment<Article, ArticleListViewModel> implements BannerView.PageTransformerListener {
    private List<Article> mArticles = new ArrayList<>();
    private BannerView mBannerView;
    private static final int CATALOG = 1;
    private static final String BANNER_CACHE_NAME = "article_banner";
    private static final String CACHE_NAME = "article_list";
    private BannerViewModel mBannerViewModel;
    protected Observer<PageBean<Article>> mObserver;
    private static final float MIN_SCALE = 0.8f;

    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void initData() {
        mBannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);
        mAdapter = new ArticleAdapter(mContext, mArticles, mMultiTypeSupport);
        mSwipeRefreshRv.setAdapter(mAdapter);
        requestBanner();
        mBannerView.setPageTransformerListener(this);
        mBannerView.setBottomColor(ContextCompat.getColor(getContext(),R.color.light_grey_500));
        super.initData();
    }

    @Override
    public void readCacheData() {
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
            mBannerView = (BannerView) LayoutInflater.from(mContext)
                    .inflate(R.layout.news_banner_layout, mSwipeRefreshRv, false);
            mSwipeRefreshRv.addHeaderView(mBannerView);
            mBannerViewModel.getBanner(CATALOG).observe(this, banners -> {
                assert banners != null;
                mBannerViewModel.showBanner(mContext, mBannerView, banners.getItems());
            });
        }
    }


    @Override
    public void onRequestData() {
        super.onRequestData();
        if (mObserver == null) {
            mObserver = result -> {
                assert result != null;
                mNextPageToken = result.getNextPageToken();
                showArticleList(result.getItems());
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, result.getItems());
            };
        }
        mViewModel.getArticles(OSCSharedPreference.getInstance().getDeviceUUID(), mNextPageToken)
                .observe(this, mObserver);

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

    @Override
    public void transformPage(View page, float position) {
        if (position >= -1.0f && position <= 1.0f) {
            float scale = 1.0f - Math.abs(position) * (1 - MIN_SCALE);
            page.setScaleX(scale);
            page.setScaleY(scale);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
