package com.steven.oschina.comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.steven.oschina.R;
import com.steven.oschina.dialog.BottomDialog;
import com.steven.oschina.emoji.EmojiView;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.widget.RichEditText;

/**
 * Description:
 * Data：7/18/2018-5:38 PM
 *
 * @author yanzhiwen
 */
public class BottomSheetBar {

    private View mRootView;
    private RichEditText mEditText;
    private ImageButton mAtView;
    private ImageButton mFaceView;
    private CheckBox mSyncToTweetView;
    private Context mContext;
    private Button mBtnCommit;
    private BottomDialog mDialog;
    private FrameLayout mFrameLayout;
    private EmojiView mEmojiView;


    private BottomSheetBar(Context context) {
        this.mContext = context;
    }

    @SuppressLint("InflateParams")
    public static BottomSheetBar delegation(Context context) {
        BottomSheetBar bar = new BottomSheetBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_comment_bar, null, false);
        bar.initView();
        return bar;
    }

    private void initView() {
        mFrameLayout = mRootView.findViewById(R.id.fl_face);
        mEditText = mRootView.findViewById(R.id.et_comment);
        mAtView = mRootView.findViewById(R.id.ib_mention);
        mFaceView = mRootView.findViewById(R.id.ib_face);
        mFaceView.setVisibility(View.GONE);
        mSyncToTweetView = mRootView.findViewById(R.id.cb_sync);
        if (mFaceView.getVisibility() == View.GONE) {
            mSyncToTweetView.setText(R.string.send_tweet);
        }
        mBtnCommit = mRootView.findViewById(R.id.btn_comment);
        mBtnCommit.setEnabled(false);

        mDialog = new BottomDialog(mContext, false);
        mDialog.setContentView(mRootView);

        mDialog.setOnDismissListener(dialog -> {
            TDevice.hideSoftKeyboard(mEditText);
            mFrameLayout.setVisibility(View.GONE);
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnCommit.setEnabled(s.length() > 0);
            }
        });
    }

    public void hideSyncAction() {
        mSyncToTweetView.setVisibility(View.INVISIBLE);
        mSyncToTweetView.setText(null);
    }

    public void hideMentionAction() {
        mAtView.setVisibility(View.INVISIBLE);
    }

    /**
     * 默认显示的
     */
    public void showSyncAction() {
        mSyncToTweetView.setVisibility(View.VISIBLE);
        mSyncToTweetView.setText(R.string.send_tweet);
    }

    public void showEmoji() {
        mSyncToTweetView.setText(R.string.tweet_publish_title);
        mFaceView.setVisibility(View.VISIBLE);
        mFaceView.setOnClickListener(v -> {
            if (mEmojiView == null) {
                mEmojiView = new EmojiView(mContext, mEditText);
                mFrameLayout.addView(mEmojiView);
            }
            mFrameLayout.setVisibility(View.VISIBLE);
            mEmojiView.openPanel();
            TDevice.closeKeyboard(mEditText);
        });

        mEditText.setOnClickListener(v -> mFrameLayout.setVisibility(View.GONE));
    }

    public void show(String hint) {
        mDialog.show();
        if (!"添加评论".equals(hint)) {
            mEditText.setHint(hint + " ");
        }
        mRootView.postDelayed(() -> TDevice.showSoftKeyboard(mEditText), 50);
    }

    public void dismiss() {
        TDevice.closeKeyboard(mEditText);
        mDialog.dismiss();
    }

    public void setMentionListener(View.OnClickListener listener) {
        mAtView.setOnClickListener(listener);
    }

    public void setFaceListener(View.OnClickListener listener) {
        mFaceView.setOnClickListener(listener);
    }

    public void setCommitListener(View.OnClickListener listener) {
        mBtnCommit.setOnClickListener(listener);
    }

    public void handleSelectFriendsResult(Intent data) {
        String names[] = data.getStringArrayExtra("names");
        if (names != null && names.length > 0) {
            String text = "";
            for (String n : names) {
                text += "@" + n + " ";
            }
            mEditText.getText().insert(mEditText.getSelectionEnd(), text);
        }
    }

    public RichEditText getEditText() {
        return mEditText;
    }

    public String getCommentText() {
        String str = mEditText.getText().toString();
        return TextUtils.isEmpty(str) ? "" : str.trim();
    }

    public Button getBtnCommit() {
        return mBtnCommit;
    }

    public boolean isSyncToTweet() {
        return mSyncToTweetView != null && mSyncToTweetView.isChecked();
    }
}
