package com.steven.oschina.bean.sub;

import java.io.Serializable;

/**
 * Description:
 * Data：7/12/2018-4:28 PM
 *
 * @author yanzhiwen
 */
public class UserRelation implements Serializable {
    public static final int RELATION_ALL = 1;
    public static final int RELATION_ONLY_YOU = 2;
    public static final int RELATION_ONLY_HER = 3;
    public static final int RELATION_NONE = 4;
    private int relation;
    private String author;
    private long authorId;

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
