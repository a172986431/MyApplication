package com.mvp.rxandroid.ndk;

/**
 * Created by zhuMH on 16/12/7.
 */

public class TestNdk {

    static {
        System.loadLibrary("hello-jni");
    }

    public native String stringFromJNI();
}
