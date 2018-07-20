package com.steven.oschina.bean.tweet;

import com.steven.oschina.bean.sub.Author;

/**
 * Description:
 * Data：7/18/2018-3:36 PM
 *
 * @author yanzhiwen
 */
public class TweetLike {
    private String pubDate;
    private Author author;
    private boolean liked;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
