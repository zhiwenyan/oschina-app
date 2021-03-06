package com.steven.oschina;

/**
 * Description:
 * Data：7/6/2018-6:14 PM
 *
 * @author yanzhiwen
 */
public final class Platform {
    private final static int CLIENT_MOBILE = 2;
    private final static int CLIENT_ANDROID = 3;
    private final static int CLIENT_IPHONE = 4;
    private final static int CLIENT_WINDOWS_PHONE = 5;
    private final static int CLIENT_WECHAT = 6;

    public static String getPlatform(int platfrom) {
        int resId;
        switch (platfrom) {
            case CLIENT_MOBILE:
                resId = R.string.from_mobile;
                break;
            case CLIENT_ANDROID:
                resId = R.string.from_android;
                break;
            case CLIENT_IPHONE:
                resId = R.string.from_iphone;
                break;
            case CLIENT_WINDOWS_PHONE:
                resId = R.string.from_windows_phone;
                break;
            case CLIENT_WECHAT:
                resId = R.string.from_wechat;
                break;
            default:
                return "";
        }
        return "";

    }
}