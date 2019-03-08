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

import com.oschina.client.recyclerview.widget.WrapRecyclerView;
import com.steven.oschina.R;

/**
 * Description:
 * Data：7/4/2018-2:32 PM
 *
 * @author yanzhiwen
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private WrapRecyclerView mRecyclerView;
    private View mLoadMoreView;
    private ProgressBar mLoadMorePb;
    private TextView mLoadMoreTv;

    private int mBottomViewCount;
    private int mHeaderViewCount;

    private boolean mLoading = false;
    private boolean mLoadEnable = true;

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
                /*
                 * The RecyclerView is not currently scrolling.（静止没有滚动）
                 */
                //  public static final int SCROLL_STATE_IDLE = 0;

                /*
                 * The RecyclerView is currently being dragged by outside input such as user touch input.
                 *（正在被外部拖拽,一般为用户正在用手指滚动）
                 */
                //  public static final int SCROLL_STATE_DRAGGING = 1;

                /*
                 * The RecyclerView is currently animating to a final position while not under outside control.
                 *（自动滚动）
                 */
                // public static final int SCROLL_STATE_SETTLING = 2;

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //上拉加载更多
                if (mListener != null && isScrollBottom() && mLoadEnable && !mLoading) {
                    mLoading = true;
                    mListener.onLoadMore();
                }
            }
        });
        //下拉刷新
        this.setOnRefreshListener(this);
        this.setColorSchemeColors(ContextCompat.getColor(context, R.color.main_green));

    }

    @Override
    public void onRefresh() {
        if (mListener != null) {
            mLoading = false;
            mListener.onRefresh();
        }
    }

    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public boolean isLoading() {
        return mLoading;
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
        mBottomViewCount++;
    }

    /**
     * 显示加载完成
     */
    public void showLoadComplete() {
        if (mRecyclerView.getAdapter() != null) {
            mLoadEnable = false;
            mLoadMorePb.setVisibility(View.GONE);
            mLoadMoreTv.setText("已全部加载完毕");
        }
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
        mHeaderViewCount++;
    }

    /**
     * 判断RecyclerView是否到了最底部
     */
    private boolean isScrollBottom() {
        //第一个可见item的位置 + 当前可见的item个数 >= item的总个数-头部底部的View的个数
        //这样就可以判断出来，是在底部了。
        return (mRecyclerView != null && mRecyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount() - mHeaderViewCount - mBottomViewCount);
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

    private OnRefreshLoadListener mListener;

    public void setOnRefreshLoadListener(OnRefreshLoadListener listener) {
        this.mListener = listener;
    }


    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }
}
