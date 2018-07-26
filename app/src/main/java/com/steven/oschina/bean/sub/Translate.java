package com.steven.oschina.bean.sub;

import java.io.Serializable;

/**
 * Description:
 * Data：7/25/2018-4:11 PM
 *
 * @author yanzhiwen
 */
public class Translate implements Serializable {
    private int index;
    private String src;
    private String dest;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }


    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
