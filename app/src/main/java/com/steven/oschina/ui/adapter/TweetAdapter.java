package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.utils.StringUtils;

import java.util.List;

/**
 * Description:
 * Data：2018/7/7
 * Author:Steven
 */

public class TweetAdapter extends CommonRecyclerAdapter<Tweet> {
    public TweetAdapter(Context context, List<Tweet> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Tweet item) {
        holder.setText(R.id.tv_tweet_name, item.getAuthor().getName())
                .setText(R.id.tv_tweet_time, StringUtils.formatSomeAgo(item.getPubDate()))
                .setText(R.id.tv_tweet_content, item.getContent())
                .setText(R.id.tv_dispatch_count, item.getStatistics().getTransmit() > 0
                        ? item.getStatistics().getTransmit() + "" : "转发")
                .setText(R.id.tv_tweet_comment_count, item.getStatistics().getComment() + "")
                .setText(R.id.tv_tweet_like_count, item.getStatistics().getLike() + "")
                .setImageByUrl(R.id.iv_tweet_face, new ViewHolder.HolderImageLoader(item.getAuthor().getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                });
    }
}