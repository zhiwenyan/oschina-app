package com.steven.oschina.ui.write;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.steven.oschina.R;

/**
 * Description:
 * Data：8/9/2018-5:34 PM
 *
 * @author yanzhiwen
 */
public class HPopupWindow extends PopupWindow implements View.OnClickListener {
    private ImageView mImageH1;
    private ImageView mImageH2;
    private ImageView mImageH3;
    private OnHeaderChangeListener mListener;

    public HPopupWindow(Context context, OnHeaderChangeListener listener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_h, null), LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setOutsideTouchable(true);
        View view = getContentView();
        this.mListener = listener;
        mImageH1 = view.findViewById(R.id.iv_h1);
        mImageH2 = view.findViewById(R.id.iv_h2);
        mImageH3 = view.findViewById(R.id.iv_h3);
        mImageH1.setOnClickListener(this);
        mImageH2.setOnClickListener(this);
        mImageH3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_h1:
                mImageH1.setSelected(!mImageH1.isSelected());
                mListener.onTitleChange(mImageH1.isSelected() ? 28 : 18);
                DrawableCompat.setTint(mImageH1.getDrawable(), mImageH1.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);

                break;
            case R.id.iv_h2:
                mImageH2.setSelected(!mImageH2.isSelected());
                mListener.onTitleChange(mImageH2.isSelected() ? 24 : 18);
                DrawableCompat.setTint(mImageH2.getDrawable(), mImageH2.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);

                break;
            case R.id.iv_h3:
                mImageH3.setSelected(!mImageH3.isSelected());
                mListener.onTitleChange(mImageH3.isSelected() ? 20 : 18);
                DrawableCompat.setTint(mImageH3.getDrawable(), mImageH3.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);
                break;

        }
    }

    public void show(View v) {
        showAsDropDown(v, 0, -2 * v.getMeasuredHeight() + 10);
    }

    public interface OnHeaderChangeListener {
        void onTitleChange(int size);
    }
}