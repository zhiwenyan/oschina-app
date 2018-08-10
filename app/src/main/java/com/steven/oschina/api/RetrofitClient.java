package com.steven.oschina.api;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.oschina.client.base_library.log.LogUtils;
import com.steven.oschina.Constants;
import com.steven.oschina.osc.OSCSharedPreference;
import com.steven.oschina.utils.MD5;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    public static String API_URL = "https://www.oschina.net/%s";
    public final static String HOST = "www.oschina.net";
    private static final ServiceApi mServiceApi;

    static {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor(LogUtils::i)
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                // 访问后台接口的主路径
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServiceApi = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getServiceApi() {
        return mServiceApi;
    }

    private static class TokenInterceptor implements Interceptor {
        private String sessionKey = "";

        TokenInterceptor() {
            if (TextUtils.isEmpty(sessionKey)) {
                sessionKey = MD5.get32MD5Str(OSCSharedPreference.getInstance().getDeviceUUID() + System.currentTimeMillis());
            }
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Accept-Language",Locale.getDefault().toString())
                    .header("HOST",HOST)
                    .header("Connection","Keep-Alive")
                    .header("sessionKey", sessionKey)
                    .header("uuid", OSCSharedPreference.getInstance().getDeviceUUID())
                    .header("Accept","image/webp")
                    .header("AppToken", APIVerify.getVerifyString())
                    .build();
            return chain.proceed(request);
        }
    }
}
