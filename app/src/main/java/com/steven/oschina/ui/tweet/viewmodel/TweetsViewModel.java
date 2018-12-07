package com.steven.oschina.ui.tweet.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.tweet.Tweet;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/27/2018-10:39 AM
 *
 * @author yanzhiwen
 */
public class TweetsViewModel extends ViewModel {
    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();


    private final MutableLiveData<PageBean<Tweet>> mTweetsLiveData = new MutableLiveData<>();


    public MutableLiveData<PageBean<Tweet>> getTweets(Map<String, Object> params) {

        mServiceApi.getTweetList(params).enqueue(new Callback<ResultBean<PageBean<Tweet>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Tweet>>> call,
                                   @NonNull Response<ResultBean<PageBean<Tweet>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mTweetsLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Tweet>>> call, @NonNull Throwable t) {

            }
        });
        return mTweetsLiveData;
    }
}
