package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
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
public class NewsSubAdapter extends CommonRecyclerAdapter<SubBean> {
    private Context mContext;

    public NewsSubAdapter(Context context, List<SubBean> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }


    @Override
    public void convert(ViewHolder holder, SubBean item) {
        TextView titleTv = holder.getView(R.id.tv_title);
        if (StringUtils.isToday(item.getPubDate()) && item.getType() != 2 && item.getType() != 7) {
            String text = "[icon] " + item.getTitle();
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_label_today);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            SpannableString spannable = new SpannableString(text);
            spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            titleTv.setText(spannable);
        } else {
            titleTv.setText(item.getTitle());
        }
        holder.setText(R.id.tv_description, item.getBody())
                .setText(R.id.tv_time, "@" + item.getAuthor().getName() + " "
                        + StringUtils.formatSomeDay(item.getPubDate()))
                .setText(R.id.tv_info_comment, item.getStatistics().getComment() + "");

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
