package com.mvp.rxandroid.http;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.mvp.rxandroid.app.AppConfig;
import com.mvp.rxandroid.app.MvpApp;
import com.mvp.rxandroid.bean.City;
import com.mvp.rxandroid.bean.CityListBean;
import com.mvp.rxandroid.bean.MapBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by elang on 16/5/23.
 * 用于实现基本的网络请求get、post，文件上传和下载
 */
public class BaseRequest implements Callback{

    private ResponseResult result;
    private OkHttpClientManager clientManager;

    /**
     * 设置回调
     * @param result
     */
    public BaseRequest setResult(ResponseResult result){
        this.result = result;
        return this;
    }

    /**
     * get方式异步请求网络
     * @param url 请求的url
     * @param params 请求的参数，没有则为null
     * @param headers 添加的响应头，没有则为null
     */
    public void getRequest(String url,Map<String,String> params,Map<String,String> headers){
        clientManager = new OkHttpClientManager();
        Request request = clientManager.createBuilder(HttpConfig.METHOD_GET, url,
                "",headers,params,null,null,null,null).build();
        clientManager.request(request,this);
    }

    /**
     * 请求网络
     */
    public void postRequest(String url,Map<String,String> map){
        clientManager = new OkHttpClientManager();
        Map<String,String> headers = new HashMap<>();
        headers.put("apikey","0fd6f1eb22f45533dd0fb48aff0613bd");
        Map<String,String> params = new HashMap<>();
        params.put("cityname","上海");
        Request request = clientManager.createBuilder(HttpConfig.METHOD_GET, AppConfig.GET_CITYS,
                "",headers,params,null,null,null,null).build();
        clientManager.request(request,this);
    }

    /**
     * 请求网络
     */
    public void request(){
        clientManager = new OkHttpClientManager();
        Map<String,String> headers = new HashMap<>();
        headers.put("apikey","0fd6f1eb22f45533dd0fb48aff0613bd");
        Map<String,String> params = new HashMap<>();
        params.put("cityname","上海");
        Request request = clientManager.createBuilder(HttpConfig.METHOD_GET, AppConfig.GET_CITYS,
                "",headers,params,null,null,null,null).build();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.addHeader("apikey","0fd6f1eb22f45533dd0fb48aff0613bd").
//                url(AppConfig.GET_CITYS + "?cityname=上海").build();
        clientManager.request(request,this);
    }

    /**
     * 结束线程
     */
    public void shutDown(){

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String result = response.body().string();
        String temp = "{\"errNum\":300202,\"errMsg\":\"Missing apikey\",\"retData\":{\"name_en\":\"minhang\",\"area_id\":\"101020200\", \"district_cn\":\"上海\", \"name_cn\":\"闵行\"}}";
        Gson gson1 = new Gson();
        TypeAdapter<CityListBean> cityListBean = gson1.getAdapter(CityListBean.class);
        TypeAdapter<MapBean> mBean = gson1.getAdapter(MapBean.class);
        CityListBean bean = null;
        MapBean mapBean = new MapBean();
        List<City> retData = null;
        Message.obtain();
        try {
            bean = cityListBean.fromJson(result);
            mapBean = gson1.fromJson(temp,MapBean.class);
//            retData = gson1.fromJson(result,List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("elang","" + bean.retDatas.get(2).get("name_cn"));
        Log.e("elang","map  " + mapBean.retData);
        Log.e("elang","list  " + retData);
    }
}
