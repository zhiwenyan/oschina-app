package com.steven.oschina.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenfarm.client.base_library.utils.ToastUtil;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Data：7/4/2018-10:01 AM
 *
 * @author yanzhiwen
 */
public abstract class BaseFragment extends Fragment {
    private View mRootView;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        initBundle(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (mRootView != null) {
            ViewGroup parent = ( ViewGroup ) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        this.mUnbinder = ButterKnife.bind(this, mRootView);
        this.initData();
        return mRootView;
    }

    public void initBundle(Bundle bundle) {

    }

    public abstract int getLayoutId();

    public abstract void initData();


    public void showToast(String message) {
        ToastUtil.toast(Objects.requireNonNull(this.getActivity()).getApplicationContext(), message);
    }

    public void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this.getActivity(), activity);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
