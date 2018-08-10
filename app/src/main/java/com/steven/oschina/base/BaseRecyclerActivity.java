package com.steven.oschina.base;

import com.oschina.client.base_library.utils.NetworkUtils;
import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.widget.SwipeRefreshRecyclerView;

import butterknife.BindView;

/**
 * Description:
 * Data：7/4/2018-3:52 PM
 *
 * @author yanzhiwen
 */
public abstract class BaseRecyclerActivity<T> extends BaseActivity implements SwipeRefreshRecyclerView.OnRefreshLoadListener {
    @BindView(R.id.swipe_refresh_recycler)
    public SwipeRefreshRecyclerView mSwipeRefreshRv;
    //上拉加载下一个页面的Token
    public String mNextPageToken = "";
    public CommonRecyclerAdapter<T> mAdapter;
    public boolean mRefreshing;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    public void initData() {
        mSwipeRefreshRv.setOnRefreshLoadListener(this);
        onRequestData(mNextPageToken);
    }

    public void onRequestData(String nextPageToken) {
        if (!NetworkUtils.isConnectedByState(this)) {
            showToast("请检查你的网络！！！");
            mSwipeRefreshRv.setRefreshing(false);
            return;
        }
        mSwipeRefreshRv.setRefreshing(mRefreshing);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mRefreshing = true;
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        mRefreshing = false;
    }
}
