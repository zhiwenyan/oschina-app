package com.steven.oschina.ui.synthetical.sub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.SubBean;

public class QuestionDetailActivity extends DetailActivity {


    public static void show(Context context, SubBean bean) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_QUESTION);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, boolean isFav) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_QUESTION);
        bean.setId(id);
        bean.setFavorite(isFav);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public BaseSubDetailFragment getDetailFragment() {
        return QuestionDetailFragment.newInstance(mSubBean);
    }

}
