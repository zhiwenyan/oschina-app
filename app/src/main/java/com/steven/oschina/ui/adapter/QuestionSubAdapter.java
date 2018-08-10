package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.utils.StringUtils;

import java.util.List;

/**
 * Description:
 * Data：7/6/2018-9:59 AM
 *
 * @author yanzhiwen
 */
public class QuestionSubAdapter extends CommonRecyclerAdapter<SubBean> {

    public QuestionSubAdapter(Context context, List<SubBean> data, int layoutId) {
        super(context, data, layoutId);
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
    }
}
