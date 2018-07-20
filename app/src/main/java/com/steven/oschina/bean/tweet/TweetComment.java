package com.steven.oschina.bean.tweet;

import com.steven.oschina.bean.sub.Author;

import java.io.Serializable;

/**
 * Description:
 * Data：7/18/2018-3:58 PM
 *
 * @author yanzhiwen
 */
public class TweetComment implements Serializable{
    private long id;
    private String content;
    private String pubDate;
    private int appClient;
    private Author author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getAppClient() {
        return appClient;
    }

    public void setAppClient(int appClient) {
        this.appClient = appClient;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
