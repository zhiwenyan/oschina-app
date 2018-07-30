package com.steven.oschina.ui.comment;

import android.app.Activity;
import android.content.Intent;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;

public class CommentsActivity extends BaseActivity {

    public static void show(Activity activity, long id, int type, int order, String title) {
        Intent intent = new Intent(activity, CommentsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("order", order);
        intent.putExtra("title", title);
        activity.startActivityForResult(intent, 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void initData() {

    }
}
