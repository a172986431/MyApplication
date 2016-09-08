package com.mvp.rxandroid.activity.weather;

import com.mvp.rxandroid.bean.CityListBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public interface CityListApi {
    @GET("weatherservice/citylist")
    Observable<CityListBean> getcitylist(@Query("cityname") String cityname);
}
