package com.mvp.rxandroid.httpservice;

import com.mvp.rxandroid.app.AppConfig;
import com.mvp.rxandroid.http.BaseRequest;
import com.mvp.rxandroid.http.ResponseResult;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by elang on 16/8/5.
 */
public class CityListService extends BaseRequest implements ResponseResult{

    public void getList(String city){
        Map<String,String> headers = new HashMap<>();
        headers.put("apikey","0fd6f1eb22f45533dd0fb48aff0613bd");
        Map<String,String> params = new HashMap<>();
        /**cityname:传入城市名称**/
        params.put("cityname",city);
        setResult(this);
        getRequest(AppConfig.GET_CITYS,params,headers);
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void fail(int errorCode, String ex) {

    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void streamSuccess(Object object, InputStream inputStream) {

    }
}
