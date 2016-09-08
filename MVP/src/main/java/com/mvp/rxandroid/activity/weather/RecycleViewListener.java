package com.mvp.rxandroid.activity.weather;

import android.view.View;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public interface RecycleViewListener {

    void OnItemClickListener(View v, int position);

    void OnItemLongClickListener(View v, int position);

}
