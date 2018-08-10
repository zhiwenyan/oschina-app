package com.steven.oschina.widget.rich;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.steven.oschina.R;


/**
 * 底部工具栏
 * Created by huanghaibin on 2017/8/3.
 */


public class RichBar extends LinearLayout {
    ImageButton mBtnKeyboard;
    ImageButton mBtnFont;
    ImageButton mBtnAlign;
    ImageButton mBtnCategory;
    ImageButton mBtnTitle;

    public RichBar(Context context) {
        this(context, null);
    }

    public RichBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_rich_bar, this, true);
        mBtnKeyboard = findViewById(R.id.btn_keyboard);
        mBtnFont = findViewById(R.id.btn_font);
        mBtnAlign = findViewById(R.id.btn_align);
        mBtnCategory = findViewById(R.id.btn_category);
        mBtnTitle = findViewById(R.id.btn_h);
    }

    void setBarEnable(boolean isEnable) {
        mBtnKeyboard.setEnabled(isEnable);
        mBtnFont.setEnabled(isEnable);
        mBtnAlign.setEnabled(isEnable);
        mBtnCategory.setEnabled(isEnable);
        mBtnTitle.setEnabled(isEnable);
    }
}