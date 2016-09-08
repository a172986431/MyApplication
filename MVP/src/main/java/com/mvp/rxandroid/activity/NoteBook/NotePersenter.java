package com.mvp.rxandroid.activity.NoteBook;

import android.os.Message;

import java.util.List;

/**
 * Created by elang on 16/6/8.
 */
public class NotePersenter {

    public final static int ADD_NOTE = 100;
    public final static int REMOVE_NOTE = 101;
    private NoteObservable mObservable;

    public NotePersenter(NoteObservable observable) {
        mObservable = observable;
    }

    /**
     * 刷新列表
     *
     * @param list
     */
    public void refreshNote(List<String> list) {
        Message message = Message.obtain();
        message.what = NotePersenter.REMOVE_NOTE;
        message.obj = list;
        mObservable.setMessage(message);
    }

    /**
     * 添加记事本
     *
     * @param str  添加的记录
     * @param list 记录列表
     */
    public void addNote(String str, List<String> list) {
        list.add(str);
        Message message = Message.obtain();
        message.what = NotePersenter.ADD_NOTE;
        message.obj = list;
        mObservable.setMessage(message);
    }

    /**
     * 移除记事本
     *
     * @param index 需要移除的记录的位置
     * @param list  记录列表
     */
    public void removeNote(int index, List<String> list) {
        list.remove(list.get(index));
        Message message = Message.obtain();
        message.what = NotePersenter.REMOVE_NOTE;
        message.obj = list;
        mObservable.setMessage(message);
    }

    /**
     * @param list
     */
    public void timerRemove(final List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (list.size() > 0 && mObservable.isAlive) {
                    removeNote(0, list);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
