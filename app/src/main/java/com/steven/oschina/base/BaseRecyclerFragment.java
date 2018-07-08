package com.steven.oschina.base;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.widget.SwipeRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Data：7/4/2018-3:52 PM
 *
 * @author yanzhiwen
 */
public class BaseRecyclerFragment<T> extends BaseFragment implements SwipeRefreshRecyclerView.OnRefreshLoadListener {
    @BindView(R.id.swipe_refresh_recycler)
    public SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;
    //上拉加载下一个页面的Token
    protected String mNextPageToken = "";
    protected CommonRecyclerAdapter<T> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    public void initData() {
        mSwipeRefreshRecyclerView.setRefreshing(true);
        mSwipeRefreshRecyclerView.setOnRefreshLoadListener(this);
        onRequestData(mNextPageToken);
    }


    public void onRequestData(String nextPageToken) {
    }




    public class RetrofitCallback extends HttpCallback<T> {
        @Override
        public void onSuccess(List<T> result, String nextPageToken) {
            super.onSuccess(result, nextPageToken);
        }

        @Override
        public void onFailure(Throwable t) {
            super.onFailure(t);
            showToast(t.getMessage());
        }
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
    }
}
