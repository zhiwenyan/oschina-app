package com.steven.oschina.ui.adapter;

import android.content.Context;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Article;

import java.util.List;

/**
 * Description:
 * Data：7/10/2018-5:13 PM
 *
 * @author yanzhiwen
 */
public class SearchAdapter extends CommonRecyclerAdapter<Article> {
    public SearchAdapter(Context context, List<Article> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Article item) {
        holder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getAuthorName() + "  " + item.getPubDate())
                .setText(R.id.tv_description, item.getDesc())
                .setText(R.id.tv_info_comment, item.getViewCount() + "");
    }
}
