package com.steven.oschina.api;

import java.util.List;

/**
 * Description:
 * Data：6/13/2018-10:32 AM
 *
 * @author yanzhiwen
 */
public abstract class HttpCallback<T> {
    public void onSuccess(List<T> result, String nextPageToken) {

    }
    public void onSuccess(T result){

    }
    public void onFailure(Throwable t) {
    }
}
