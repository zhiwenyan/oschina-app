package com.oschina.client.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhiwenyan on 5/25/17.
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;

    public GridLayoutItemDecoration(Context context, int drawableId) {
        mDrawable = ContextCompat.getDrawable(context, drawableId);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int bottom = mDrawable.getIntrinsicHeight();
        int right = mDrawable.getIntrinsicWidth();

        if (isLastRaw(view, parent)) {  //最后一行
            bottom = 0;
        }
        if (isLastColumn(view, parent)) {  //最后一列
            right = 0;
        }
        outRect.bottom = bottom;
        outRect.right = right;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }


    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
//            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - parent.getPaddingLeft();
            int right = childView.getRight() + mDrawable.getIntrinsicWidth() - parent.getPaddingRight();
            int top = childView.getBottom();
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int top = childView.getTop();
            int bottom = childView.getBottom();
            int left = childView.getRight();
            int right = mDrawable.getIntrinsicWidth() + left;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    /**
     * 最后一列
     *
     * @return
     */
    private boolean isLastColumn(View view, RecyclerView parent) {
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager ) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return (currentPosition + 1) % spanCount == 0;
        }
        return false;
    }

    /**
     * 最后一行
     *
     * @return
     */

    private boolean isLastRaw(View view, RecyclerView parent) {
        //当前位置>（行数-1）*列数
        int spanCount = getSpanCount(parent);
        //行数=总的条目/列数
        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount : (parent.getAdapter().getItemCount() / spanCount + 1);
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        return (currentPosition + 1) > (rowNumber - 1) * spanCount;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager ) layoutManager;
            return gridLayoutManager.getSpanCount();
        }
        return 1;
    }
}
