package com.steven.oschina.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Data：8/9/2018-5:24 PM
 *
 * @author yanzhiwen
 */
public class TriangleView extends View {
    private Paint mPaint;

    public TriangleView(Context context) {
        this(context,null);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xFF000000);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.lineTo(0, 0);
        path.lineTo(getMeasuredWidth(), 0);
        path.lineTo(getMeasuredWidth() / 2, getMeasuredHeight());
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
