package com.steven.oschina.bean.sub;

/**
 * Description:
 * Data：8/8/2018-2:06 PM
 *
 * @author yanzhiwen
 */
public class Report {
    private static final long serialVersionUID = 1L;

    public static final byte TYPE_ARTICLE = 0x09;
    public static final byte TYPE_QUESTION = 0x02;// 问题
    public static final byte TYPE_BLOG = 0x03;

    private long objId;//需要举报的id
    private String url;// 举报的链接地址
    private byte objType;// 举报的类型
    private int reason;// 原因
    private String otherReason;// 其他原因

    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte getObjType() {
        return objType;
    }

    public void setObjType(byte objType) {
        this.objType = objType;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }
}
