package com.steven.oschina.ui.synthetical.sub;


import android.os.Bundle;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.sub.SubTab;
import com.steven.oschina.ui.adapter.BlogSubAdapter;
import com.steven.oschina.ui.adapter.NewsSubAdapter;
import com.steven.oschina.ui.adapter.QuestionSubAdapter;

import java.util.ArrayList;
import java.util.List;


public class SubFragment extends BaseRecyclerFragment<SubBean> {
    private List<SubBean> mSubBeans;
    protected SubTab mSubTab;

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
        mSubTab = (SubTab) bundle.getSerializable("sub_tab");
    }

    @Override
    public void initData() {
        mSubBeans = new ArrayList<>();
        super.initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onRequestData("");

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        onRequestData(mNextPageToken);
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getSubList(mSubTab.getToken(), nextPageToken), new HttpCallback<SubBean>() {
            @Override
            public void onSuccess(List<SubBean> list, String nextPageToken) {
                if (mSwipeRefreshRecyclerView.isRefreshing()) {
                    mSwipeRefreshRecyclerView.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (list.size() == 0) {
                    mSwipeRefreshRecyclerView.showLoadComplete();
                    return;
                }
                showSubList(list);
            }
        });

    }


    private void showSubList(List<SubBean> subBeans) {
        if (mSwipeRefreshRecyclerView.getStatus() == 1) {
            mSubBeans.clear();
        }
        mSubBeans.addAll(subBeans);
        if (mAdapter == null) {
            mAdapter = getAdapter();
            mSwipeRefreshRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
        });
    }

    private CommonRecyclerAdapter<SubBean> getAdapter() {
        if (mSubTab.getType() == News.TYPE_BLOG)
            return new BlogSubAdapter(getActivity(), mSubBeans, R.layout.item_sub_news);
        else if (mSubTab.getType() == News.TYPE_QUESTION)
            return new QuestionSubAdapter(getActivity(), mSubBeans, R.layout.item_sub_news);
        return new NewsSubAdapter(getActivity(), mSubBeans, R.layout.item_sub_news);
    }
}

