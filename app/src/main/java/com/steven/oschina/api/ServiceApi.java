package com.steven.oschina.api;

import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.tweet.Tweet;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
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


}
