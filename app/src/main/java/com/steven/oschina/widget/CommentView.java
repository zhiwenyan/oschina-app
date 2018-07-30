package com.steven.oschina.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.GlideApp;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.comment.Comment;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.utils.CollectionUtil;
import com.steven.oschina.utils.CommentsUtil;
import com.steven.oschina.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Description:资讯、问答、博客、翻译、活动、软件详情评论列表当中进行展示的子view.
 * Data：7/30/2018-11:40 AM
 *
 * @author yanzhiwen
 */
public class CommentView extends FrameLayout implements View.OnClickListener {
    private long mId;
    private int mType;
    private TextView mTitle;
    private String mShareTitle;
    private TextView mSeeMore;
    private LinearLayout mLayComments;
    private LinearLayout mLinearComment, mLinearTip;
    private OnCommentClickListener mListener;

    public CommentView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CommentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.detail_comment_view, this, true);
        mTitle = findViewById(R.id.tv_detail_comment);
        mLinearComment = findViewById(R.id.ll_comment);
        mLayComments = findViewById(R.id.lay_detail_comment);
        mLinearTip = findViewById(R.id.ll_tip);
        mSeeMore = findViewById(R.id.tv_see_more_comment);
        mLinearTip.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onShowComment(v);
            }
        });
    }


    public void init(long id, final int type, int order, final int commentCount, final OnCommentClickListener onCommentClickListener) {
        this.mId = id;
        this.mType = type;
        mSeeMore.setVisibility(View.GONE);
        mSeeMore.setText(String.format("查看所有 %s 条评论", commentCount));
        mLinearComment.setVisibility(GONE);
        mLinearTip.setVisibility(GONE);
        this.mListener = onCommentClickListener;
        Map<String, Object> params = new HashMap<>();
        params.put("sourceId", id);
        params.put("type", type);
        params.put("parts", "refer,reply");
        params.put("order", order);
        params.put("pageToken", "");
        HttpUtils._get(RetrofitClient.getServiceApi().comment_list(params), new HttpCallback<PageBean<Comment>>() {
            @Override
            public void onSuccess(PageBean<Comment> result) {
                super.onSuccess(result);
                List<Comment> comments = result.getItems();
                setTitle(String.format("%s", type == News.TYPE_QUESTION ? "热门回复" : "热门评论"));
                mSeeMore.setVisibility(VISIBLE);
                mSeeMore.setOnClickListener(CommentView.this);
                Comment[] array = CollectionUtil.toArray(comments, Comment.class);
                initComment(array, onCommentClickListener);
            }
        });
    }


    private void initComment(final Comment[] comments, final OnCommentClickListener onCommentClickListener) {

        if (mLayComments != null)
            mLayComments.removeAllViews();
        if (comments != null && comments.length > 0) {
            mLinearComment.setVisibility(VISIBLE);
            mLayComments.setVisibility(VISIBLE);
            for (int i = 0, len = comments.length; i < len; i++) {
                final Comment comment = comments[i];
                if (comment != null) {
                    final ViewGroup commentItem = insertComment(true, comment, onCommentClickListener);
                    commentItem.setOnClickListener(v -> {
                        if (mType == ServiceApi.COMMENT_EVENT || mType == ServiceApi.COMMENT_QUESTION) {
                            // QuesAnswerDetailActivity.show(lay.getContext(), comment, mId, mType);
                        } else {
                            onCommentClickListener.onClick(v, comment);
                        }
                    });
                    mLayComments.addView(commentItem);
                    if (i == len - 1) {
                        commentItem.findViewById(R.id.line).setVisibility(GONE);
                    } else {
                        commentItem.findViewById(R.id.line).setVisibility(View.VISIBLE);
                    }
                }
            }
        } else {
            mLinearComment.setVisibility(View.GONE);
            mLinearTip.setVisibility(View.VISIBLE);
        }
    }

    public void setShareTitle(String shareTitle) {
        this.mShareTitle = shareTitle;
    }


    private ViewGroup insertComment(final boolean first, final Comment comment, final OnCommentClickListener onCommentClickListener) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup commentItem = ( ViewGroup ) inflater.inflate(R.layout.lay_comment_item, null, false);
        Author author = comment.getAuthor();
        CircleImageView ivAvatar = commentItem.findViewById(R.id.iv_avatar);
        GlideApp.with(getContext()).load(author.getPortrait()).into(ivAvatar);
        TextView tv_name = commentItem.findViewById(R.id.tv_name);
        TextView tv_pub_date = commentItem.findViewById(R.id.tv_pub_date);
        TweetTextView tv_content = commentItem.findViewById(R.id.tv_content);
        tv_name.setText(author.getName());
        tv_pub_date.setText(StringUtils.formatSomeAgo(comment.getPubDate()));
        CommentsUtil.formatHtml(getResources(), tv_content, comment.getContent());
        ivAvatar.setOnClickListener(v -> {
            //    OtherUserHomeActivity.show(getContext(), comment.getAuthor().getId());
        });
        final ImageView ivComment = commentItem.findViewById(R.id.iv_best_answer);
        final TextView tvVoteCount = commentItem.findViewById(R.id.tv_vote_count);
        tvVoteCount.setText(String.valueOf(comment.getVote()));
        final ImageView ivVoteStatus = commentItem.findViewById(R.id.btn_vote);
        if (mType == ServiceApi.COMMENT_QUESTION || mType == ServiceApi.COMMENT_EVENT
                || mType == ServiceApi.COMMENT_TRANSLATION || mType == ServiceApi.COMMENT_BLOG) {

            tvVoteCount.setVisibility(View.GONE);
            ivVoteStatus.setVisibility(View.GONE);
            if (comment.isBest()) {
                ivComment.setEnabled(false);
                ivComment.setImageResource(R.mipmap.label_best_answer);
                ivComment.setVisibility(VISIBLE);
            }
        } else {
            ivComment.setEnabled(true);
            tvVoteCount.setText(String.valueOf(comment.getVote()));
            tvVoteCount.setVisibility(View.VISIBLE);
            ivVoteStatus.setVisibility(View.VISIBLE);

            if (comment.getVoteState() == 1) {
                ivVoteStatus.setImageResource(R.mipmap.ic_thumbup_actived);
                ivVoteStatus.setTag(true);
            } else if (comment.getVoteState() == 0) {
                ivVoteStatus.setImageResource(R.mipmap.ic_thumb_normal);
                ivVoteStatus.setTag(null);
            }

        }
        if (!first) {
            addView(commentItem, 0);
        }

        return commentItem;
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {

        // if (mId != 0 && mType != 0)
        //     CommentsActivity.show((AppCompatActivity ) getContext(), mId, mType, ServiceApi.COMMENT_NEW_ORDER, mShareTitle);
    }

    public interface OnCommentClickListener {
        void onClick(View view, Comment comment);

        void onShowComment(View view);
    }
}
