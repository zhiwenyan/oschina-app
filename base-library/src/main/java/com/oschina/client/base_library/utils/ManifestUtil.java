package com.oschina.client.base_library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;


public class ManifestUtil {

    public static String marketCode = null;

    public static String getApkVersionName(Context context) {
        try {
            String version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
            return version;
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static String getApkVersionCode(Context context) {
        try {
            int version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
            return String.valueOf(version);
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static void initMarketCode(Context context) {

        if (marketCode != null) {
            return;
        }

        marketCode = getMarketCode(context);
    }

    public static String getMarketCode() {
        return marketCode;
    }

    public static String getMarketCode(Context context) {
        //APPLICATION_CHANNEL
        String code = getMetaData(context, "UMENG_CHANNEL");
        if (code != null) {
            return code;
        }
        return "A1";
    }

}
