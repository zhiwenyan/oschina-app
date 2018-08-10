package com.steven.oschina.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.R;
import com.steven.oschina.bean.comment.Comment;
import com.steven.oschina.dialog.DialogHelper;

import java.io.File;
import java.util.List;


/**
 * Description:
 * Data：7/11/2018-3:13 PM
 *
 * @author yanzhiwen
 */
public class CommentShareView extends NestedScrollView implements Runnable {
    private CommentShareAdapter mAdapter;
    //    private ShareDialog mShareDialog;
    private ProgressDialog mDialog;
    private Bitmap mBitmap;
    private boolean isShare;

    public CommentShareView(Context context) {
        this(context, null);
    }

    public CommentShareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.comment_share_view, this, true);
        RecyclerView mRecyclerComment = ( RecyclerView ) findViewById(R.id.rv_comment);
        mRecyclerComment.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new CommentShareAdapter(context,null,R.layout.item_list_comment_share);
        mRecyclerComment.setAdapter(mAdapter);
        //      mShareDialog = new ShareDialog((Activity ) context, -1, false);
        mDialog = DialogHelper.getProgressDialog(context);
        mDialog.setMessage("请稍候...");
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isShare)
                    return;
                if (mBitmap != null && !mBitmap.isRecycled()) {
                    mBitmap.recycle();
                }
                removeCallbacks(CommentShareView.this);
            }
        });
    }

    public void init(String title, Comment comment) {
        if (comment == null)
            return;
        setText(R.id.tv_title, title);
        //  mAdapter.clear();
        //  mAdapter.addItem(comment);
    }

    public void dismiss() {
        isShare = false;
        if (mDialog != null)
            mDialog.dismiss();
//        if (mShareDialog != null)
//            mShareDialog.dismiss();
    }

    @Override
    public void run() {
        isShare = true;
        if (mDialog == null)
            return;
        mDialog.dismiss();
        mBitmap = getBitmap();
//        mShareDialog.bitmap(mBitmap);
//        mShareDialog.show();
    }

    public void share() {
        mDialog.show();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
        postDelayed(this, 2000);
    }

    private void setText(int viewId, String text) {
        (( TextView ) findViewById(viewId)).setText(text);
    }

    private Bitmap getBitmap() {
        return create(getChildAt(0));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
        clearShareImage();
    }

    public static void clearShareImage() {
        try {
            String url = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath() + File.separator + "开源中国/share/";
            File file = new File(url);
            if (!file.exists())
                return;
            File[] files = file.listFiles();
            if (files == null || files.length == 0)
                return;
            for (File f : file.listFiles()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap create(View v) {
        try {
            int w = v.getWidth();
            int h = v.getHeight();
            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            Canvas c = new Canvas(bmp);
            c.drawColor(Color.WHITE);
            v.layout(0, 0, w, h);
            v.draw(c);
            return bmp;
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            return null;
        }
    }

    static class CommentShareAdapter extends CommonRecyclerAdapter<Comment> {

        public CommentShareAdapter(Context context, List<Comment> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, Comment item) {

        }

    }
}
