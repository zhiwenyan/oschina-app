package com.steven.oschina.base;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.widget.SwipeRefreshRecyclerView;

/**
 * Description:
 * Data：7/4/2018-3:52 PM
 *
 * @author yanzhiwen
 */
public class BaseRecyclerFragment<T> extends BaseFragment implements SwipeRefreshRecyclerView.OnRefreshLoadListener {
    public SwipeRefreshRecyclerView mSwipeRefreshRv;
    //上拉加载下一个页面的Token
    protected String mNextPageToken = "";
    protected CommonRecyclerAdapter<T> mAdapter;
    public boolean mRefreshing = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    public void initData() {
        mSwipeRefreshRv = mRootView.findViewById(R.id.swipe_refresh_recycler);
        mSwipeRefreshRv.setOnRefreshLoadListener(this);
        onRequestData(mNextPageToken);
    }

    public void onRequestData(String nextPageToken) {
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
