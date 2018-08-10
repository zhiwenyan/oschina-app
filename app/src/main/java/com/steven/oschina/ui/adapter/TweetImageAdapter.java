package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.tweet.Tweet;

import java.util.List;

/**
 * Description:
 * Data：8/3/2018-1:50 PM
 *
 * @author yanzhiwen
 */
public class TweetImageAdapter extends CommonRecyclerAdapter<Tweet.Image> {
    private Context mContext;
    RecyclerView rv;

    public TweetImageAdapter(Context context, RecyclerView rv, List<Tweet.Image> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
        this.rv = rv;
    }

    @Override
    public void convert(ViewHolder holder, Tweet.Image item) {
        ImageView iv_image = holder.getView(R.id.iv_content);
        iv_image.setTag(item.getHref());
        ImageLoader.load(mContext, rv.findViewWithTag(item.getHref()), item.getHref());
    }
}
