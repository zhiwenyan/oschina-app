package com.steven.oschina.ui.tweet;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.emoji.Emojicon;
import com.steven.oschina.emoji.FacePanelView;
import com.steven.oschina.emoji.InputHelper;
import com.steven.oschina.media.adapter.TweetSelectImageAdapter;
import com.steven.oschina.media.bean.Image;
import com.steven.oschina.ui.SelectOptions;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.widget.RichEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.steven.oschina.utils.TDevice.showSoftKeyboard;


public class TweetPublishFragment extends BaseFragment {

    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.icon_send)
    TextView mIconSend;
    @BindView(R.id.toolbar)
    FrameLayout mToolbar;
    @BindView(R.id.edit_content)
    RichEditText mEditContent;
    @BindView(R.id.txt_indicator)
    TextView mTxtIndicator;
    @BindView(R.id.recycler_images)
    RecyclerView mRvImages;
    @BindView(R.id.cb_commit_control)
    CheckBox mCbCommitControl;
    @BindView(R.id.iv_picture)
    ImageView mIvPicture;
    @BindView(R.id.iv_mention)
    ImageView mIvMention;
    @BindView(R.id.iv_tag)
    ImageView mIvTag;
    @BindView(R.id.iv_emoji)
    ImageView mIvEmoji;
    @BindView(R.id.lay_option)
    LinearLayout mLayOption;
    private List<Image> mTweetImages;
    private TweetSelectImageAdapter mImageAdapter;
    @BindView(R.id.panel_face)
    FacePanelView mFacePanelView;

    public static TweetPublishFragment newInstance() {
        Bundle args = new Bundle();
        TweetPublishFragment fragment = new TweetPublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tweet_publish;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {
        mTweetImages = new ArrayList<>();
        mFacePanelView.setListener(new FacePanelView.FacePanelListener() {
            @Override
            public void onDeleteClick() {
                InputHelper.backspace(mEditContent);
            }

            @Override
            public void hideSoftKeyboard() {
                TweetPublishFragment.this.hideSoftKeyboard();
            }

            @Override
            public void onFaceClick(Emojicon v) {
                InputHelper.input2OSC(mEditContent, v);
            }
        });
        mRvImages.setOnTouchListener((v, event) -> {
            hideAllKeyBoard();
            return false;
        });
    }

    private void hideSoftKeyboard() {
        mEditContent.clearFocus();
        (( InputMethodManager ) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                mEditContent.getWindowToken(), 0);
    }

    @OnClick({R.id.icon_back, R.id.icon_send, R.id.iv_picture, R.id.iv_mention, R.id.iv_tag, R.id.iv_emoji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                (( Activity ) mContext).finish();
                break;
            case R.id.icon_send:

                break;
            case R.id.iv_picture:
                SelectImageActivity.show(mContext, new SelectOptions.Builder()
                        .setHasCamera(true)
                        .setSelectCount(9)
                        .setCallback(this::handleCallbackResult)
                        .build());
                break;
            case R.id.iv_mention:

                break;
            case R.id.iv_tag:

                break;
            case R.id.iv_emoji:
                handleEmojiClick(view);
                break;
        }
    }

    /**
     * Emoji 表情点击
     *
     * @param v View
     */
    private void handleEmojiClick(View v) {
        if (mFacePanelView.isShow()) {
            mFacePanelView.hidePanel();
            showSoftKeyboard(mEditContent);
        } else {
            TDevice.closeKeyboard(mEditContent);
            mFacePanelView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFacePanelView.openPanel();
                }
            }, 250);
        }
    }

    private void handleCallbackResult(List<Image> images) {
        mTweetImages.clear();
        mTweetImages.addAll(images);
        if (mImageAdapter == null) {
            mImageAdapter = new TweetSelectImageAdapter(mContext, mTweetImages, R.layout.item_list_tweet_publish_selecter);
        } else {
            mImageAdapter.notifyDataSetChanged();
        }
        mRvImages.setAdapter(mImageAdapter);

    }

    private void hideAllKeyBoard() {
        mFacePanelView.hidePanel();
        hideSoftKeyboard();
    }


}
