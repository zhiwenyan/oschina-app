package com.steven.oschina.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greenfarm.client.recyclerview.widget.WrapRecyclerView;
import com.steven.oschina.R;

/**
 * Description:
 * Data：7/4/2018-2:32 PM
 *
 * @author yanzhiwen
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout {
    private WrapRecyclerView mRecyclerView;
    private View mLoadMoreView;
    private ProgressBar mLoadMorePb;
    private TextView mLoadMoreTv;
    private boolean mLoadEnable = true;
    private final int STATUS_LOAD = 2, STATUS_REFRESH = 1;
    private int mStatus = STATUS_REFRESH;
    private int mBottomCount;

    public SwipeRefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRecyclerView = new WrapRecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        addView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //上拉加载更多
                if (mListener != null && isScrollBottom() && mLoadEnable) {
                    mListener.onLoadMore();
                    mStatus = STATUS_LOAD;

                }
            }
        });
        //下拉刷新
        this.setOnRefreshListener(() -> {
            if (mListener != null) {
                mListener.onRefresh();
                mStatus = STATUS_REFRESH;
            }
        });
        this.setColorSchemeColors(ContextCompat.getColor(context, R.color.main_green));

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter not null！！！");
        }
        mRecyclerView.setAdapter(adapter);
        if (mLoadMoreView == null) {
            mLoadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.recycle_footer_view, mRecyclerView, false);
            mLoadMorePb = mLoadMoreView.findViewById(R.id.pb_footer);
            mLoadMoreTv = mLoadMoreView.findViewById(R.id.tv_footer);
        }
        mRecyclerView.addFooterView(mLoadMoreView);
        mBottomCount++;
    }

    /**
     * 显示加载完成
     */
    public void showLoadComplete() {
        mLoadEnable = false;
        mLoadMorePb.setVisibility(View.GONE);
        mLoadMoreTv.setText("已全部加载完毕");
    }


    /**
     * 显示加载
     */
    public void showLoad() {
        mLoadEnable = true;
        mLoadMoreTv.setVisibility(View.VISIBLE);
        mLoadMoreTv.setText("加载中...");
    }

    /**
     * 添加头部View 比如Banner
     *
     * @param view view
     */
    public void addHeaderView(View view) {
        mRecyclerView.addHeaderView(view);
        mBottomCount++;
    }

    /**
     * 判断RecyclerView是否到了最底部
     */
    private boolean isScrollBottom() {
        return (mRecyclerView != null && mRecyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount() - mBottomCount);
    }


    /**
     * 获取RecyclerView可见的最后一项
     *
     * @return 可见的最后一项position
     */
    public int getLastVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }


    /**
     * 获得最大的位置
     *
     * @param positions 获得最大的位置
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    public int getStatus() {
        return mStatus;
    }


    private OnRefreshLoadListener mListener;

    public void setOnRefreshLoadListener(OnRefreshLoadListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }
}
