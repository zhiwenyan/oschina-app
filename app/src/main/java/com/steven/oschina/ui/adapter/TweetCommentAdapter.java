package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.tweet.TweetComment;
import com.steven.oschina.utils.StringUtils;

import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:38 PM
 *
 * @author yanzhiwen
 */
public class TweetCommentAdapter extends CommonRecyclerAdapter<TweetComment> {
    public TweetCommentAdapter(Context context, List<TweetComment> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, TweetComment item) {
        Author author = item.getAuthor();
        holder.setText(R.id.tv_name, TextUtils.isEmpty(author.getName()) ? "匿名用户" : author.getName())
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_pub_date, StringUtils.formatSomeAgo(item.getPubDate()))
                .setImageByUrl(R.id.iv_avatar, new ViewHolder.HolderImageLoader(author.getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                });
    }
}
