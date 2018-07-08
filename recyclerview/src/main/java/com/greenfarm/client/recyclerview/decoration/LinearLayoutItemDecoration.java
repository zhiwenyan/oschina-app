package com.greenfarm.client.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhiwenyan on 5/25/17.
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Drawable mDrawable;

    public LinearLayoutItemDecoration(Context context, int drawableId) {
        mContext = context;
        mDrawable = ContextCompat.getDrawable(context, drawableId);
    }

    /**
     * 留出空间
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //当前View的位置
        int position = parent.getChildAdapterPosition(view);
        if (position != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mDrawable.getIntrinsicHeight();
        }
    }

    /**
     * 绘制分割线
     *
     * @param c
     * @param parent
     * @param state
     */

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(rect);
            mDrawable.draw(c);
        }

    }
}
