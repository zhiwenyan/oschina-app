package com.steven.oschina.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oschina.client.base_library.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Data：7/4/2018-10:01 AM
 *
 * @author yanzhiwen
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        initBundle(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //针对fragment多次调用onCreateView的问题
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
        ToastUtil.toast(mContext.getApplicationContext(), message);
    }

    public void startActivity(Class<?> activity) {
        Intent intent = new Intent(this.getActivity(), activity);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
