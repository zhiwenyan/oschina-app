package com.steven.oschina.ui.synthetical.sub.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.Article;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/8/2018-3:59 PM
 *
 * @author yanzhiwen
 */
public class ArticleViewModel extends BaseViewModel<PageBean<Article>> {

    public MutableLiveData<PageBean<Article>> getArticleRecommends(Map<String, Object> params) {
        mServiceApi.getArticleRecommends(params).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mObservableListLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {

            }
        });
        return mObservableListLiveData;
    }

    public MutableLiveData<PageBean<Article>> getEnglishArticles(Map<String, Object> params) {
        mServiceApi.getEnglishArticles(params).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mObservableListLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {
            }
        });
        return mObservableListLiveData;
    }


    public MutableLiveData<PageBean<Article>> getArticles(String deviceUUID, String nextPageToken) {
        mServiceApi.getArticles(deviceUUID, nextPageToken).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mObservableListLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {
            }
        });
        return mObservableListLiveData;
    }
//
//    public MutableLiveData<PageBean<Banner>> getBanner(int catalog) {
//        mServiceApi.getBanner(catalog).enqueue(new Callback<ResultBean<PageBean<Banner>>>() {
//            @Override
//            public void onResponse(@NonNull Call<ResultBean<PageBean<Banner>>> call, @NonNull Response<ResultBean<PageBean<Banner>>> response) {
//                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
//                    mObservableListLiveData.setValue(response.body().getResult());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResultBean<PageBean<Banner>>> call, @NonNull Throwable t) {
//
//            }
//        });
//        return mObservableListLiveData;
//
//    }
}
