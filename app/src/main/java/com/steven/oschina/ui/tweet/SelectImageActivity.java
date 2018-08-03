package com.steven.oschina.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.ui.SelectOptions;

public class SelectImageActivity extends BaseActivity {
    private static SelectOptions mOption;

    public static void show(Context context, SelectOptions options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_image;
    }


    @Override
    protected void initData() {
        SelectImageFragment fragment = SelectImageFragment.newInstance(mOption);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fl_content, fragment);
        trans.commit();
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }
}
