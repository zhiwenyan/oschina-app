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
 * 推荐的文章
 */
public class ArticleFragment extends BaseRecyclerFragment<Article> {
    private List<Article> mArticles;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onRequestData("");
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
        super.initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        onRequestData(mNextPageToken);

    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getArticles(OSCSharedPreference.getInstance().getDeviceUUID(),
                mNextPageToken), new HttpCallback<Article>() {
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
