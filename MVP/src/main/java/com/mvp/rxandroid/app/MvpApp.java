package com.mvp.rxandroid.app;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.mvp.rxandroid.thirdparty.alimail.AlimeiSDKManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by elang on 16/5/23.
 */
public class MvpApp extends Application{

    public static MvpApp mvpApp;
    public static int screenWidth;
    public static boolean DEBUG = true;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        mvpApp = this;
        super.onCreate();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        AlimeiSDKManager.test();
        // 初始化内存泄露检测
        mRefWatcher = installLeakCanary();
    }

    private RefWatcher installLeakCanary() {
        if (!DEBUG) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MvpApp application = (MvpApp) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
