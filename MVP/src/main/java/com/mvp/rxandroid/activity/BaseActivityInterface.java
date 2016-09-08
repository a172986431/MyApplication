package com.mvp.rxandroid.activity;

/**
 * Created by elang on 16/5/25.
 * BaseActivity的接口实现一些通用方法
 */
public interface BaseActivityInterface {
    /**
     * 显示进度条
     */
    public void showProgress();

    /**
     * 隐藏进度条
     */
    public void dismissProgress();
}
