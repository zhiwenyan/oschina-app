package com.steven.oschina.ui.explore;


import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;


public class ExploreFragment extends BaseFragment {
    public static ExploreFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ExploreFragment fragment = new ExploreFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void initData() {

    }
}
