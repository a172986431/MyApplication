package com.mvp.rxandroid.activity.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import com.google.android.agera.Updatable;
import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by elang on 16/5/24.
 */
public class ChatActivity extends BaseActivity implements Updatable{

    @BindView(R.id.send_bt) Button sendBt;
    public int index = 0;
    private CompositeSubscription mCompositeSubscription;
    private ChatPresenter chatPresenter;
    private  Reservoir<String> reservoir;

    private Subscriber<Object> subscriberT = new Subscriber<Object>() {
        @Override
        public void onCompleted() {
            sendBt.setEnabled(true);
            sendBt.setTextColor(Color.argb(0xff, 0xff, 0, 0));
            Log.e("elang", "线程结束  ");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("elang", "接收出错了  ");
        }

        @Override
        public void onNext(Object o) {
            if (o instanceof Bitmap) {
                Log.e("elang", "接收到数据  image");
            } else if (String.class.isInstance(o)) {
                Log.e("elang", "接收到数据  " + o.toString());
            }else if (ArrayList.class.isInstance(o)) {
                Log.e("elang", "接收到数据s  " + o.toString());
            }else {
                Log.e("elang", "接收到数据o  " + o.toString());
            }
        }
    };

    private Subscriber<Message> subscriberMsg = new Subscriber<Message>() {
        @Override
        public void onCompleted() {
            sendBt.setEnabled(true);
            sendBt.setTextColor(Color.argb(0xff, 0xff, 0, 0));
            Log.e("elang", "线程结束  ");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("elang", "接收出错了  ");
        }

        @Override
        public void onNext(Message msg) {
            Object o = msg.obj;
            if (o instanceof Bitmap) {
                Log.e("elang", "接收到数据  image");
            } else if (String.class.isInstance(o)) {
                Log.e("elang", "接收到数据  " + o.toString());
            }else if (ArrayList.class.isInstance(o)) {
                Log.e("elang", "接收到数据s  " + o.toString());
            }else {
                Log.e("elang", "接收到数据o  " + o.toString());
            }
        }
    };

    Action1<Object> action1 = new Action1<Object>() {
        @Override
        public void call(Object o) {
            if (o instanceof Bitmap) {
                Log.e("elang", "a接收到数据  image");
            } else if (String.class.isInstance(o)) {
                Log.e("elang", "a接收到数据  " + o.toString());
            }else if (ArrayList.class.isInstance(o)) {
                Log.e("elang", "a接收到数据s  " + o.toString());
            }else {
                Log.e("elang", "a接收到数据o  " + o.toString());
            }
        }
    };

    /**
     * 观察者，用于观察是否有订阅的消息
     */
    private Subscriber subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            sendBt.setEnabled(true);
            sendBt.setTextColor(Color.argb(0xff, 0xff, 0, 0));
            Log.e("elang", "线程结束  ");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("elang", "接收出错了  ");
        }

        @Override
        public void onNext(String s) {
            Log.e("elang", "接收到数据  " + s);
        }
    };

    /**
     * 使用IO线程处理, 主线程响应
     * 被观察者，给订阅者发送消息
     */
    private Observable<Object> observable = Observable.create(new Observable.OnSubscribe<Object>() {
        @Override
        public void call(Subscriber<? super Object> subscriber) {
            while (!subscriber.isUnsubscribed()) {
            index++;
            try {
                Thread.sleep(1000);
                subscriber.onNext("发送的消息 " + index);
                Log.e("elang", "测试到数据  ");
//                    Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatPresenter = new ChatPresenter(this,subscriberT,action1);
//        sendBt = (Button) findViewById(R.id.send_bt);
        mCompositeSubscription = new CompositeSubscription();
        ButterKnife.bind(this);

        reservoir = Reservoirs.reservoir();

    }

    public void sent(final View view) {
        sendBt.setEnabled(false);
        sendBt.setTextColor(Color.argb(0xff, 0x0, 0, 0));
        //设置订阅者
//        observable.subscribe(subscriber);
//        mCompositeSubscription.add(observable.subscribe(subscriber));
        chatPresenter.sendTextMessage("发送text");
//        chatPresenter.sendImageMessage(BitmapFactory.decodeResource(getResources(),R.drawable.common_full_open_on_phone));
//        chatPresenter.testFlatMap(subscriber);

//        chatPresenter.getMessage();
//        mCompositeSubscription.add(chatPresenter.observableTimer());
    }

    public void 测试封装Rx(View view){
        chatPresenter.getReservoir(reservoir);
        chatPresenter.handle(handler);
    }

    @Override
    protected void onDestroy() {
        subscriberT.unsubscribe();
        subscriber.unsubscribe();
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reservoir.removeUpdatable(this);
        handler.removeCallbacks(chatPresenter.runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reservoir.addUpdatable(this);
    }

    @Override
    public void update() {
        Log.e("elang","update  " + reservoir.get());
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e("elang","handle  " + msg.what);
            super.handleMessage(msg);
        }
    };
}
