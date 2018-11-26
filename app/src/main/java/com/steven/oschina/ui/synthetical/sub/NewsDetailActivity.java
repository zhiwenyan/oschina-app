package com.steven.oschina.ui.synthetical.sub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.SubBean;

/**
 * Description:
 * Data：7/26/2018-1:21 PM
 *
 * @author yanzhiwen
 */
public  class NewsDetailActivity extends DetailActivity {

    public static void show(Context context, SubBean bean) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, int type) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(type);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_NEWS);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, boolean isFav) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_NEWS);
        bean.setId(id);
        bean.setFavorite(isFav);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public BaseSubDetailFragment getDetailFragment() {
        return  NewsDetailFragment.newInstance(mSubBean);
    }
}
