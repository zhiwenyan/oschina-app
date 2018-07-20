package com.steven.oschina.emoji;

import android.content.Context;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.GlideApp;
import com.steven.oschina.R;

import java.util.List;

/**
 * Description:
 * Data：7/19/2018-10:50 AM
 *
 * @author yanzhiwen
 */
public class FaceAdapter extends CommonRecyclerAdapter<Emojicon> {
    private Context mContext;

    public FaceAdapter(Context context, List<Emojicon> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Emojicon item) {
        ImageView mImage = holder.getView(R.id.iv_face);
        GlideApp.with(mContext)
                .load(item.getResId())
                .into(mImage);
    }
}
