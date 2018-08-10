package com.steven.oschina.ui.synthetical.article;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.oschina.client.base_library.banner.BannerAdapter;
import com.oschina.client.base_library.banner.BannerView;
import com.steven.oschina.CacheManager;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.utils.TypeFormat;
import com.steven.oschina.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐的文章
 */
public class ArticleFragment extends BaseRecyclerFragment<Article> {
    private List<Article> mArticles;
    private BannerView mBannerView;
    private static final int CATALOG = 1;
    private static final String BANNER_CACHE_NAME = "article_banner";
    private static final String CACHE_NAME = "article_list";

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
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
            showBanner(banners);
        }
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

    private void requestBanner() {
        if (mBannerView == null) {
            mBannerView = ( BannerView ) LayoutInflater.from(mContext)
                    .inflate(R.layout.news_banner_layout, mSwipeRefreshRv, false);
            mSwipeRefreshRv.addHeaderView(mBannerView);
            HttpUtils.get(RetrofitClient.getServiceApi().getBanner(CATALOG), new HttpCallback<Banner>() {
                @Override
                public void onSuccess(List<Banner> banners, String nextPageToken) {
                    super.onSuccess(banners, nextPageToken);
                    CacheManager.saveToJson(OSCApplication.getInstance(), BANNER_CACHE_NAME, banners);
                    showBanner(banners);

                }
            });
        }
    }

    private void showBanner(List<Banner> banners) {
        mBannerView.setBannerTitle(banners.get(0).getName());
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView bannerIv;
                if (convertView != null) {
                    bannerIv = ( ImageView ) convertView;
                    bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    bannerIv = new ImageView(getActivity());
                }
                ImageLoader.load(getActivity(), bannerIv, banners.get(position).getImg());
                return bannerIv;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return banners.get(position).getName();
            }
        });
        mBannerView.startLoop();
        mBannerView.setOnBannerItemClickListener(position -> {
            Banner banner = banners.get(position);
            if (banner != null) {
                int type = banner.getType();
                long id = banner.getId();
                if (type == News.TYPE_HREF) {
                    WebActivity.show(getContext(), banner.getHref());
                } else {
                    UIHelper.showDetail(getContext(), type, id, banner.getHref());
                }
            }
        });
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getArticles(OSCSharedPreference.getInstance().getDeviceUUID(),
                nextPageToken), new HttpCallback<Article>() {
            @Override
            public void onSuccess(List<Article> articles, String nextPageToken) {
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (articles.size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showArticleList(articles);
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, articles);
            }
        });
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
            requestBanner();
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
}
