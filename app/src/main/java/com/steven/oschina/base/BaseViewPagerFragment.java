package com.steven.oschina.base;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.steven.oschina.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Data：7/4/2018-1:54 PM
 *
 * @author yanzhiwen
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    public TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    public ViewPager mViewPager;
    public Adapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_view_pager;
    }

    @Override
    public void initData() {
        mAdapter = new Adapter(getChildFragmentManager());
        mAdapter.setupWithFragments(getFragments(), getPageTitles());
        mViewPager.setAdapter(mAdapter);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
            setupTabView();
        }
    }

    public void setupTabView(){

    }

    public static class Adapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();
        private String[] mTitles;

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void setupWithFragments(List<Fragment> fragments, String[] titles) {
            this.mFragments.addAll(fragments);
            this.mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    }

    public abstract List<Fragment> getFragments();

    public abstract String[] getPageTitles();
}

