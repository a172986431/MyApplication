package com.mvp.rxandroid.httpservice;

import android.os.AsyncTask;

import com.google.android.agera.BaseObservable;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.mvp.rxandroid.http.BaseRequest;
import com.mvp.rxandroid.http.ResponseResult;

import java.io.InputStream;

/**
 * Created by elang on 16/6/12.
 */
public class NoteAsyncObservable extends BaseObservable
        implements Repository<Result<Object>>, ResponseResult {

    private final BaseRequest baseRequest;
    private final Supplier<Object> paramSupplier;

    private Result<Object> result;
    private ResponseResult responseResult;

    public NoteAsyncObservable(BaseRequest baseRequest,
                                   Supplier<Object> paramSupplier) {
        this.baseRequest = baseRequest;
        this.paramSupplier = paramSupplier;
        this.result = Result.absent();
    }

    @Override
    protected synchronized void observableActivated() {
        baseRequest.setResult(this).request();
    }

    @Override
    protected synchronized void observableDeactivated() {
        if (baseRequest != null) {
            baseRequest.shutDown();
        }
    }

    @Override
    public synchronized void success(Object response) {
        result = Result.absentIfNull(response);
        dispatchUpdate();
    }

    @Override
    public void fail(int errorCode, String ex) {

    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void streamSuccess(Object object, InputStream inputStream) {

    }

    @Override
    public synchronized Result<Object> get() {
        return result;
    }
}
