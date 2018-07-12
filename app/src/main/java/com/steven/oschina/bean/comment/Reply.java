package com.steven.oschina.bean.comment;

import com.steven.oschina.bean.sub.Author;

import java.io.Serializable;

/**
 * Description:
 * Data：7/11/2018-3:21 PM
 *
 * @author yanzhiwen
 */
public class Reply implements Serializable {
    private long id;
    private Author author;
    private String content;
    private String pubDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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
        return "Reply{" +
                "id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
