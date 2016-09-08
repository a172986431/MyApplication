package com.mvp.rxandroid.activity.NoteBook;

import android.os.Message;
import android.util.Log;

import com.google.android.agera.BaseObservable;

/**
 * 被监听者的消息存储实体
 * Created by elang on 16/6/8.
 */
public class NoteObservable extends BaseObservable {

    private Message message;
    /**
     * 用于判断是否存在监听
     */
    public Boolean isAlive = false;

    /**
     * 获取事件的数据
     *
     * @return
     */
    public Message getMessage() {
        return message;
    }

    /**
     * 用于存储数据和发送更新事件
     *
     * @param msg 要发送的数据
     */
    public void setMessage(Message msg) {
        message = msg;
        update();
    }

    /**
     * 推送事件
     */
    private void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dispatchUpdate();
            }
        }).start();
    }

    @Override
    protected void observableActivated() {
        super.observableActivated();
        //第一个监听add的时候调用
        Log.e("elang", "开始消息监听");
        isAlive = true;
    }

    @Override
    protected void observableDeactivated() {
        super.observableDeactivated();
        //只有所有的监听都remove掉才会调用
        Log.e("elang", "结束消息监听");
        isAlive = false;
    }
}
