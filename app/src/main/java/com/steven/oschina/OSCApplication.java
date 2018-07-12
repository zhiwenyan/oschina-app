package com.steven.oschina;

import android.app.Application;
import android.provider.Settings;
import android.text.TextUtils;

import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.utils.MD5;

import java.util.UUID;

/**
 * Description:
 * Data：7/5/2018-9:31 AM
 *
 * @author yanzhiwen
 */
public class OSCApplication extends AppContext {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    private void init(){
        OSCSharedPreference.init(this, "osc_update_sp");
        if (TextUtils.isEmpty(OSCSharedPreference.getInstance().getDeviceUUID())) {
            String androidId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
            if (TextUtils.isEmpty(androidId)) {
                androidId = UUID.randomUUID().toString().replaceAll("-", "");
            }
            OSCSharedPreference.getInstance().putDeviceUUID(MD5.get32MD5Str(androidId));
        }
    }
}
