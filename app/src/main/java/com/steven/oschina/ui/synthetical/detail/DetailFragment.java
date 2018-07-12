package com.steven.oschina.ui.synthetical.detail;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
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
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.media.Util;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.sub.UserRelation;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.OWebView;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.widget.SwipeRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DetailFragment extends BaseRecyclerFragment {
    private SubBean mSubBean;
    protected OWebView mWebView;
    protected View mHeaderView;
    private NestedScrollView mViewScroller;
    protected SwipeRefreshRecyclerView mRefreshRv;
    private List<Article> mArticles;
    private ImageView mAvatarIv;
    private TextView mArtcleTitle;
    private TextView mAuthorName;
    private TextView mOrigin;
    private TextView mPubDate;
    private TextView mRecommed;
    private TextView mTextAbstract;
    private Button mBtnRelation;

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
            mViewScroller = mHeaderView.findViewById(R.id.lay_nsv);
            mAvatarIv = mHeaderView.findViewById(R.id.iv_avatar);
            mArtcleTitle = mHeaderView.findViewById(R.id.tv_title);
            mAuthorName = mHeaderView.findViewById(R.id.tv_name);
            mOrigin = mHeaderView.findViewById(R.id.tv_origin);
            mPubDate = mHeaderView.findViewById(R.id.tv_pub_date);
            mRecommed = mHeaderView.findViewById(R.id.tv_recommend);
            mBtnRelation =  mHeaderView.findViewById(R.id.btn_relation);
            mTextAbstract = mHeaderView.findViewById(R.id.tv_detail_abstract);
        }
        mRefreshRv = mRootView.findViewById(R.id.swipe_refresh_recycler);
        if (mContext == null) return;
        //码云挂件替换
        mSubBean.setBody(mSubBean.getBody().replaceAll("(|<pre>)<code>&lt;script src='(//gitee.com/[^>]+)'&gt;&lt;/script&gt;\\s*</code>(|</pre>)",
                "<code><script src='https:$2'></script></code>"));
        mWebView.loadDetailDataAsync(mSubBean.getBody());
        super.initData();
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        Map<String, Object> params = new HashMap<>();
        String ident = OSCSharedPreference.getInstance().getDeviceUUID();
        params.put("key", String.format("osc_%s_%s", mSubBean.getType(), mSubBean.getId()));
        params.put("ident", ident);
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pageToken", nextPageToken);
        }
        HttpUtils.get(RetrofitClient.getServiceApi().getArticleRecommends(params), new HttpCallback<Article>() {
            @Override
            public void onSuccess(List<Article> result, String nextPageToken) {
                super.onSuccess(result, nextPageToken);
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (result.size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showArticleList(result);
            }
        });
    }

    private void showAuthor() {
        Author author = mSubBean.getAuthor();
        ImageLoader.load(mContext, mAvatarIv, author.getPortrait());
        mAuthorName.setText(author.getName());
        mPubDate.setText(StringUtils.formatYearMonthDay(mSubBean.getPubDate()));
        mTextAbstract.setText(mSubBean.getSummary());
        if (TextUtils.isEmpty(mSubBean.getSummary())) {
            mRootView.findViewById(R.id.line3).setVisibility(View.GONE);
            mRootView.findViewById(R.id.line4).setVisibility(View.GONE);
            mTextAbstract.setVisibility(View.GONE);
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
        mArtcleTitle.setText(spannable);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        onRequestData(mNextPageToken);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onRequestData("");
    }

    private void showArticleList(List<Article> articles) {
        if (mRefreshing) {
            mArticles.clear();
        }
        mArticles.addAll(articles);
        if (mAdapter == null) {
            mAdapter = new ArticleAdapter(this.getActivity(), mArticles, (article, position) -> {
                String[] images = article.getImgs();
                if (images == null || images.length == 0) {
                    return R.layout.item_article_not_img;
                }
                if (images.length < 3)
                    return R.layout.item_article_one_img;
                return R.layout.item_article_three_img;
            });
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mRefreshRv.addHeaderView(mHeaderView);
        showAuthor();

    }

    protected abstract View getHeaderView();
}
