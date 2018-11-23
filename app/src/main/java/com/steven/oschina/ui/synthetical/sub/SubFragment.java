package com.steven.oschina.ui.synthetical.sub;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.sub.SubTab;
import com.steven.oschina.header.BlogHeaderView;
import com.steven.oschina.header.HeaderView;
import com.steven.oschina.ui.adapter.BlogSubAdapter;
import com.steven.oschina.ui.adapter.NewsSubAdapter;
import com.steven.oschina.ui.adapter.QuestionSubAdapter;
import com.steven.oschina.ui.synthetical.sub.viewmodel.SubViewModel;
import com.steven.oschina.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;


public class SubFragment extends BaseRecyclerFragment1<SubBean, SubViewModel> {
    private List<SubBean> mSubBeans;
    protected SubTab mSubTab;
    private HeaderView mHeaderView;
    private String CACHE_NAME;

    public static SubFragment newInstance(SubTab subTab) {
        SubFragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mSubTab = ( SubTab ) bundle.getSerializable("sub_tab");
    }

    @Override
    public void initData() {
        mSubBeans = new ArrayList<>();
        CACHE_NAME = mSubTab.getToken();
        super.initData();
    }

    @Override
    public void requestCacheData() {
        List<SubBean> subBeans = CacheManager.readListJson(mContext, mSubTab.getToken(), SubBean.class);
        if (subBeans != null) {
            showSubList(subBeans);
        }
        List<Banner> banners = CacheManager.readListJson(mContext, mSubTab.getToken() + "banner"
                + mSubTab.getType(), Banner.class);
        if (banners != null) {
            mHeaderView.showBanners(banners);
        }
    }

    public void initHeader() {
        if (mSubTab.getBanner() != null) {
            if (mSubTab.getBanner().getCatalog() == SubTab.BANNER_CATEGORY_NEWS) {
                // mHeaderView = new NewsHeaderView(mContext, mSubTab.getBanner().getHref(), mSubTab.getToken() + "banner" + mSubTab.getType());
            } else if (mSubTab.getBanner().getCatalog() == SubTab.BANNER_CATEGORY_EVENT) {
                // mEventHeaderView = new EventHeaderView(mContext, getImgLoader(), mSubTab.getBanner().getHref(), mSubTab.getToken() + "banner" + mSubTab.getType());
            } else if (mSubTab.getBanner().getCatalog() == SubTab.BANNER_CATEGORY_BLOG) {
                mHeaderView = new BlogHeaderView(mContext, mSubTab.getBanner().getHref(), mSubTab.getToken() + "banner" + mSubTab.getType(), SubTab.BANNER_CATEGORY_BLOG);
            }
            mHeaderView.requestBanner(mSubTab.getBanner().getCatalog());
            mSwipeRefreshRv.addHeaderView(mHeaderView);

        }
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        mViewModel.getSubList(mSubTab.getToken(), nextPageToken).observe(this, new Observer<PageBean<SubBean>>() {
            @Override
            public void onChanged(@Nullable PageBean<SubBean> result) {
                mSwipeRefreshRv.setRefreshing(!mRefreshing);
                mNextPageToken = nextPageToken;
                if (result.getItems().size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showSubList(result.getItems());
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, result.getItems());
            }
        });

    }


    private void showSubList(List<SubBean> subBeans) {
        if (mRefreshing) {
            mSubBeans.clear();
        }
        mSubBeans.addAll(subBeans);
        if (mAdapter == null) {
            mAdapter = getAdapter();
            mSwipeRefreshRv.setAdapter(mAdapter);
            initHeader();
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
            SubBean subBean = mSubBeans.get(position);
            switch (mSubTab.getType()) {
                case News.TYPE_SOFTWARE:
                    //SoftwareDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_QUESTION:
                    QuestionDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_BLOG:
                    BlogDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_TRANSLATE:
                    NewsDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_EVENT:
                    //EventDetailActivity.show(mContext, subBean);
                    break;
                case News.TYPE_NEWS:
                    NewsDetailActivity.show(mContext, subBean);
                    break;
                default:
                    UIHelper.showUrlRedirect(mContext, mSubTab.getHref());
                    break;
            }
        });
    }

    private CommonRecyclerAdapter<SubBean> getAdapter() {
        if (mSubTab.getType() == News.TYPE_BLOG)
            return new BlogSubAdapter(getActivity(), mSubBeans, R.layout.item_sub_news);
        else if (mSubTab.getType() == News.TYPE_QUESTION)
            return new QuestionSubAdapter(getActivity(), mSubBeans, R.layout.item_list_sub_question);
        return new NewsSubAdapter(getActivity(), mSubBeans, R.layout.item_sub_news);
    }
}

