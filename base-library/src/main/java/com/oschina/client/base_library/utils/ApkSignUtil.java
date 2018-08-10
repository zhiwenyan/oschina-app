package com.oschina.client.base_library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

public class ApkSignUtil {

    //may return null
    public static String getSign(Context context) {
        String packageName = context.getPackageName();
        Signature[] signs = getRawSignature(context, packageName);
        if ((signs == null) || (signs.length == 0)) {
            return null;
        } else {
            Signature sign = signs[0];
            return MD5Util.md5(sign.toByteArray());
        }
    }

    public static Signature[] getRawSignature(Context context, String packageName) {
        if ((packageName == null) || (packageName.length() == 0)) {
            return null;
        }
        PackageManager pkgMgr = context.getPackageManager();
        PackageInfo info;
        try {
            info = pkgMgr.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        if (info == null) {
            return null;
        }
        return info.signatures;
    }


}
