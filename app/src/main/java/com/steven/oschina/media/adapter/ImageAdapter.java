package com.steven.oschina.media.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.MultiTypeSupport;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.media.bean.Image;

import java.util.List;

/**
 * Description:
 * Data：8/2/2018-11:30 AM
 *
 * @author yanzhiwen
 */
public class ImageAdapter extends CommonRecyclerAdapter<Image> {
    private Context mContext;

    public ImageAdapter(Context context, List<Image> data, MultiTypeSupport<Image> multiTypeSupport) {
        super(context, data, multiTypeSupport);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Image item) {
        if (!TextUtils.isEmpty(item.getPath())) {
            View maskView = holder.getView(R.id.lay_mask);
            ImageView cb_selected = holder.getView(R.id.cb_selected);
            ImageView iv_image = holder.getView(R.id.iv_image);
            ImageLoader.load(mContext, iv_image, item.getPath());
            cb_selected.setSelected(item.isSelect());
            maskView.setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);
        }
    }
}