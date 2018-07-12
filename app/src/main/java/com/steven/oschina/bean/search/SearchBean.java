package com.steven.oschina.bean.search;

import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Author;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Data：7/10/2018-4:14 PM
 *
 * @author yanzhiwen
 */
public class SearchBean implements Serializable {
    private int authorsCount;
    private int softwareCount;
    private int articleCount;
    private String nextPageToken;
    private String prevPageToken;
    private List<Author> authors;
    private List<Article> softwares;
    private List<Article> articles;

    public int getAuthorsCount() {
        return authorsCount;
    }

    public void setAuthorsCount(int authorsCount) {
        this.authorsCount = authorsCount;
    }

    public int getSoftwareCount() {
        return softwareCount;
    }

    public void setSoftwareCount(int softwareCount) {
        this.softwareCount = softwareCount;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Article> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Article> softwares) {
        this.softwares = softwares;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }
}
