package com.steven.oschina.header;

import android.content.Context;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.bean.banner.Banner;

import java.util.List;

/**
 * Description:
 * Data：7/26/2018-4:14 PM
 *
 * @author yanzhiwen
 */
public class NewsHeaderView extends HeaderView {

    public NewsHeaderView(Context context, String api, String cacheName, int catalog) {
        super(context, api, cacheName, catalog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.news_banner_layout;
    }

    @Override
    public void showBanners(List<Banner> banners) {
        //super.showBanners(banners);
    }

    @Override
    public CommonRecyclerAdapter<Banner> getAdapter() {
        return null;
    }
}
