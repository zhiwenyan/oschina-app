package com.steven.oschina.ui.synthetical.article;


import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.ui.adapter.ArticleAdapter;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.ui.synthetical.sub.viewmodel.ArticleViewModel;
import com.steven.oschina.utils.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 英文类型的文章
 */
public class EnglishArticleFragment extends BaseRecyclerFragment1<Article, ArticleViewModel> {
    //英文类型的文章
    private static final int TYPE_ENGLISH = 8000;
    private List<Article> mArticles;
    private static final String CACHE_NAME = "english_article_list";

    public static EnglishArticleFragment newInstance() {
        return new EnglishArticleFragment();
    }

    @Override
    public void initData() {
        mArticles = new ArrayList<>();
        super.initData();
    }

    @Override
    public void requestCacheData() {
        List<Article> articles = CacheManager.readListJson(mContext, CACHE_NAME, Article.class);
        if (articles != null) {
            showArticleList(articles);
        }
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        Map<String, Object> params = new HashMap<>();
        params.put("ident", OSCSharedPreference.getInstance().getDeviceUUID());
        params.put("type", TYPE_ENGLISH);
        params.put("pageToken", nextPageToken);
        mViewModel.getEnglishArticles(params).observe(this, result -> {
            mNextPageToken = nextPageToken;
            if (result.getItems().size() == 0) {
                mSwipeRefreshRv.showLoadComplete();
                return;
            }
            CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, result.getItems());
            showArticleList(result.getItems());
        });

    }

    private void showArticleList(List<Article> articles) {
        mSwipeRefreshRv.setRefreshing(!mRefreshing);
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
        mAdapter.setOnItemClickListener(position -> {
            Article article = mArticles.get(position);
            int type = article.getType();
            long id = article.getOscId();
            switch (type) {
                case News.TYPE_SOFTWARE:
                    //   SoftwareDetailActivity.show(mContext, id);
                    break;
                case News.TYPE_QUESTION:
                    QuestionDetailActivity.show(mContext, id);
                    break;
                case News.TYPE_BLOG:
                    BlogDetailActivity.show(mContext, id);
                    break;
                case News.TYPE_TRANSLATE:
                    NewsDetailActivity.show(mContext, id, News.TYPE_TRANSLATE);
                    break;
                case News.TYPE_EVENT:
                    //        EventDetailActivity.show(mContext, id);
                    break;
                case News.TYPE_NEWS:
                    NewsDetailActivity.show(mContext, id);
                    break;
                case Article.TYPE_ENGLISH:
                    EnglishArticleDetailActivity.show(mContext, article);
                    break;
                default:
                    UIHelper.showUrlRedirect(mContext, article.getUrl());
                    break;
            }
        });
    }
}
