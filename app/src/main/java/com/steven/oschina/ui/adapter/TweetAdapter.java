package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tweetContentTv=holder.getView(R.id.tv_tweet_content);
        holder.setText(R.id.tv_tweet_name, TextUtils.isEmpty(item.getAuthor().getName()) ? "匿名用户" : item.getAuthor().getName())
                .setText(R.id.tv_tweet_time, StringUtils.formatSomeAgo(item.getPubDate()))
                .setText(R.id.tv_tweet_content, item.getContent())
                .setImageResource(R.id.iv_like_state, item.isLiked() ? R.mipmap.ic_thumbup_actived : R.mipmap.ic_thumb_normal)
                .setImageByUrl(R.id.iv_tweet_face, new ViewHolder.HolderImageLoader(item.getAuthor().getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                });
        //用户的一些行为 转发 点赞之类的
        if (item.getStatistics() != null) {
            holder.setText(R.id.tv_tweet_comment_count, 0 == item.getStatistics().getComment() ? "评论" : item.getStatistics().getComment() + "")
                    .setText(R.id.tv_tweet_like_count, 0 == item.getStatistics().getLike() ? "赞" : item.getStatistics().getLike() + "")
                    .setText(R.id.tv_dispatch_count, item.getStatistics().getTransmit() > 0
                            ? item.getStatistics().getTransmit() + "" : "转发");
        }
        if (!TextUtils.isEmpty(item.getContent())) {
            String content = item.getContent().replaceAll("[\n\\s]+", " ");
            //holder.mViewContent.setText(AssimilateUtils.assimilate(mContext, content));
//            tweetContentTv.setText(TweetParser.getInstance().parse(mContext, content));
//            tweetContentTv.setMovementMethod(LinkMovementMethod.getInstance());
//            tweetContentTv.setFocusable(false);
//            tweetContentTv.setDispatchToParent(true);
//            tweetContentTv.setLongClickable(false);
        }
    }
}