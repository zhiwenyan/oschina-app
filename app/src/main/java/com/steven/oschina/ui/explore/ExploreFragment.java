package com.steven.oschina.ui.explore;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.ui.search.SearchActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class ExploreFragment extends BaseFragment {
    @BindView(R.id.viewStatusBar)
    View mViewStatusBar;
    @BindView(R.id.btn_search)
    ImageView mBtnSearch;
    @BindView(R.id.rl_zb)
    LinearLayout mRlZb;
    @BindView(R.id.rl_soft)
    LinearLayout mRlSoft;
    @BindView(R.id.rl_git)
    LinearLayout mRlGit;
    @BindView(R.id.rl_gits)
    LinearLayout mRlGits;
    @BindView(R.id.rl_scan)
    LinearLayout mRlScan;
    @BindView(R.id.rl_shake)
    LinearLayout mRlShake;
    @BindView(R.id.iv_has_location)
    ImageView mIvHasLocation;
    @BindView(R.id.layout_nearby)
    LinearLayout mLayoutNearby;
    @BindView(R.id.layout_events)
    LinearLayout mLayoutEvents;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.btn_search, R.id.rl_zb, R.id.rl_soft, R.id.rl_git, R.id.rl_gits, R.id.rl_scan, R.id.rl_shake})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                startActivity(SearchActivity.class);
                break;
            case R.id.rl_zb:
                break;
            case R.id.rl_soft:
                break;
            case R.id.rl_git:
                break;
            case R.id.rl_gits:
                break;
            case R.id.rl_scan:
                break;
            case R.id.rl_shake:
                break;
        }
    }
}
