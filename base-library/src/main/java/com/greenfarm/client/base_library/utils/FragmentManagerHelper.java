package com.greenfarm.client.base_library.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by zhiwenyan on 6/2/17
 * <p>
 * Fragment管理类  统一管理Fragment
 */

public class FragmentManagerHelper {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;

    /**
     * @param fragmentManager
     * @param containerViewId
     */
    public FragmentManagerHelper(FragmentManager fragmentManager, int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
    }

    /**
     * 添加Fragment的方法
     *
     * @param fragment
     */
    public void add(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            switchFragment(fragment);
        } else {
            fragmentTransaction.add(mContainerViewId, fragment);
            fragmentTransaction.commit();
        }
    }


    /**
     * 切换显示的Fragment
     *
     * @param fragment
     */
    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        List<Fragment> childFragments = mFragmentManager.getFragments();
        //隐藏所有的Fragment
        for (Fragment childFragment : childFragments) {
            if (childFragment != null) {
                fragmentTransaction.hide(childFragment);
            }
        }
        //如果容器没有就去添加，否则我们就去显示，显示之前把所有已经添加的不显示
        if (!childFragments.contains(fragment)) {
            fragmentTransaction.add(mContainerViewId, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

}
