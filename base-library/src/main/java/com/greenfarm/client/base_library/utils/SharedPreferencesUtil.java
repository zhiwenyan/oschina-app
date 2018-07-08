package com.greenfarm.client.base_library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferencesUtil {

    public static void setSharedPreferences(Context ctx, String key, String value) {
        if (ctx == null || key == null || value == null)
            return;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPreferencesValue(Context ctx, String key, String defValue) {
        if (ctx == null || key == null || defValue == null)
            return "";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sp.getString(key, defValue);
    }

    public static void setSharedPreferences(Context ctx, String key, boolean value) {
        if (ctx == null || key == null)
            return;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getSharedPreferencesValue(Context ctx, String key, boolean defValue) {
        if (ctx == null || key == null)
            return false;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sp.getBoolean(key, defValue);
    }

//    public static void setUser(Context ctx, User user, String strJson) {
//        if (user == null) {
//            setSharedPreferences(ctx, "user", "");
//            return;
//        }
//        String encJson;
//        try {
//            encJson = DesUtil.getInstance().encrypt(strJson);
//        } catch (Exception e) {
//            encJson = strJson;
//        }
//        setSharedPreferences(ctx, "user", encJson);
//    }
//
//    public static User getUser(Context ctx) {
//        String encJson = getSharedPreferencesValue(ctx, "user", "");
//        if (TextUtils.isEmpty(encJson)) {
//            return null;
//        }
//        String json;
//        try {
//            json = DesUtil.getInstance().decrypt(encJson);
//        } catch (Exception e) {
//            json = encJson;
//        }
//        return new Gson().fromJson(json, User.class);
//    }
}
