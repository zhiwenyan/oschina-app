package com.steven.oschina.ui.user;


import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;


public class UserInfoFragment extends BaseFragment {
    public static UserInfoFragment newInstance() {

        Bundle args = new Bundle();

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initData() {

    }
}
