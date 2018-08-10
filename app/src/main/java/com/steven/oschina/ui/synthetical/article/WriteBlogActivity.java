package com.steven.oschina.ui.synthetical.article;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.ui.write.AlignPopupWindow;
import com.steven.oschina.ui.write.FontPopupWindow;
import com.steven.oschina.ui.write.HPopupWindow;
import com.steven.oschina.widget.rich.RichEditLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteBlogActivity extends BaseActivity implements
        AlignPopupWindow.OnAlignChangeListener, FontPopupWindow.OnFontChangeListener, HPopupWindow.OnHeaderChangeListener {
    @BindView(R.id.richLayout)
    RichEditLayout mRichEditLayout;
    @BindView(R.id.btn_font)
    ImageButton mBtnFont;
    @BindView(R.id.btn_h)
    ImageButton mBtnH;
    @BindView(R.id.btn_align)
    ImageButton mBtnAlign;
    @BindView(R.id.btn_category)
    ImageButton mBtnCategory;
    @BindView(R.id.btn_keyboard)
    ImageButton mBtnKeyboard;
    @BindView(R.id.fl_content)
    FrameLayout mFrameContent;
    private AlignPopupWindow mAlignPopupWindow;
    private FontPopupWindow mFontPopupWindow;
    private HPopupWindow mHPopupWindow;


    public static void show(Context context) {
        context.startActivity(new Intent(context, WriteBlogActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_blog;
    }

    @Override
    protected void initData() {
        mAlignPopupWindow = new AlignPopupWindow(this, this);
        mFontPopupWindow = new FontPopupWindow(this, this);
        mHPopupWindow = new HPopupWindow(this, this);
        mRichEditLayout.setContentPanel(mFrameContent);

    }


    @OnClick({R.id.btn_font, R.id.btn_h, R.id.btn_align, R.id.btn_category, R.id.btn_keyboard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_font:
                mFontPopupWindow.show(view);
                break;
            case R.id.btn_h:
                mHPopupWindow.show(view);
                break;
            case R.id.btn_align:
                mAlignPopupWindow.show(view);
                break;
            case R.id.btn_category:
                break;
            case R.id.btn_keyboard:
                if (mRichEditLayout.isKeyboardOpen()) {
                    mRichEditLayout.closeKeyboard();
                } else {
                    mRichEditLayout.openKeyboard();
                }
                break;
        }
    }

    @Override
    public void onAlignChange(int align) {
        mRichEditLayout.setAlignStyle(align);
    }

    @Override
    public void onTitleChange(int size) {
        mRichEditLayout.setTextSize(size);
    }


    @Override
    public void onBoldChange(boolean isBold) {
        mRichEditLayout.setBold(isBold);

    }

    @Override
    public void onItalicChange(boolean isItalic) {
        mRichEditLayout.setItalic(isItalic);
    }

    @Override
    public void onMidLineChange(boolean isMidLine) {
        mRichEditLayout.setMidLine(isMidLine);
    }
}
