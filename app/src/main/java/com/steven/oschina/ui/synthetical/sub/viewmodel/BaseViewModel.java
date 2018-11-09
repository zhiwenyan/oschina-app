package com.steven.oschina.ui.synthetical.sub.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.base.ResultBean;

/**
 * Description:
 * Data：11/8/2018-3:14 PM
 *
 * @author yanzhiwen
 */
public class BaseViewModel<T> extends ViewModel {


    public final ServiceApi mServiceApi = RetrofitClient.getServiceApi();
    //列表LiveData
    public final MutableLiveData<T> mObservableListLiveData = new MutableLiveData<>();
    public final MutableLiveData<ResultBean<T>> mObservableListLiveData1 = new MutableLiveData<>();
    //
    public final MutableLiveData<T> mObservableLiveData = new MutableLiveData<>();


    public final MutableLiveData<String> mObservableError = new MutableLiveData<>();


    public MutableLiveData<String> getObservableError() {
        return mObservableError;
    }
}
