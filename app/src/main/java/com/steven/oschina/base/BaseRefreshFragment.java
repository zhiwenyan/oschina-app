package com.steven.oschina.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.oschina.client.base_library.utils.NetworkUtils;
import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.widget.SwipeRefreshRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:
 * Data：7/4/2018-3:52 PM
 *
 * @author yanzhiwen
 */
public class BaseRefreshFragment<T, V> extends BaseFragment implements SwipeRefreshRecyclerView.OnRefreshLoadListener {
    public SwipeRefreshRecyclerView mSwipeRefreshRv;
    //上拉加载下一个页面的Token
    protected String mNextPageToken = "";
    protected CommonRecyclerAdapter<T> mAdapter;
    protected V mViewModel;
    protected Observer<PageBean<T>> mObserver;
    private boolean mRefreshing = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    public void initView() {
        super.initView();
        mSwipeRefreshRv = mRootView.findViewById(R.id.swipe_refresh_recycler);
        mSwipeRefreshRv.setRefreshing(mRefreshing);
        mSwipeRefreshRv.setOnRefreshLoadListener(this);
    }

    @Override
    public void initData() {
        initViewModel();
        readCacheData();
        onRequestData();
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        if (mViewModel == null) {
            Class modelClass;
//            if (type instanceof ParameterizedType) {
//                modelClass = ( Class ) (( ParameterizedType ) type).getActualTypeArguments()[1];
//            } else {
//                //如果没有指定泛型参数，则默认使用BaseViewModel
//                modelClass = BaseViewModel.class;
//            }
            Type[] types = ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments();
            modelClass = (Class) types[1];
            mViewModel = (V) createViewModel(this, modelClass);

        }

    }

    public void readCacheData() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls activity
     * @param <T> viewModel
     * @return viewModel
     */
    public static <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }


    public void onRequestData() {
        if (!NetworkUtils.isConnectedByState(mContext)) {
            showToast("请检查你的网络！！！");
            mSwipeRefreshRv.setRefreshing(false);
        }
        mSwipeRefreshRv.setRefreshing(mRefreshing);

//        mViewModel.getObservableError().observe(this, mErrorObserver);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mNextPageToken = "";
        mRefreshing = true;
        onRequestData();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        mRefreshing = false;
        onRequestData();
    }

//    public Observer<String> mErrorObserver = new Observer<String>() {
//        @Override
//        public void onChanged(@Nullable String s) {
//
//        }
//    };
}
