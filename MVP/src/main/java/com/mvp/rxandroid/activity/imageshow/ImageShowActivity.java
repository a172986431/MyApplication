package com.mvp.rxandroid.activity.imageshow;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.BaseActivity;
import com.mvp.rxandroid.activity.TextLeak;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by elang on 16/6/3.
 */
public class ImageShowActivity extends BaseActivity {

    private Button addImage_bt,likeTimer_bt;
    private GridView image_gv;
    private ImageshowAdapter adapter;
    private List<String> lists;
    private ImageshowPersenter persenter;

    /**
     * 观察者，用于观察是否有订阅的消息
     */
    private Subscriber<Message> subscriberMsg = new rx.Subscriber<Message>() {
        @Override
        public void onCompleted() {
            addImage_bt.setEnabled(true);
            addImage_bt.setTextColor(Color.argb(0xff, 0xff, 0, 0));
            Log.e("elang", "线程结束  ");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("elang", "接收出错了  ");
        }

        @Override
        public void onNext(Message msg) {
            if (msg.what == ImageshowPersenter.NO_MORE_IMAGE) {
                showToast((String) msg.obj);
            }
            if (msg.what == ImageshowPersenter.REFRESH_LIST) {
                Log.e("elang", "next " + msg.what + "   " + msg.toString());
                lists = (List<String>) msg.obj;
                adapter.setData(lists);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        persenter = new ImageshowPersenter(subscriberMsg);
        persenter.setActivityInterface(this);
        initView();
        TextLeak.getInstance(this).setRetainedTextView(addImage_bt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        subscriberMsg.unsubscribe();
    }

    @Override
    protected void initView() {
        addImage_bt = (Button) findViewById(R.id.addImage_bt);
        likeTimer_bt = (Button) findViewById(R.id.likeTimer_bt);
        image_gv = (GridView) findViewById(R.id.image_gv);
        lists = new ArrayList<>();
        adapter = new ImageshowAdapter(mContext, lists);
        image_gv.setAdapter(adapter);
//        ButterKnife.bind(this);
        super.initView();
    }

    /**
     * 添加图片按钮按下事件
     *
     * @param view
     */
    public void addImage(View view) {
        addImage_bt.setEnabled(false);
        addImage_bt.setTextColor(Color.argb(0xff, 0x0, 0, 0));
        persenter.addImage(lists);
    }

    /**
     * 模拟时钟
     *
     * @param view
     */
    public void likeTimer(View view) {
        likeTimer_bt.setEnabled(false);
        likeTimer_bt.setTextColor(Color.argb(0xff, 0x0, 0, 0));
        persenter.likeTimer(lists);
    }

    /**
     * 模拟网络请求数据
     *
     * @param view
     */
    public void getImageUrl(View view) {
        Log.e("elang","url");
        persenter.imageUrl();
    }
}
