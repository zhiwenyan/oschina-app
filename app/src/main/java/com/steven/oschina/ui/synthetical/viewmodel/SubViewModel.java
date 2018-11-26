package com.steven.oschina.ui.synthetical.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.bean.sub.SubTab;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：10/23/2018-11:28 AM
 *
 * @author yanzhiwen
 */
public class SubViewModel extends ViewModel {
    private final ServiceApi mServiceApi = RetrofitClient.getServiceApi();

    private final MutableLiveData<PageBean<SubBean>> mSubNewsLiveData = new MutableLiveData<>();
    private final MutableLiveData<PageBean<SubBean>> mSubQuestionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<PageBean<SubBean>> mSubBlogLiveData = new MutableLiveData<>();


    public MutableLiveData<PageBean<SubBean>> getSubList(String token, String pageNextToken) {
        mServiceApi.getSubList(token, pageNextToken).enqueue(new Callback<ResultBean<PageBean<SubBean>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<SubBean>>> call,
                                   @NonNull Response<ResultBean<PageBean<SubBean>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    if (token.equals(SubTab.TOKEN_NEWS)) {
                        mSubNewsLiveData.setValue(response.body().getResult());
                    } else if (token.equals(SubTab.TOKEN_QA)) {
                        mSubQuestionsLiveData.setValue(response.body().getResult());
                    } else {
                        mSubBlogLiveData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<SubBean>>> call, @NonNull Throwable t) {
            }
        });
        if (token.equals(SubTab.TOKEN_NEWS)) {
            return mSubNewsLiveData;
        } else if (token.equals(SubTab.TOKEN_QA)) {
            return mSubQuestionsLiveData;
        }
        return mSubBlogLiveData;
    }
}
