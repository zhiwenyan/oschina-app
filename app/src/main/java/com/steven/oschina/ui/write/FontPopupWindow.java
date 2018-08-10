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
public class FontPopupWindow extends PopupWindow implements View.OnClickListener {
    private ImageView mImageFontBold;
    private ImageView mImageFontItalic;
    private ImageView mImageFontLine;
    private OnFontChangeListener mListener;

    public FontPopupWindow(Context context, OnFontChangeListener listener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_font, null), LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setOutsideTouchable(true);
        this.mListener = listener;
        View view = getContentView();
        mImageFontBold = view.findViewById(R.id.iv_font_bold);
        mImageFontItalic = view.findViewById(R.id.iv_font_italic);
        mImageFontLine = view.findViewById(R.id.iv_font_line);
        mImageFontBold.setOnClickListener(this);
        mImageFontItalic.setOnClickListener(this);
        mImageFontLine.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_font_bold:
                mImageFontBold.setSelected(!mImageFontBold.isSelected());
                mListener.onBoldChange(mImageFontBold.isSelected());
                DrawableCompat.setTint(mImageFontBold.getDrawable(), mImageFontBold.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);
                break;
            case R.id.iv_font_italic:
                mImageFontItalic.setSelected(!mImageFontItalic.isSelected());
                mListener.onItalicChange(mImageFontItalic.isSelected());
                DrawableCompat.setTint(mImageFontItalic.getDrawable(), mImageFontItalic.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);
                break;
            case R.id.iv_font_line:
                mImageFontLine.setSelected(!mImageFontLine.isSelected());
                mListener.onMidLineChange(mImageFontLine.isSelected());
                DrawableCompat.setTint(mImageFontLine.getDrawable(), mImageFontLine.isSelected() ? 0xff24cf5f : 0xFFFFFFFF);
                break;
        }
    }

    public void show(View v) {
        showAsDropDown(v, 0, -2 * v.getMeasuredHeight() + 10);
    }

    public interface OnFontChangeListener {
        void onBoldChange(boolean isBold);

        void onItalicChange(boolean isItalic);

        void onMidLineChange(boolean isMidLine);
    }
}
