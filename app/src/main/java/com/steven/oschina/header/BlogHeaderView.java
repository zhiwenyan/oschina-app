package com.steven.oschina.header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.media.Util;
import com.steven.oschina.bean.sub.SubTab;
import com.steven.oschina.ui.synthetical.sub.SubActivity;

import java.util.List;

/**
 * Description:
 * Data：7/9/2018-4:07 PM
 *
 * @author yanzhiwen
 */
@SuppressLint("ViewConstructor")
public class BlogHeaderView extends HeaderView {

    public BlogHeaderView(Context context, String api, String cacheName, int catalog) {
        super(context, api, cacheName, catalog);
        findViewById(R.id.tv_all).setOnClickListener(v -> {
            SubTab tab = new SubTab();
            tab.setName("每日一博");
            tab.setFixed(false);
            tab.setHref("https://www.oschina.net/action/apiv2/sub_list?token=1abf09a23a87442184c2f9bf9dc29e35");
            tab.setNeedLogin(false);
            tab.setSubtype(1);
            tab.setOrder(4);
            tab.setToken("1abf09a23a87442184c2f9bf9dc29e35");
            tab.setType(3);

            SubActivity.show(getContext(), tab);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.blog_header_layout;
    }
    @Override
    public CommonRecyclerAdapter<Banner> getAdapter() {
        return new BlogHeaderAdapter(getContext(), mBanners, R.layout.item_list_blog_banner);
    }

    private static class BlogHeaderAdapter extends CommonRecyclerAdapter<Banner> {

        BlogHeaderAdapter(Context context, List<Banner> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, Banner item) {
            holder.itemView.getLayoutParams().width = Util.getScreenWidth(holder.itemView.getContext()) / 5 * 3;
            holder.setText(R.id.tv_name, item.getName())
                    .setImageByUrl(R.id.iv_banner, new ViewHolder.HolderImageLoader(item.getImg()) {
                        @Override
                        public void displayImage(Context context, ImageView imageView, String imagePath) {
                            ImageLoader.load(context, imageView, imagePath);
                        }
                    });
        }
    }
}
