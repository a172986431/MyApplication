package com.mvp.rxandroid.activity.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.agera.Repositories;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by elang on 16/5/25.
 * 聊天的逻辑处理
 */
public class ChatPresenter {

    private Context mContext;
    private Subscriber mSubscriber;
    private Action1 mAction1;
    public Runnable runnable;

    public ChatPresenter(Context context, Subscriber subscriber,Action1 action1) {
        mContext = context;
        mSubscriber = subscriber;
        mAction1 = action1;
    }

    public void toastMessage(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送仅文字的信息
     *
     * @param text
     */
    public void sendTextMessage(String text) {
        if (text != null && mSubscriber != null) {
            Observable.just(text).subscribe(mSubscriber);
        }
    }

    /**
     * 发送图片
     *
     * @param image
     */
    public void sendImageMessage(Bitmap image) {
        if (image != null && mSubscriber != null) {
            Observable.just(image).subscribe(mSubscriber);
        }
    }

    /**
     * 撤回一条消息
     *
     * @param message
     */
    public void revokeMsg(String message) {

    }

    /**
     * 测试其他方法的应用
     * @param subscriber
     */
    public void testFlatMap(Subscriber subscriber) {
        List<Object> list = new ArrayList<Object>();
        list.add("hello1");
        list.add("hello2");
        list.add("hello3");
        list.add("haha1");
        list.add("haha2");
        list.add("haha3");
        list.add("haha4");
        Observable.just(list)
                .flatMap(new Func1<List<Object>, Observable<Object>>() {
                    @Override
                    public Observable<Object> call(List<Object> strings) {
                        return Observable.from(strings);
                    }
                }).subscribe(mSubscriber);
//        Observable.from(list).subscribe(subscriber);
    }

    private Boolean filterString(String str, String str1) {
        String subs = str.substring(0, str1.length());
        if (!subs.equals(str1))
            return true;
        return false;
    }

    /**
     * 测试作为Handle类型使用
     */
    public void getMessage() {
        if (mSubscriber != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!mSubscriber.isUnsubscribed()) {
                        Message message = new Message();
                        message.obj = "循环消息";
                        Observable.just(message).subscribe(mAction1);
                        Log.e("elang","message  " + message.toString());
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

    /**
     * 测试作为时钟线程的性能
     */
    public Subscription observableTimer() {
//        Observable.interval(1, TimeUnit.SECONDS).flatMap(new Func1<Long, Observable<String>>() {
//            @Override
//            public Observable<String> call(Long aLong) {
//                Log.e("elang", "call: ");
//                return Observable.just(aLong + "  time");
//            }
//        })
////                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mSubscriber);
        return Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                Log.e("elang", "call: ");
                return aLong + "  time";
            }
        })
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAction1);
    }

    /**
     * Reservoir的用法
     * @param reservoir
     */
    public void getReservoir(final Reservoir reservoir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(5000);
                        reservoir.accept("Reservoir");
                        Log.e("elang", "Reservoir Thread ");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        reservoir.accept("Reservoir 1");
        reservoir.accept("Reservoir 2");
    }

    public void handle(final Handler handler){
        runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                Thread.sleep(5000);
                                handler.sendEmptyMessage(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
//        new Thread(runnable).start();
        handler.sendEmptyMessage(0);
    }

}
