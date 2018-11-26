package com.steven.oschina.ui.synthetical.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.Article;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/26/2018-5:49 PM
 *
 * @author yanzhiwen
 */
public class ArticleViewModel extends ViewModel {
    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();


    private final MutableLiveData<Article> mArticleLiveData = new MutableLiveData<>();
    private final MutableLiveData<Article> mTranslateArticleLiveData = new MutableLiveData<>();
    private final MutableLiveData<Article> mEnglishArticleLiveData = new MutableLiveData<>();


    public MutableLiveData<Article> getArticle(String indet, String key) {
        mServiceApi.getArticleDetail(indet, key).enqueue(new Callback<ResultBean<Article>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<Article>> call, @NonNull Response<ResultBean<Article>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mArticleLiveData.setValue(response.body().getResult());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<Article>> call, @NonNull Throwable t) {

            }
        });

        return mArticleLiveData;
    }
//

    public MutableLiveData<Article> getTranslateArticle(String key, int type) {
        mServiceApi.article_translate(key, type).enqueue(new Callback<ResultBean<Article>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<Article>> call, @NonNull Response<ResultBean<Article>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mTranslateArticleLiveData.setValue(response.body().getResult());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<Article>> call, @NonNull Throwable t) {

            }
        });

        return mTranslateArticleLiveData;
    }

    public MutableLiveData<Article> getEnglishArticle(String indent, String key, int type) {
        mServiceApi.getEnglishArticle(indent, key, type).enqueue(new Callback<ResultBean<Article>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<Article>> call, @NonNull Response<ResultBean<Article>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mEnglishArticleLiveData.setValue(response.body().getResult());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<Article>> call, @NonNull Throwable t) {

            }
        });

        return mEnglishArticleLiveData;
    }
}
