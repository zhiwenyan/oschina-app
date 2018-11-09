package com.steven.oschina.api;

import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.comment.Comment;
import com.steven.oschina.bean.search.SearchBean;
import com.steven.oschina.bean.simple.User;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetComment;
import com.steven.oschina.bean.tweet.TweetLike;
import com.steven.oschina.ui.web.Rule;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
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
    int CATALOG_ALL = 0;
    int CATALOG_SOFTWARE = 1;
    int CATALOG_QUESTION = 2;
    int CATALOG_BLOG = 3;
    int CATALOG_TRANSLATION = 4;
    int CATALOG_EVENT = 5;
    int CATALOG_NEWS = 6;
    int CATALOG_TWEET = 100;

    int COMMENT_SOFT = 1;    // 软件推荐-不支持(默认软件评论其实是动弹)
    int COMMENT_QUESTION = 2;    // 讨论区帖子
    int COMMENT_BLOG = 3;    // 博客
    int COMMENT_TRANSLATION = 4;    // 翻译文章
    int COMMENT_EVENT = 5;    // 活动类型
    int COMMENT_NEWS = 6;    // 资讯类型
    int COMMENT_TWEET = 100;  // 动弹

    int COMMENT_HOT_ORDER = 2; //热门评论顺序
    int COMMENT_NEW_ORDER = 1; //最新评论顺序

    int CATALOG_BANNER_NEWS = 1; // 资讯Banner
    int CATALOG_BANNER_BLOG = 2; // 博客Banner
    int CATALOG_BANNER_EVENT = 3; // 活动Banner

    int CATALOG_BLOG_NORMAL = 1; // 最新
    int CATALOG_BLOG_HEAT = 2; // 最热
    int CATALOG_BLOG_RECOMMEND = 3; //推荐

    String CATALOG_NEWS_DETAIL = "news";
    String CATALOG_TRANSLATE_DETAIL = "translation";
    String CATALOG_SOFTWARE_DETAIL = "software";

    String LOGIN_WEIBO = "weibo";
    String LOGIN_QQ = "qq";
    String LOGIN_WECHAT = "wechat";
    String LOGIN_CSDN = "csdn";

    int REGISTER_INTENT = 1;
    int RESET_PWD_INTENT = 2;

    int REQUEST_COUNT = 0x50;//请求分页大小

    int TYPE_USER_FLOWS = 1;//你关注的人

    int TYPE_USER_FANS = 2;//关注你的人
    //栏目
    @GET("sub_list?")
    Call<ResultBean<PageBean<SubBean>>> getSubList(@Query("token") String token, @Query("pageToken") String nextPageToken);

    //英文
    @GET("get_articles?")
    Call<ResultBean<PageBean<Article>>> getEnglishArticles(@QueryMap Map<String, Object> map);

    //推荐
    @GET("get_articles?")
    Call<ResultBean<PageBean<Article>>> getArticles(@Query("ident") String ident, @Query("pageToken") String nextPageToken);

    //动弹
    @GET("tweet_list?")
    Call<ResultBean<PageBean<Tweet>>> getTweetList(@QueryMap Map<String, Object> params);

    //热门话题
    @GET("topic?")
    Call<ResultBean<PageBean<Topic>>> getHotTopic(@Query("type") String type, @Query("pageToken") String nextPageToken);

    @GET("topic_tweets?")
    Call<ResultBean<PageBean<Tweet>>> getTopicTweets(@QueryMap Map<String, Object> map);

    //banner
    @GET("banner?")
    Call<ResultBean<PageBean<Banner>>> getBanner(@Query("catalog") int catalog);

    //搜索
    @FormUrlEncoded
    @POST("search_articles?")
    Call<ResultBean<SearchBean>> searchArticles(@FieldMap Map<String, Object> params);

    //detail
    @GET("detail?")
    Call<ResultBean<SubBean>> getDetail(@QueryMap Map<String, Object> params);

    @GET("get_article_detail?")
    Call<ResultBean<Article>> getArticleDetail(@Query("ident") String ident, @Query("key") String key);

    @GET("get_article_detail?")
    Call<ResultBean<Article>> getEnglishArticleDetailEN(@Query("ident") String ident, @Query("key") String key, @Query("type") int type);

    @GET("article_translate?")
    Call<ResultBean<Article>> article_translate(@Query("key") String key, @Query("type") int type);

    @GET("get_article_recommends?")
    Call<ResultBean<PageBean<Article>>> getArticleRecommends(@QueryMap Map<String, Object> params);

    @GET("tweet?")
    Call<ResultBean<Tweet>> getTweetDetail(@Query("id") long id);

    @GET("tweet_likes?")
    Call<ResultBean<PageBean<TweetLike>>> getTweetLikeList(@Query("sourceId") long sourceId, @Query("pageToken") String pageToken);

    @GET("tweet_comments?")
    Call<ResultBean<PageBean<TweetComment>>> getTweetCommentList(@Query("sourceId") long sourceId, @Query("pageToken") String pageToken);

    @FormUrlEncoded
    @POST("account_login?")
    Call<ResultBean<User>> login(@Field("account") String username, @Field("password") String password);


    @GET("get_article_rules?")
    Call<ResultBean<Rule>> get_article_rules(@Query("url") String url);


    @GET("comment_list?")
    Call<ResultBean<PageBean<Comment>>> comment_list(@QueryMap Map<String, Object> params);


}
