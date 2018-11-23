package com.steven.oschina.ui.synthetical.sub.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
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
public class ArticleViewModel extends ViewModel {


    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();
    //列表LiveData
    private final MutableLiveData<PageBean<Article>> mArticlesLiveData = new MutableLiveData<>();
    private final MutableLiveData<PageBean<Article>> mArticleRecommendsLiveData = new MutableLiveData<>();
    private final MutableLiveData<PageBean<Article>> mEnglishArticlesLiveData = new MutableLiveData<>();

    public MutableLiveData<PageBean<Article>> getArticleRecommends(Map<String, Object> params) {
        mServiceApi.getArticleRecommends(params).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mArticleRecommendsLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {

            }
        });
        return mArticleRecommendsLiveData;
    }

    public MutableLiveData<PageBean<Article>> getEnglishArticles(Map<String, Object> params) {
        mServiceApi.getEnglishArticles(params).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mEnglishArticlesLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {
            }
        });
        return mEnglishArticlesLiveData;
    }


    public MutableLiveData<PageBean<Article>> getArticles(String deviceUUID, String nextPageToken) {
        mServiceApi.getArticles(deviceUUID, nextPageToken).enqueue(new Callback<ResultBean<PageBean<Article>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull
                    Response<ResultBean<PageBean<Article>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mArticlesLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Article>>> call, @NonNull Throwable t) {
            }
        });
        return mArticlesLiveData;
    }


}
