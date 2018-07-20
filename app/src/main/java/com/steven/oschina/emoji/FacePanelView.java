package com.steven.oschina.emoji;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.steven.oschina.R;
import com.steven.oschina.utils.SoftKeyboardUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 * Data：7/19/2018-10:31 AM
 *
 * @author yanzhiwen
 */
public class FacePanelView extends LinearLayout implements View.OnClickListener,
        FaceRecyclerView.OnFaceClickListener, SoftKeyboardUtil.IPanelHeightTarget {
    private static final int PAGE_COUNT = 2;
    private ViewPager mViewPager;
    private FacePanelListener mListener;
    private boolean mKeyboardShowing;
    private AtomicBoolean mWillShowPanel = new AtomicBoolean();
    private int mRealHeight;

    public FacePanelView(Context context) {
        super(context);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FacePanelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRealHeight = SoftKeyboardUtil.getMinPanelHeight(getResources());
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.lay_face_panel, this);

        mViewPager = findViewById(R.id.view_pager);
        findViewById(R.id.tv_qq).setOnClickListener(this);
        findViewById(R.id.tv_emoji).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                final FaceRecyclerView view = createRecyclerView();
                bindRecyclerViewData(view, position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                if (object instanceof FaceRecyclerView) {
                    container.removeView(( FaceRecyclerView ) object);
                }
            }
        });

        // init soft keyboard helper
        SoftKeyboardUtil.attach(( Activity ) getContext(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qq:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_emoji:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.btn_del:
                onDeleteClick();
                break;
        }
    }

    protected FaceRecyclerView createRecyclerView() {
        return new FaceRecyclerView(getContext(), this);
    }

    protected void bindRecyclerViewData(FaceRecyclerView view, final int position) {
        view.setData(DisplayRules.getAllByType(position));
    }

    protected void onDeleteClick() {
        FacePanelListener listener = mListener;
        if (listener != null)
            listener.onDeleteClick();
    }

    @Override
    public void onFaceClick(Emojicon v) {
        FacePanelListener listener = mListener;
        if (listener != null)
            listener.onFaceClick(v);
    }

    public void setListener(FacePanelListener listener) {
        mListener = listener;
    }

    @Override
    public void refreshHeight(int panelHeight) {
        mRealHeight = panelHeight;
        //UiUtil.changeViewHeight(this, panelHeight);
    }

    @Override
    public void onKeyboardShowing(boolean showing) {
        mKeyboardShowing = showing;
        if (showing) {
            hidePanel();
        } else if (mWillShowPanel.getAndSet(false)) {
            openPanel();
        }
    }

    public boolean isShow() {
        return getVisibility() != GONE;
    }

    public void hidePanel() {
        setVisibility(GONE);
    }

    public void openPanel() {
        if (mKeyboardShowing) {
            mWillShowPanel.set(true);
            FacePanelListener listener = mListener;
            if (listener != null)
                listener.hideSoftKeyboard();
        } else {
            setVisibility(VISIBLE);
        }

    }

    @Override
    public int getPanelHeight() {
        return mRealHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mKeyboardShowing) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.getMode(MeasureSpec.EXACTLY));
        } else {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(SoftKeyboardUtil.getKeyboardHeight(getContext()),
                    MeasureSpec.getMode(MeasureSpec.EXACTLY));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface FacePanelListener extends FaceRecyclerView.OnFaceClickListener {
        void onDeleteClick();

        void hideSoftKeyboard();
    }

}
