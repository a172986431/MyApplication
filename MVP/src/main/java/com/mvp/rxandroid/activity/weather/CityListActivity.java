package com.mvp.rxandroid.activity.weather;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.BaseActivity;
import com.mvp.rxandroid.bean.City;
import com.mvp.rxandroid.bean.CityListBean;
import com.mvp.rxandroid.dialog.ProgressDialogUtils;
import com.mvp.rxandroid.http.BaseRequest;
import com.mvp.rxandroid.http.NetWork;
import com.mvp.rxandroid.util.SoftInputUtilS;
import com.mvp.rxandroid.views.itemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by elang on 16/7/29.
 */
public class CityListActivity extends BaseActivity implements RecycleViewListener{
    @BindView(R.id.edit_input)
    EditText input;

    @BindView(R.id.edit_result)
    RecyclerView result;

    @OnClick(R.id.edit_btn)
    public void onsearch()
    {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
        SoftInputUtilS.hideSoftInput(this);
        ProgressDialogUtils.showProgress(CityListActivity.this);
        mSubscription = ((CityListApi)NetWork.getApi(CityListApi.class)).getcitylist(input.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        Observable observable = Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {

            }
        }).observeOn(AndroidSchedulers.mainThread());
        Observable observable1 = Observable.just("");
        Observable.merge(observable).mergeWith(observable1).subscribe(subscriber);
    }

    private CityListRvAdapter adapter = new CityListRvAdapter();
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);

        result.setLayoutManager(new LinearLayoutManager(this));
        result.addItemDecoration(new itemDecoration(this));
        ScaleInAnimator scalein = new ScaleInAnimator();
        result.setItemAnimator(scalein);
        result.setAdapter(adapter);
        adapter.setCallback(this);
        new BaseRequest().request();
        subscriber.unsubscribe();
        setTitle("搜索天气城市列表");
    }

    Observer<CityListBean> observer = new Observer<CityListBean>() {
        @Override
        public void onCompleted() {
            ProgressDialogUtils.dismissProgress(CityListActivity.this);
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(CityListActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(CityListBean cityListBean) {
            if (cityListBean.errNum == 0)
            {
//                adapter.setCities(cityListBean.retData);
                setTitle(input.getText().toString().trim());
            }
            else
            {
                Toast.makeText(CityListActivity.this, cityListBean.errMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Subscriber<Object> subscriber = new Subscriber<Object>() {
        @Override
        public void onCompleted() {
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
                Log.e("elang", "接收到数据 string  " + o.toString());
            }else if (ArrayList.class.isInstance(o)) {
                Log.e("elang", "接收到数据 list  " + o.toString());
            }else {
                Log.e("elang", "接收到数据 o  " + o.toString());
            }
        }
    };


    @Override
    public void OnItemClickListener(View v, int position) {
        Toast.makeText(CityListActivity.this, ((City)adapter.getItem(position)).name_cn, Toast.LENGTH_SHORT).show();
//
//        startActivity(WeatherDetailActivity.getIntent(this,(City) adapter.getItem(position)));
    }

    @Override
    public void OnItemLongClickListener(View v, int position) {

    }
}
