package com.steven.oschina.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.steven.oschina.media.SpaceGridItemDecoration;
import com.steven.oschina.utils.TDevice;

/**
 * Description:
 * Data：8/3/2018-1:39 PM
 *
 * @author yanzhiwen
 */
public class TweetPictureRecycleView extends RecyclerView {
    public TweetPictureRecycleView(Context context) {
        this(context, null);
    }

    public TweetPictureRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TweetPictureRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new GridLayoutManager(context, 3));
        addItemDecoration(new SpaceGridItemDecoration(( int ) TDevice.dipToPx(getResources(), 2)));
    }
}
