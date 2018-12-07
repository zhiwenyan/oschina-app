package com.steven.oschina.ui.tweet.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.tweet.TweetComment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/27/2018-11:48 AM
 *
 * @author yanzhiwen
 */
public class TweetCommentViewModel extends ViewModel {
    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();


    private final MutableLiveData<PageBean<TweetComment>> mTweetCommentsLiveData = new MutableLiveData<>();


    public MutableLiveData<PageBean<TweetComment>> getTweetComments(long id, String nextPageToken) {


        mServiceApi.getTweetCommentList(id, nextPageToken).enqueue(new Callback<ResultBean<PageBean<TweetComment>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<TweetComment>>> call,
                                   @NonNull Response<ResultBean<PageBean<TweetComment>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    System.out.println("getTweetComments");
                    mTweetCommentsLiveData.postValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<TweetComment>>> call, @NonNull Throwable t) {

            }
        });
        return mTweetCommentsLiveData;
    }
}
