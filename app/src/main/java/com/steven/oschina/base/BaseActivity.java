package com.steven.oschina.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.greenfarm.client.base_library.utils.AppManagerUtil;
import com.greenfarm.client.base_library.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Data：7/4/2018-9:57 AM
 *
 * @author yanzhiwen
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManagerUtil.instance().attachActivity(this);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initData();
    }

    protected abstract int getLayoutId();


    protected abstract void initData();


    protected void showToast(String message) {
        ToastUtil.toast(this.getApplicationContext(), message);
    }

    protected void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        AppManagerUtil.instance().detachActivity(this);
        super.onDestroy();

    }
}
