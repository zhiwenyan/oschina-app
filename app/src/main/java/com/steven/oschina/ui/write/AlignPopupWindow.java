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
import com.steven.oschina.bean.write.TextSection;

/**
 * Description:
 * Data：8/9/2018-5:34 PM
 *
 * @author yanzhiwen
 */
public class AlignPopupWindow extends PopupWindow implements View.OnClickListener {
    private OnAlignChangeListener mListener;
    private ImageView mImageAlignLeft;
    private ImageView mImageAlignCenter;
    private ImageView mImageAlignRight;

    public AlignPopupWindow(Context context, OnAlignChangeListener listener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_align, null), LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setOutsideTouchable(true);
        this.mListener = listener;
        View view = getContentView();
        mImageAlignLeft = view.findViewById(R.id.iv_align_left);
        mImageAlignCenter = view.findViewById(R.id.iv_align_center);
        mImageAlignRight = view.findViewById(R.id.iv_align_right);
        mImageAlignLeft.setOnClickListener(this);
        mImageAlignCenter.setOnClickListener(this);
        mImageAlignRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_align_left:
                mImageAlignLeft.setSelected(!mImageAlignLeft.isSelected());
                mListener.onAlignChange(TextSection.LEFT);
                DrawableCompat.setTint(mImageAlignLeft.getDrawable(), 0xff24cf5f);
                DrawableCompat.setTint(mImageAlignCenter.getDrawable(), 0xFFFFFFFF);
                DrawableCompat.setTint(mImageAlignRight.getDrawable(), 0xFFFFFFFF);
                break;
            case R.id.iv_align_center:
                mImageAlignCenter.setSelected(!mImageAlignCenter.isSelected());
                mListener.onAlignChange(TextSection.CENTER);
                DrawableCompat.setTint(mImageAlignLeft.getDrawable(), 0xFFFFFFFF);
                DrawableCompat.setTint(mImageAlignCenter.getDrawable(), 0xff24cf5f);
                DrawableCompat.setTint(mImageAlignRight.getDrawable(), 0xFFFFFFFF);
                break;
            case R.id.iv_align_right:
                mImageAlignRight.setSelected(!mImageAlignRight.isSelected());
                mListener.onAlignChange(TextSection.RIGHT);
                DrawableCompat.setTint(mImageAlignLeft.getDrawable(), 0xFFFFFFFF);
                DrawableCompat.setTint(mImageAlignCenter.getDrawable(), 0xFFFFFFFF);
                DrawableCompat.setTint(mImageAlignRight.getDrawable(), 0xff24cf5f);
                break;

        }
    }


    public void show(View v) {
        showAsDropDown(v, 0, -2 * v.getMeasuredHeight() + 10);
    }

    public interface OnAlignChangeListener {
        void onAlignChange(int align);
    }
}
