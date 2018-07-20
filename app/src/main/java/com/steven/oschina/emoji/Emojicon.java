package com.steven.oschina.emoji;

/**
 * Description:
 * Data：7/19/2018-10:45 AM
 *
 * @author yanzhiwen
 */
public class Emojicon {
    private final int resId; // 图片资源地址
    private final int value; // 一个emoji对应唯一一个value
    private final String emojiStr; // emoji在互联网传递的字符串
    private final String remote;

    public Emojicon(int id, int value, String name, String remote) {
        this.resId = id;
        this.value = value;
        this.emojiStr = name;
        this.remote = remote;
    }

    public int getResId() {
        return resId;
    }

    public String getRemote() {
        return remote;
    }

    public int getValue() {
        return value;
    }

    public String getEmojiStr() {
        return emojiStr;
    }
}
