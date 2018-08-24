package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.utils.TweetParser;

import java.util.List;

/**
 * Description:
 * Data：8/20/2018-2:56 PM
 *
 * @author yanzhiwen
 */
public class TopicAdapter extends CommonRecyclerAdapter<Topic> {
    private Context mContext;
    private static final int[] images = {
            R.mipmap.bg_topic_1, R.mipmap.bg_topic_2, R.mipmap.bg_topic_3,
            R.mipmap.bg_topic_4, R.mipmap.bg_topic_5
    };

    public TopicAdapter(Context context, List<Topic> data, int layoutId) {
        super(context, data, layoutId);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Topic item) {
        Tweet[] tweets = item.getTweets();
        ImageView iv_wallpaper = holder.getView(R.id.iv_wallpaper);
        iv_wallpaper.setImageResource(images[holder.getAdapterPosition() % 5]);
        holder.setText(R.id.tv_topic_title, "#" + item.getTitle() + "#")
                .setText(R.id.tv_topic_content_1, TweetParser.getInstance().parse(mContext, tweets[0].getContent()))
                .setText(R.id.tv_topic_content_2, TweetParser.getInstance().parse(mContext, tweets[1].getContent()))
                .setText(R.id.tv_topic_content_3, TweetParser.getInstance().parse(mContext, tweets[2].getContent()))
                .setImageByUrl(R.id.iv_avatar_topic_1, new ViewHolder.HolderImageLoader(tweets[0].getAuthor().getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                }).setImageByUrl(R.id.iv_avatar_topic_2, new ViewHolder.HolderImageLoader(tweets[1].getAuthor().getPortrait()) {
            @Override
            public void displayImage(Context context, ImageView imageView, String imagePath) {
                ImageLoader.load(context, imageView, imagePath);

            }
        }).setImageByUrl(R.id.iv_avatar_topic_3, new ViewHolder.HolderImageLoader(tweets[2].getAuthor().getPortrait()) {
            @Override
            public void displayImage(Context context, ImageView imageView, String imagePath) {
                ImageLoader.load(context, imageView, imagePath);

            }
        }).setText(R.id.tv_count, String.format("共有%s参与", item.getJoinCount() + ""));
    }
}
