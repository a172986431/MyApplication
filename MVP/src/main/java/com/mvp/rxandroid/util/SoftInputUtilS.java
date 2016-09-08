package com.mvp.rxandroid.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class SoftInputUtilS {

    public static void hideSoftInput(final Activity acitivity)
    {
        if(null!=acitivity && acitivity.getCurrentFocus()!=null){
            if(null!=acitivity.getWindow()){
                acitivity.getWindow().getDecorView().clearFocus();
                final InputMethodManager im = ((InputMethodManager) acitivity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                if(null!=im){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            im.hideSoftInputFromWindow(acitivity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    },200);

                }
            }
        }
    }

}
