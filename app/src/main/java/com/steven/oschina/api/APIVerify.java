package com.steven.oschina.api;


import com.steven.oschina.utils.AES;
import com.steven.oschina.utils.MD5;

/**
 * API验证
 * Created by huanghaibin on 2018/4/13.
 */

public final class APIVerify {

    public static String getVerifyString() {
//        if (BuildConfig.DEBUG) {
//            return MD5.get32MD5Str(AES.encryptByBase64(BuildConfig.APPLICATION_ID));
//        }
//        return MD5.get32MD5Str(MD5.get32MD5Str(BuildConfig.AES_KEY) + MD5.get32MD5Str(BuildConfig.VERSION_NAME) + AES.encryptByBase64(BuildConfig.APPLICATION_ID));
        return MD5.get32MD5Str(MD5.get32MD5Str("osc[oschina].app") + MD5.get32MD5Str("v4.1.8 (1807020800)") + AES.encryptByBase64("net.oschina.app"));

    }

}
