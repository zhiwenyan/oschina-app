package com.oschina.client.base_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceUtil {
    /**
     * DeviceId
     *
     * @param act
     * @return
     */
    public static String getImei(Context act) {
        try {
            @SuppressLint("HardwareIds")
            String imei = ((TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "888";
    }

    public static String getDeviceIdString() {
        try {
            String mSzDevIDShort = "35" + // we make this look like a valid IMEI
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                    + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                    + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                    + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                    + Build.USER.length() % 10; // 13 digits
            return mSzDevIDShort;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "888";
    }

    public static String getMacAddressString(Context act) {
        try {
            WifiManager wifi = (WifiManager) act.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "888";
    }

}
