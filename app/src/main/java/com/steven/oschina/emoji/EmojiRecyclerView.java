package com.steven.oschina.emoji;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Description:
 * Data：7/19/2018-11:20 AM
 *
 * @author yanzhiwen
 */
class EmojiRecyclerView extends FaceRecyclerView {

    EmojiRecyclerView(Context context, FaceRecyclerView.OnFaceClickListener listener) {
        super(context, listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent = this;
        while (!((parent = parent.getParent()) instanceof ViewPager)) ;
        parent.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
