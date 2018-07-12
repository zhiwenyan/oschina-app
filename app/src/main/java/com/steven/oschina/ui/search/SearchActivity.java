package com.steven.oschina.ui.search;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerActivity;
import com.steven.oschina.bean.search.SearchBean;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.ui.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Data：7/10/2018-4:07 PM
 *
 * @author yanzhiwen
 */
public class SearchActivity extends BaseRecyclerActivity {
    private static final int TYPE_DEFAULT = -1;
    private static final int ORDER_DEFAULT = 1;
    private static final int ORDER_HOT = 2;
    private static final int ORDER_TIME = 3;
    @BindView(R.id.view_searcher)
    SearchView mViewSearcher;
    @BindView(R.id.recyclerViewHistory)
    RecyclerView mRVHistory;
    private int mOrder;
    private List<Article> mArticles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        mOrder = ORDER_DEFAULT;
        mArticles = new ArrayList<>();
        super.initData();
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        String keyword = mViewSearcher.getQuery().toString();
        if (TextUtils.isEmpty(keyword)) {
            showToast("请输入关键字");
            return;
        }
        mArticles.clear();
        mRefreshing = true;
        searchByKeyword("");
    }

    private void searchByKeyword(String nextPageToken) {
        String keyword = mViewSearcher.getQuery().toString();
        if (mRefreshing && !TextUtils.isEmpty(keyword)) {
            mSwipeRefreshRv.setRefreshing(true);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("type", TYPE_DEFAULT);
        params.put("order", mOrder);
        params.put("keyword", keyword);
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pageToken", nextPageToken);
        }
        HttpUtils.post(RetrofitClient.getServiceApi().searchArticles(params), new HttpCallback<SearchBean>() {
            @Override
            public void onSuccess(SearchBean result) {
                super.onSuccess(result);
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = result.getNextPageToken();
                if (result.getArticles().size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showSearchList(result.getArticles());
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        searchByKeyword("");
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        searchByKeyword(mNextPageToken);
    }

    private void showSearchList(List<Article> result) {
        if (mRefreshing) {
            mArticles.clear();
        }
        mArticles.addAll(result);
        if (mAdapter == null) {
            mAdapter = new SearchAdapter(this, mArticles, R.layout.item_sub_news);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {

        });
    }
}
