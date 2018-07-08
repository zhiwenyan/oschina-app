package com.steven.oschina.ui.synthetical;


import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 英文
 */
public class EnglishArticleFragment extends BaseRecyclerFragment<Article> {
    //获取英文
    private static final int TYPE_ENGLISH = 8000;
    private List<Article> mArticles;

    public static EnglishArticleFragment newInstance() {
        return new EnglishArticleFragment();
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
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
        HttpUtils.get(RetrofitClient.getServiceApi().getEnglishArticles(OSCSharedPreference.getInstance().getDeviceUUID(),
                TYPE_ENGLISH, mNextPageToken), new HttpCallback<Article>() {
            @Override
            public void onSuccess(List<Article> articles, String nextPageToken) {
                if (mSwipeRefreshRecyclerView.isRefreshing()) {
                    mSwipeRefreshRecyclerView.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (articles.size() == 0) {
                    mSwipeRefreshRecyclerView.showLoadComplete();
                    return;
                }
                showArticleList(articles);
            }
        });

    }

    private void showArticleList(List<Article> articles) {
        if (mSwipeRefreshRecyclerView.getStatus() == 1) {
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
            mSwipeRefreshRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {

        });
    }
}
