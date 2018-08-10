package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.tweet.TweetLike;

import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:38 PM
 *
 * @author yanzhiwen
 */
public class TweetLikeAdapter extends CommonRecyclerAdapter<TweetLike> {
    public TweetLikeAdapter(Context context, List<TweetLike> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, TweetLike item) {
        Author author = item.getAuthor();
        holder.setText(R.id.tv_name, TextUtils.isEmpty(author.getName()) ? "匿名用户" : author.getName())
                .setImageByUrl(R.id.iv_avatar, new ViewHolder.HolderImageLoader(author.getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                });
    }
}
