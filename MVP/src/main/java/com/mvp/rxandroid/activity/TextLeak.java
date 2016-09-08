package com.mvp.rxandroid.activity;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zhuMH on 16/9/8.
 */
public class TextLeak {

    private Context mCtx;
    private Button mTextView;

    private static TextLeak ourInstance = null;

    public static TextLeak getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TextLeak(context);
        }
        return ourInstance;
    }

    public void setRetainedTextView(Button tv){
        this.mTextView = tv;
        mTextView.setText(mCtx.getString(android.R.string.ok));
    }

    private TextLeak() {
    }

    private TextLeak(Context context) {
        this.mCtx = context;
    }

}
