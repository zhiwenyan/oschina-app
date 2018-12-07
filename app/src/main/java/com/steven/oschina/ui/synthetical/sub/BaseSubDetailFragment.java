package com.steven.oschina.ui.synthetical.sub;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.comment.Comment;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.sub.UserRelation;
import com.steven.oschina.media.Util;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.OWebView;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.widget.CommentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseSubDetailFragment<T, V> extends BaseRecyclerFragment1<Article, ArticleListViewModel> {
    protected SubBean mSubBean;
    protected OWebView mWebView;
    protected View mHeaderView;
    private List<Article> mArticles;
    private ImageView mAvatarIv;
    private TextView mArticleTitle;
    private TextView mAuthorName;
    private TextView mPubDate;
    private TextView mTextAbstract;
    private Button mBtnRelation;
    private CommentView mCommentView;

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mSubBean = ( SubBean ) bundle.getSerializable("sub_tab");
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
        mHeaderView = getHeaderView();
        if (mHeaderView != null) {
            mWebView = mHeaderView.findViewById(R.id.webView);
            mWebView.setUseShareCss(true);
            mAvatarIv = mHeaderView.findViewById(R.id.iv_avatar);
            mArticleTitle = mHeaderView.findViewById(R.id.tv_title);
            mAuthorName = mHeaderView.findViewById(R.id.tv_author);
            mPubDate = mHeaderView.findViewById(R.id.tv_pub_date);
            mBtnRelation = mHeaderView.findViewById(R.id.btn_relation);
            mTextAbstract = mHeaderView.findViewById(R.id.tv_detail_abstract);
            mCommentView = mHeaderView.findViewById(R.id.cv_comment);
            if (mContext == null) return;
            //码云挂件替换
            if (mWebView != null) {
                mSubBean.setBody(mSubBean.getBody().replaceAll("(|<pre>)<code>&lt;script src='(//gitee.com/[^>]+)'&gt;&lt;/script&gt;\\s*</code>(|</pre>)",
                        "<code><script src='https:$2'></script></code>"));
                mWebView.loadDetailDataAsync(mSubBean.getBody());
            }
        }
        super.initData();
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        Map<String, Object> params = new HashMap<>();
        String ident = OSCSharedPreference.getInstance().getDeviceUUID();
        params.put("key", String.format("osc_%s_%s", mSubBean.getType(), mSubBean.getId()));
        params.put("ident", ident);
        if (!TextUtils.isEmpty(mNextPageToken)) {
            params.put("pageToken", mNextPageToken);
        }
        mObserver=result->{
            assert result != null;
            mNextPageToken = result.getNextPageToken();
            if (mOnCompleteListener != null) {
                mOnCompleteListener.onComplete();
            }
            showArticleList(result.getItems());
        };
        //类似的文章推荐
        mViewModel.getArticleRecommends(params).observe(this, mObserver);
    }


    private void showArticleList(List<Article> articles) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mSwipeRefreshRv.setRefreshing(false);
            mArticles.clear();
        }
        if (articles.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mArticles.addAll(articles);
        if (mAdapter == null) {
            mAdapter = new ArticleAdapter(mContext, mArticles, (article, position) -> {
                String[] images = article.getImgs();
                if (images == null || images.length == 0) {
                    return R.layout.item_article_not_img;
                }
                if (images.length < 3)
                    return R.layout.item_article_one_img;
                return R.layout.item_article_three_img;
            });
            mSwipeRefreshRv.setAdapter(mAdapter);
            mSwipeRefreshRv.addHeaderView(mHeaderView);
            showAuthor();
            showCommentView();
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showAuthor() {
        Author author = mSubBean.getAuthor();
        ImageLoader.load(mContext, mAvatarIv, author.getPortrait());
        mAuthorName.setText(author.getName());
        mPubDate.setText(StringUtils.formatYearMonthDay(mSubBean.getPubDate()));
        if (mTextAbstract != null && mSubBean.getSummary() != null && !TextUtils.isEmpty(mSubBean.getSummary())) {
            mTextAbstract.setText(mSubBean.getSummary());
            mHeaderView.findViewById(R.id.lay_blog_detail_abstract).setVisibility(View.VISIBLE);
        }
        mBtnRelation.setText(mSubBean.getAuthor().getRelation() < UserRelation.RELATION_ONLY_HER
                ? "已关注" : "关注");
        SpannableStringBuilder spannable = new SpannableStringBuilder(mSubBean.getTitle());
        Resources resources = mContext.getResources();
        int top = Util.dipTopx(mContext, 2);
        boolean isToday = StringUtils.isToday(mSubBean.getPubDate());
        if (isToday) {
            spannable.append(" [icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_today);
            if (originate != null) {
                originate.setBounds(0, -top, originate.getIntrinsicWidth() + top, originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, spannable.length() - 7, spannable.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (mSubBean.isOriginal()) {
            spannable.append(" [icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_originate);
            if (originate != null) {
                originate.setBounds(0, -top, originate.getIntrinsicWidth() + top, originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, spannable.length() - 7, spannable.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannable.append(" [icon] ");
            Drawable originate = resources.getDrawable(R.mipmap.ic_label_reprint);
            if (originate != null) {
                originate.setBounds(0, -top, originate.getIntrinsicWidth() + top, originate.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(originate, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, spannable.length() - 7, spannable.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (mSubBean.isRecommend()) {
            spannable.append(" [icon] ");
            Drawable recommend = resources.getDrawable(R.mipmap.ic_label_recommend);
            if (recommend != null) {
                recommend.setBounds(0, -top, recommend.getIntrinsicWidth() + top, recommend.getIntrinsicHeight());
            }
            ImageSpan imageSpan = new ImageSpan(recommend, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, spannable.length() - 7, spannable.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mArticleTitle.setText(spannable);
    }


    private void showCommentView() {
        SubBean.Statistics statistics = mSubBean.getStatistics();
        if (statistics == null)
            return;
        mCommentView.setShareTitle(mSubBean.getTitle());
        mCommentView.setTitle(String.format("%s (%d)", getResources().getString(R.string.answer_hint), mSubBean.getStatistics().getComment()));
        mCommentView.init(mSubBean.getId(),
                mSubBean.getType(),
                2,
                mSubBean.getStatistics().getComment(), new CommentView.OnCommentClickListener() {
                    @Override
                    public void onClick(View view, Comment comment) {

                    }

                    @Override
                    public void onShowComment(View view) {

                    }
                });
    }

    protected abstract View getHeaderView();

    private onCompleteListener mOnCompleteListener;

    public void setOnCompleteListener(onCompleteListener onCompleteListener) {
        mOnCompleteListener = onCompleteListener;
    }

    public interface onCompleteListener {
        void onComplete();
    }
}
