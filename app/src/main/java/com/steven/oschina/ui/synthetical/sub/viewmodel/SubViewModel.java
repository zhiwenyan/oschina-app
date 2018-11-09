package com.steven.oschina.ui.synthetical.sub.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.SubBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：10/23/2018-11:28 AM
 *
 * @author yanzhiwen
 */
public class SubViewModel extends BaseViewModel<PageBean<SubBean>> {

    public MutableLiveData<PageBean<SubBean>> getSubList(String token, String pageNextToken) {
        mServiceApi.getSubList(token, pageNextToken).enqueue(new Callback<ResultBean<PageBean<SubBean>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<SubBean>>> call,
                                   @NonNull Response<ResultBean<PageBean<SubBean>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mObservableListLiveData.setValue(response.body().getResult());
                } else {
                    mObservableError.setValue("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<SubBean>>> call, @NonNull Throwable t) {
                mObservableError.setValue(t.getMessage());
            }
        });
        return mObservableListLiveData;
    }


}
