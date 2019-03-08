package com.steven.oschina.bean.tweet;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Description:
 * Data：8/20/2018-11:58 AM
 *
 * @author yanzhiwen
 */
public class Topic implements Serializable {
    private int joinCount;
    private int id;
    private String href;
    private int sort;
    private String title;
    private String content;
    private String desc;
    private Tweet[] tweets;


    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tweet[] getTweets() {
        return tweets;
    }



    public String getDesc() {
        return desc;
    }

    public void setTweets(Tweet[] tweets) {
        this.tweets = tweets;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "joinCount=" + joinCount +
                ", id=" + id +
                ", href='" + href + '\'' +
                ", sort=" + sort +
                ", title='" + title + '\'' +
                ", tweets=" + Arrays.toString(tweets) +
                '}';
    }
}
