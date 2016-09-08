package com.mvp.rxandroid.http;

import java.io.InputStream;

/**
 * Created by elang on 16/5/23.
 * 网络结果回调
 */
public interface ResponseResult {

    public void success(Object object);

    public void fail(int errorCode, String ex);

    public void progress(int progress);

    public void streamSuccess(Object object, InputStream inputStream);
}
