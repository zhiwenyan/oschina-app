package com.steven.oschina.api;

import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.search.SearchBean;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetComment;
import com.steven.oschina.bean.tweet.TweetLike;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Description:
 * Data：7/4/2018-5:41 PM
 *
 * @author yanzhiwen
 */
public interface ServiceApi {

    @GET("sub_list?")
    Call<ResultBean<PageBean<SubBean>>> getSubList(@Query("token") String token, @Query("pageToken") String nextPageToken);

    //英文
    @GET("get_articles?")
    Call<ResultBean<PageBean<Article>>> getEnglishArticles(@Query("ident") String ident, @Query("type") int type,
                                                           @Query("pageToken") String nextPageToken);

    //推荐
    @GET("get_articles?")
    Call<ResultBean<PageBean<Article>>> getArticles(@Query("ident") String ident, @Query("pageToken") String nextPageToken);

    //动弹
    @GET("tweet_list?")
    Call<ResultBean<PageBean<Tweet>>> getTweetList(@QueryMap Map<String, Object> params);

    //banner
    @GET("banner?")
    Call<ResultBean<PageBean<Banner>>> getBanner(@Query("catalog") int catalog);

    //搜索
    @FormUrlEncoded
    @POST("search_articles?")
    Call<ResultBean<SearchBean>> searchArticles(@FieldMap Map<String, Object> params);


    //banner
    @GET("detail?")
    Call<ResultBean<SubBean>> getDetail(@QueryMap Map<String, Object> params);

    @GET("getArticleDetail?")
    Call<ResultBean<Article>> getArticleDetail(@Query("ident") String ident, @Query("key") String key);

    @GET("get_article_recommends?")
    Call<ResultBean<PageBean<Article>>> getArticleRecommends(@QueryMap Map<String, Object> params);

    @GET("tweet?")
    Call<ResultBean<Tweet>> getTweetDetail(@Query("id") long id);

    @GET("tweet_likes?")
    Call<ResultBean<PageBean<TweetLike>>> getTweetLikeList(@Query("sourceId") long sourceId, @Query("pageToken") String pageToken);

    @GET("tweet_comments?")
    Call<ResultBean<PageBean<TweetComment>>> getTweetCommentList(@Query("sourceId") long sourceId, @Query("pageToken") String pageToken);


}
