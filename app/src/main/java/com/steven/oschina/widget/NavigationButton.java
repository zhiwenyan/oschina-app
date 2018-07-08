package com.steven.oschina.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.steven.oschina.R;

/**
 * Description:
 * Data：7/4/2018-10:30 AM
 *
 * @author yanzhiwen
 */
public class NavigationButton extends FrameLayout {
    private ImageView mIconView;
    private TextView mIconTitle;
    private TextView mDot;

    public NavigationButton(@NonNull Context context) {
        this(context, null);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tab_bottom_nav, this, true);
        mIconView = findViewById(R.id.nav_iv_icon);
        mIconTitle = findViewById(R.id.nav_tv_title);
        mDot = findViewById(R.id.nav_tv_dot);
    }

    public void showRedDot(int count) {
        mDot.setVisibility(count > 0 ? VISIBLE : GONE);
        mDot.setText(String.valueOf(count));
    }

    public void init(int resId, String title) {
        mIconView.setImageResource(resId);
        mIconTitle.setText(title);
    }

    public void setSelected(boolean selected) {
        mIconView.setSelected(selected);
        mIconTitle.setSelected(selected);
    }

}
