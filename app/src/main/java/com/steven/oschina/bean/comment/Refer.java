package com.steven.oschina.bean.comment;

import java.io.Serializable;

/**
 * Description:
 * Data：7/11/2018-3:21 PM
 *
 * @author yanzhiwen
 */
public class Refer implements Serializable{
    private String author;
    private String content;
    private String pubDate;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Refer{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
