package com.steven.oschina.ui.synthetical;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.greenfarm.client.base_library.banner.BannerAdapter;
import com.greenfarm.client.base_library.banner.BannerView;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐的文章
 */
public class ArticleFragment extends BaseRecyclerFragment<Article> {
    private List<Article> mArticles;
    private BannerView mBannerView;
    private static final int CATALOG = 1;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
        super.initData();
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

        });
    }
}
