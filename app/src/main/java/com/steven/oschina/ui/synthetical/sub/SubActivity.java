package com.steven.oschina.ui.synthetical.sub;

import android.content.Context;
import android.content.Intent;

import com.greenfarm.client.base_library.utils.FragmentManagerHelper;
import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.SubTab;

public class SubActivity extends BaseActivity {
    public static void show(Context context, SubTab bean) {
        if (bean == null)
            return;
        Intent intent = new Intent(context, SubActivity.class);
        intent.putExtra("sub_tab", bean);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        setDarkToolBar();
        SubTab tab = ( SubTab ) getIntent().getSerializableExtra("sub_tab");
        setTitle(tab.getName());
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.fl_content);
        fragmentManagerHelper.add(SubFragment.newInstance(tab));
    }

}
