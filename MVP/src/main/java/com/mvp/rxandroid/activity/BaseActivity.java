package com.mvp.rxandroid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mvp.rxandroid.app.MvpApp;
import com.mvp.rxandroid.util.ViewServer;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by elang on 16/5/24.
 */
public class BaseActivity extends Activity implements BaseActivityInterface{

    protected Context mContext;
    private ProgressDialog  progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ViewServer.get(this).addWindow(this);
    }

    protected void initView(){
        progressDialog = new ProgressDialog(this);
    }

    /**
     * 弹出等待的进度条
     */
    @Override
    public void showProgress(){
        progressDialog.setTitle("loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请等待加载中......");
        progressDialog.show();

    }

    /**
     * 取消进度显示
     */
    @Override
    public void dismissProgress(){
        progressDialog.dismiss();
    }

    /**
     * 弹出toast提示
     * @param msg 提示的内容
     */
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
        RefWatcher watcher = MvpApp.getRefWatcher(MvpApp.mvpApp);
        if (watcher != null) {
            watcher.watch(this);
        }
    }
}
