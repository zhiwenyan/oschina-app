package com.steven.oschina.ui.tweet.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.tweet.Topic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/27/2018-10:39 AM
 *
 * @author yanzhiwen
 */
public class HotTopicViewModel extends ViewModel {
    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();


    private final MutableLiveData<PageBean<Topic>> mTweetTopicsLiveData = new MutableLiveData<>();


    public MutableLiveData<PageBean<Topic>> getHotTopics(String type, String nextPageToken) {

        mServiceApi.getHotTopic(type, nextPageToken).enqueue(new Callback<ResultBean<PageBean<Topic>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Topic>>> call,
                                   @NonNull Response<ResultBean<PageBean<Topic>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mTweetTopicsLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Topic>>> call, @NonNull Throwable t) {

            }
        });
        return mTweetTopicsLiveData;
    }
}
