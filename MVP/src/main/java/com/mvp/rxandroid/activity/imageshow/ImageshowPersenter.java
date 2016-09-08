package com.mvp.rxandroid.activity.imageshow;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.mvp.rxandroid.activity.BaseActivityInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by elang on 16/6/3.
 */
public class ImageshowPersenter {

    public final static int NO_MORE_IMAGE = 100;
    public final static int REFRESH_LIST = 101;
    public final static int GET_IMAGE_URL = 102;
    private Subscriber mSubscriber;
    private ImageshowModel model;
    private BaseActivityInterface mActivityInterface;

    public ImageshowPersenter(Subscriber subscriber) {
        mSubscriber = subscriber;
        model = new ImageshowModel();
        model.initData();
    }

    public void setActivityInterface(BaseActivityInterface baseActivityInterface) {
        mActivityInterface = baseActivityInterface;
    }

    /**
     * 取出一个数据并且通知刷新
     *
     * @param lists
     */
    public void addImage(List<String> lists) {
        if (lists != null && lists.size() >= model.imageUrls.size()) {
            noMoreImage();
        } else {
            lists = model.addImage(lists);
            notifyRefresh(lists);
        }
        Log.e("elang", lists.size() + "  size");
    }

    /**
     * 循环获取图片
     *
     * @param lists
     */
    public void likeTimer(final List<String> lists) {
        if (lists != null) {
            lists.clear();
        }
        if (mSubscriber == null) {
            return;
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<String> list = lists;
//                while (!mSubscriber.isUnsubscribed()){
//                    if (list.size() >= model.imageUrls.size()){
//                        list.clear();
//                    }
//                    list = model.addImage(list);
//                    notifyRefresh(list);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        Observable.interval(1, TimeUnit.SECONDS).flatMap(new Func1<Long, Observable<Message>>() {
            @Override
            public Observable<Message> call(Long aLong) {
                Log.e("elang", "call: ");
                List<String> list = lists;
                if (list.size() >= model.imageUrls.size()) {
                    list.clear();
                } else {
                    list = model.addImage(list);
                }
                Message message = new Message();
                message.obj = list;
                message.what = REFRESH_LIST;
                return Observable.just(message);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void imageUrl() {
        if (mActivityInterface != null) {
            mActivityInterface.showProgress();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.obj = model.imageUrls;
                message.what = REFRESH_LIST;
                Observable.just(message)
//                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                if (mActivityInterface != null) {
                    mActivityInterface.dismissProgress();
                }
            }
        }).start();
    }

    /**
     * 刷新列表数据
     *
     * @param lists
     */
    private void notifyRefresh(List<String> lists) {
        Message message = new Message();
        message.obj = lists;
        message.what = REFRESH_LIST;
        sendMessage(message);
    }

    /**
     * 提示数据中没有更多了
     */
    private void noMoreImage() {
        Message message = new Message();
        message.obj = "没有更多的图片了！";
        message.what = NO_MORE_IMAGE;
        sendMessage(message);
    }

    /**
     * 将message发送给activity
     *
     * @param message
     */
    public void sendMessage(Message message) {
        Observable.just(message)
//                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        Log.e("elang", message.toString());
    }

}
