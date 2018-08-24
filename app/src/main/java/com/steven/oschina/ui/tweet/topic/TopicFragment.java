package com.steven.oschina.ui.tweet.topic;


import android.content.Intent;
import android.os.Bundle;

import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.ui.adapter.TopicAdapter;

import java.util.ArrayList;
import java.util.List;


public class TopicFragment extends BaseRecyclerFragment<Topic> {
    private static final String CACHE_NAME = "hot_topic";
    private static final String TOPIC_TYPE = "2";
    private List<Topic> mTopics;

    public static TopicFragment newInstance() {
        Bundle args = new Bundle();
        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        mTopics = new ArrayList<>();
        super.initData();
    }

    @Override
    public void requestCacheData() {
        super.requestCacheData();
        List<Topic> topics = CacheManager.readListJson(mContext, CACHE_NAME, Topic.class);
        if (topics != null) {
            showTopicList(topics);
        }

    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getHotTopic(TOPIC_TYPE, nextPageToken), new HttpCallback<Topic>() {
            @Override
            public void onSuccess(List<Topic> topics, String nextPageToken) {
                super.onSuccess(topics, nextPageToken);
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (topics.size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showTopicList(topics);
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, topics);
            }
        });
    }

    private void showTopicList(List<Topic> topics) {
        if (mRefreshing) {
            mTopics.clear();
        }
        mTopics.addAll(topics);
        if (mAdapter == null) {
            mAdapter = new TopicAdapter(mContext, mTopics, R.layout.item_list_topic);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
            Topic topic = mTopics.get(position);
            Intent intent = new Intent(mContext, TopicDetailActivity.class);
            intent.putExtra("topic", topic);
            intent.putExtra("position", position);
            startActivity(intent);
        });
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
}
