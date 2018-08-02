package com.steven.oschina.media;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.greenfarm.client.recyclerview.adapter.OnItemClickListener;
import com.steven.oschina.R;
import com.steven.oschina.media.adapter.ImageFolderAdapter;
import com.steven.oschina.media.bean.ImageFolder;

import java.util.List;

/**
 * Description:
 * Data：8/2/2018-3:25 PM
 *
 * @author yanzhiwen
 */
public class ImageFolderPopupWindow extends PopupWindow implements View.OnAttachStateChangeListener, OnItemClickListener {

    private ImageFolderAdapter mFolderAdapter;
    private Callback mCallback;
    private RecyclerView mFolderView;
    private List<ImageFolder> mImageFolders;
    private Context mContext;

    public ImageFolderPopupWindow(Context context, List<ImageFolder> imageFolder, Callback callback) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_folder, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mCallback = callback;
        this.mImageFolders = imageFolder;
        this.mContext = context;
        // init
        //  setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);

        // content
        View content = getContentView();
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        content.addOnAttachStateChangeListener(this);
        mFolderView = content.findViewById(R.id.rv_popup_folder);
        mFolderView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setAdapter(ImageFolderAdapter adapter) {
        this.mFolderAdapter = adapter;
        mFolderView.setAdapter(adapter);
        mFolderAdapter.setOnItemClickListener(this);
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void onItemClick(int position) {
        if (mCallback != null)
            mCallback.onSelect(this, mImageFolders.get(position));
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        if (mCallback != null) {
            setBackgroundAlpha(0.8f);
            mCallback.onShow();
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        if (mCallback != null) {
            setBackgroundAlpha(1.0f);
            mCallback.onDismiss();
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param alpha 透明度
     */
    public void setBackgroundAlpha(float alpha) {
        Activity activity = (( Activity ) mContext);
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

    }

    public interface Callback {
        void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder model);

        void onDismiss();

        void onShow();
    }
}
