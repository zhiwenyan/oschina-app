package com.steven.oschina.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

/**
 * Description:
 * Data：7/19/2018-11:20 AM
 *
 * @author yanzhiwen
 */
@SuppressLint("ViewConstructor")
public class EmojiRecyclerView extends FaceRecyclerView {

    EmojiRecyclerView(Context context, FaceRecyclerView.OnFaceClickListener listener) {
        super(context, listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
 //       ViewParent parent = this;
//        while (!((parent = parent.getParent()) instanceof ViewPager)) ;
//        parent.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
