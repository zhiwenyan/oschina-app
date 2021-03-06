package com.steven.oschina.bean.sub;

import android.text.TextUtils;

import com.oschina.client.base_library.log.LogUtils;
import com.steven.oschina.api.RetrofitClient;

import java.io.Serializable;

/**
 * Description:
 * Data：7/5/2018-11:36 AM
 *
 * @author yanzhiwen
 */
public class SubTab implements Serializable {
    public static final String TOKEN_NEWS = "e6142fa662bc4bf21083870a957fbd20";
    public static final String TOKEN_QA = "98d04eb58a1d12b75d254deecbc83790";
    public static final String TOKEN_BLOG = "df985be3c5d5449f8dfb47e06e098ef9";

    public static final String TAG_NEW = "new";
    public static final String TAG_HOT = "hot";

    public static final int BANNER_CATEGORY_NEWS = 1;
    public static final int BANNER_CATEGORY_EVENT = 3;
    public static final int BANNER_CATEGORY_BLOG = 4;

    private String token;
    private String name;
    private boolean fixed;
    private boolean needLogin;
    private String tag;
    private int type;
    private int subtype;
    private int order;
    private String href;
    private Banner banner;
    private boolean isActived;


    public class Banner implements Serializable {
        private int catalog;
        private String href;

        public int getCatalog() {
            return catalog;
        }

        public void setCatalog(int catalog) {
            this.catalog = catalog;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public String toString() {
            return "Banner{" +
                    "catalog=" + catalog +
                    ", href='" + href + '\'' +
                    '}';
        }
    }

    @Override
    public int hashCode() {
        return this.token == null ? 0 : this.token.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof SubTab) {
            SubTab tab = ( SubTab ) obj;
            if (tab.getToken() == null) return false;
            if (this.token == null) return false;
            return tab.getToken().equals(this.token);
        }
        return false;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getHref() {
        String url = String.format("action/apiv2/sub_list?token=%s", TextUtils.isEmpty(token) ? "" : token);
        url = String.format(RetrofitClient.API_URL, url);
        LogUtils.e("SubTab getHref:" + url);
        return url;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "SubTab{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", fixed=" + fixed +
                ", needLogin=" + needLogin +
                ", tag='" + tag + '\'' +
                ", type=" + type +
                ", subtype=" + subtype +
                ", order=" + order +
                ", href='" + href + '\'' +
                ", banner=" + banner +
                '}';
    }
}