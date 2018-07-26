package com.steven.oschina.header;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.OnItemClickListener;
import com.steven.oschina.CacheManager;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.media.Util;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.ui.synthetical.article.WebActivity;
import com.steven.oschina.utils.UIHelper;

import java.util.List;

/**
 * Description:
 * Data：7/9/2018-4:00 PM
 *
 * @author yanzhiwen
 */
public abstract class HeaderView extends LinearLayout implements OnItemClickListener {
    private String mAPI;
    private String mCacheName;
    private CommonRecyclerAdapter<Banner> mAdapter;
    protected List<Banner> mBanners;
    private int mCatalog;
    private RecyclerView mRecyclerView;
    protected View mBannerView;

    public HeaderView(Context context, String api, String cacheName, int catalog) {
        super(context, null);
        this.mAPI = api;
        this.mCacheName = cacheName;
        this.mCatalog = catalog;
        init(context);
    }


    private void init(Context context) {
        mBannerView = LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLayoutManager());
    }

    /**
     * 请求banner
     *
     * @param catalog banner的类型
     */
    public void requestBanner(int catalog) {
        HttpUtils.get(RetrofitClient.getServiceApi().getBanner(catalog), new HttpCallback<Banner>() {
            @Override
            public void onSuccess(List<Banner> result, String nextPageToken) {
                super.onSuccess(result, nextPageToken);
                showBanners(result);
                CacheManager.saveToJson(getContext(), mCacheName, result);
            }
        });
    }

    public void showBanners(List<Banner> banners) {
        mBanners = banners;
        if (mAdapter == null) {
            mAdapter = getAdapter();
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HeaderView.this);
    }

    public abstract int getLayoutId();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void onItemClick(int position) {
        Banner banner = mBanners.get(position);
        if (banner != null) {
            int type = banner.getType();
            long id = banner.getId();
            if (type == News.TYPE_HREF) {
                WebActivity.show(getContext(), banner.getHref());
            } else {
                UIHelper.showDetail(getContext(), type, id, banner.getHref());
            }
        }
    }

    public abstract CommonRecyclerAdapter<Banner> getAdapter();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(Util.getScreenWidth(getContext()), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
