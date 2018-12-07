package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.simple.About;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.utils.TweetParser;
import com.steven.oschina.widget.TweetPicturesLayout;
import com.steven.oschina.widget.TweetTextView;

import java.util.List;

/**
 * Description:
 * Data：2018/7/7
 * Author:Steven
 */

public class TweetAdapter extends CommonRecyclerAdapter<Tweet> implements View.OnClickListener {
    private Context mContext;

    public TweetAdapter(Context context, List<Tweet> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Tweet item) {
        //动弹的图片
        TweetPicturesLayout tweetPicturesLayout = holder.getView(R.id.layout_ref_images);
        Tweet.Image[] images = item.getImages();
        tweetPicturesLayout.setImage(images);

        LinearLayout like = holder.getView(R.id.ll_like);
        like.setOnClickListener(this);
        LinearLayout dispatch = holder.getView(R.id.ll_dispatch);
        dispatch.setOnClickListener(this);
        TweetTextView tweetContentTv = holder.getView(R.id.tv_tweet_content);

        String path = item.getAuthor().getPortrait();
//        String pathTmp = path.toLowerCase();
//        if (pathTmp.contains("www.oschina.net/img/portrait".toLowerCase())
//                || pathTmp.contains("secure.gravatar.com/avatar".toLowerCase())) {
//            path = "";
//        }
        holder.setText(R.id.tv_tweet_name, TextUtils.isEmpty(item.getAuthor().getName()) ? "匿名用户" : item.getAuthor().getName())
                .setText(R.id.tv_tweet_time, StringUtils.formatSomeAgo(item.getPubDate()))
                .setText(R.id.tv_tweet_content, item.getContent())
                .setImageResource(R.id.iv_like_state, item.isLiked() ? R.mipmap.ic_thumbup_actived : R.mipmap.ic_thumb_normal)
                .setImageByUrl(R.id.iv_tweet_face, new ViewHolder.HolderImageLoader(path) {
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
            //     "content": "码云上使用osc账号授权登录，设置ssh公钥时“权限验证 当前账户密码” 是多少？
            // 死都输不对，osc密码刚刚特意修改了的
            // <a href=\"https://my.oschina.net/javayou\" class=\"referer\" target=\"_blank\">@红薯</a>

//           "content": "GitHub 在国内无法访问之后，如何自救 " +
//            "<a href='https://my.oschina.net/yuandi/blog/1863198' rel='nofollow' target='_blank'>https://my.oschina.net/yuandi/blog/1863198</a>",
            String content = item.getContent().replaceAll("[\n\\s]+", " ");
            //holder.mViewContent.setText(AssimilateUtils.assimilate(mContext, content));
            tweetContentTv.setText(TweetParser.getInstance().parse(mContext, content));
            tweetContentTv.setMovementMethod(LinkMovementMethod.getInstance());
            tweetContentTv.setFocusable(false);
            tweetContentTv.setDispatchToParent(true);
            tweetContentTv.setLongClickable(false);
        }

        LinearLayout layoutRef = holder.getView(R.id.layout_ref);
        TextView viewRefTitle = holder.getView(R.id.tv_ref_title);
        TextView viewRefContent = holder.getView(R.id.tv_ref_content);
        /* - about - */
        if (item.getAbout() != null) {
            layoutRef.setVisibility(View.VISIBLE);
            layoutRef.setTag(item);
            layoutRef.setOnClickListener(this);

            About about = item.getAbout();
            tweetPicturesLayout.setImage(about.getImages());
            if (!About.check(about)) {
                viewRefTitle.setVisibility(View.VISIBLE);
                viewRefTitle.setText("不存在或已删除的内容");
                viewRefContent.setText("抱歉，该内容不存在或已被删除");
            } else {
                if (about.getType() == ServiceApi.COMMENT_TWEET) {
                    viewRefTitle.setVisibility(View.GONE);
                    String aname = "@" + about.getTitle();
                    String cnt = about.getContent();
                    //Spannable spannable = AssimilateUtils.assimilate(mContext, cnt);
                    // TODO: 7/23/2018
                    //   Spannable spannable = TweetParser.getInstance().parse(mContext, cnt);
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(aname + ": ");
                    //          builder.append(spannable);
                    ForegroundColorSpan span = new ForegroundColorSpan(
                            mContext.getResources().getColor(R.color.day_colorPrimary));
                    builder.setSpan(span, 0, aname.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    viewRefContent.setMaxLines(Integer.MAX_VALUE);
                    viewRefContent.setText(builder);
                } else {
                    viewRefTitle.setVisibility(View.VISIBLE);
                    viewRefTitle.setText(about.getTitle());
                    viewRefContent.setMaxLines(3);
                    viewRefContent.setEllipsize(TextUtils.TruncateAt.END);
                    viewRefContent.setText(about.getContent());
                }
            }
        } else {
            layoutRef.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_like:
                break;
            case R.id.ll_dispatch:
                break;
        }
    }
}