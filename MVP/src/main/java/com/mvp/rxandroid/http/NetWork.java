package com.mvp.rxandroid.http;

import com.mvp.rxandroid.activity.weather.CityListApi;
import com.mvp.rxandroid.app.AppConfig;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class NetWork {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static CityListApi cityListApi;

    public static CityListApi getClitListApi() {
        if (cityListApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(AppConfig.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            cityListApi = retrofit.create(CityListApi.class);
        }
        return cityListApi;
    }

    /**
     * 传入被观察者的接口类，通过动态代理出真实对象
     * @param cls
     * @return
     */
    public static Object getApi(Class<?> cls) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OKHttpUtils.getInstaces().getOkHttpClient())
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        Object object = null;
        try {
            object = retrofit.create(cls);
        } catch (Exception e) {
        }
        if (object == null) {
            object = Observable.create(null);
        }
        return object;
    }
}
