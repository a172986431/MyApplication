package com.mvp.rxandroid.proxy;

import android.util.Log;

/**
 * Created by elang on 16/7/6.
 */
public class RealHello implements IHello,IProxy{
    @Override
    public void sayHello() {
        Log.e("elang","Hello");
    }

    @Override
    public String getProxy() {
        Log.e("elang","Hello proxy");
        return "proxy";
    }
}
