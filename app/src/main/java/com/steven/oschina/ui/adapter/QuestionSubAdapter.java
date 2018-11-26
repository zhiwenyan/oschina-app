package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.utils.UIHelper;

import java.util.List;

/**
 * Description:
 * Data：7/6/2018-9:59 AM
 *
 * @author yanzhiwen
 */
public class QuestionSubAdapter extends CommonRecyclerAdapter<SubBean> {
    private Context mContext;

    public QuestionSubAdapter(Context context, List<SubBean> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, SubBean item) {
        holder.setText(R.id.tv_question_title, item.getTitle()).
                setText(R.id.tv_question_content, item.getBody())
                .setText(R.id.tv_time, "@" + item.getAuthor().getName() + " "
                        + StringUtils.formatSomeDay(item.getPubDate()))
                .setText(R.id.tv_info_comment, item.getStatistics().getComment() + "")
                .setImageByUrl(R.id.iv_avatar, new ViewHolder.HolderImageLoader(item.getAuthor().getPortrait()) {
                    @Override
                    public void displayImage(Context context, ImageView imageView, String imagePath) {
                        ImageLoader.load(context, imageView, imagePath);
                    }
                });


        //setOnClickListener
        holder.itemView.setOnClickListener(v -> {
            switch (item.getType()) {
                case News.TYPE_SOFTWARE:
                    //SoftwareDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_QUESTION:
                    QuestionDetailActivity.show(mContext, item);
                    break;
                case News.TYPE_BLOG:
                    BlogDetailActivity.show(mContext, item);
                    break;
                case News.TYPE_TRANSLATE:
                    NewsDetailActivity.show(mContext, item);
                    break;
                case News.TYPE_EVENT:
                    //EventDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_NEWS:
                    NewsDetailActivity.show(mContext, item);
                    break;
                default:
                    UIHelper.showUrlRedirect(mContext, item.getHref());
                    break;
            }
        });
    }
}
