package com.steven.oschina.api;

import android.support.annotation.NonNull;

import com.oschina.client.base_library.log.LogUtils;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：6/13/2018-10:25 AM
 *
 * @author yanzhiwen
 */
public class HttpUtils {

    public static <T> void get(Call<ResultBean<PageBean<T>>> call, HttpCallback<T> callback) {
        call.enqueue(new Callback<ResultBean<PageBean<T>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<T>>> call, @NonNull Response<ResultBean<PageBean<T>>> response) {
                ResultBean<PageBean<T>> resultBean = response.body();
                if (resultBean != null) {
                    if (resultBean.isOk()) {
                        LogUtils.i("请求数据成功" + resultBean.getCode());
                        callback.onSuccess(resultBean.getResult().getItems(), resultBean.getResult().getNextPageToken());
                    } else {
                        LogUtils.i("出错了" + resultBean.getCode());
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<T>>> call, @NonNull Throwable t) {
                LogUtils.i("服务器异常" + t.getLocalizedMessage());

            }
        });
    }

    public static <T> void _get(Call<ResultBean<T>> call, HttpCallback<T> callback) {
        call.enqueue(new Callback<ResultBean<T>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<T>> call, @NonNull Response<ResultBean<T>> response) {
                ResultBean<T> resultBean = response.body();
                if (resultBean != null) {
                    if (resultBean.isOk()) {
                        callback.onSuccess(resultBean.getResult());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<T>> call, @NonNull Throwable t) {

            }
        });
    }


    public static <T> void post(Call<ResultBean<T>> call, HttpCallback<T> callback) {
        call.enqueue(new Callback<ResultBean<T>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<T>> call, @NonNull Response<ResultBean<T>> response) {
                ResultBean<T> resultBean = response.body();
                if (resultBean != null) {
                    if (resultBean.isOk()) {
                        callback.onSuccess(resultBean.getResult());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<T>> call, @NonNull Throwable t) {

            }
        });
    }

}
