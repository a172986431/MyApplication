package com.mvp.rxandroid.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.mvp.rxandroid.app.MvpApp;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by elang on 16/8/1.
 */
public class OkHttpClientManager {

    private static OkHttpClient mOkHttpClient;
    private HttpConfig okHttpConfig;
    /**默认的配置**/
    private static HttpConfig config;


    private static final String TAG = "OkHttpClientManager";

    public OkHttpClientManager(){
        initClient();
    }

    public static OkHttpClient initClient() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpClientManager.class) {
                mOkHttpClient = new OkHttpClient();
                init();
            }
        }
        return mOkHttpClient;
    }

    /**
     * 发送异步请求
     */
    public void request(Request request, Callback callback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 发送同步请求
     */
    public Response request(Request request){
        Call call = mOkHttpClient.newCall(request);
        Response execute = null;
        try {
            execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return execute;
    }

    /**
     * 初始化网络请求工具类
     *
     */
    private static void init() {
        config = HttpConfig.createDefault(MvpApp.mvpApp);
        Cache cache = new Cache(config.getCacheDir(), config.getCacheSize());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache).connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeOut(),TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeOut(),TimeUnit.SECONDS);
        if (config.getProxy() != null)
        {
            builder.proxy(config.getProxy());
        }
        if (config.getProxySelector() != null)
        {
            builder.proxySelector(config.getProxySelector());
        }
        if (config.getSocketFactory() != null)
        {
            builder.socketFactory(config.getSocketFactory());
        }
        if (config.getSslSocketFactory() != null)
        {
            builder.sslSocketFactory(config.getSslSocketFactory());
            builder.hostnameVerifier(new HostnameVerifier(){
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        }
        mOkHttpClient = builder.build();
    }

    /**
     * 构造请求Builder
     *
     * @return
     */
    public Request.Builder createBuilder(String method, String url, String tag, Map<String, String> headers,
                                          Map<String, String> params,File[] files,String[] mediaTypes,
                                          Boolean isRefresh, HttpConfig okHttpConfig) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (okHttpConfig != null) {
            this.okHttpConfig = okHttpConfig;
        }else {
            this.okHttpConfig = config;
        }
        if (params != null && !params.isEmpty()) {
            if (method.equals(HttpConfig.METHOD_GET)) {
                builder.url(attachHttpGetParams(url, params));
            } else {
                builder.post(getPostBody(params));
            }
        } else if (method.equals(HttpConfig.METHOD_POST)) {
            builder.post(RequestBody.create(MediaType.parse("text/html"), ""));
        }

        // 添加默认Headers
        if (this.okHttpConfig.getHeads() != null && !this.okHttpConfig.getHeads().isEmpty()) {
            for (Map.Entry<String, String> header : this.okHttpConfig.getHeads().entrySet()) {
                builder.header(header.getKey(), header.getValue());
            }
        }
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder.addHeader(header.getKey(), header.getValue());
            }
        }
        if (files != null && files.length > 0) {
            int length = files.length;
            for (int i = 0;i < length;i++) {
                File file = files[i];
                MediaType mediaType;
                if (mediaTypes != null && mediaTypes.length > i && mediaTypes[i] != null){
                    mediaType = MediaType.parse(mediaTypes[i]);
                }else {
                    mediaType = MediaType.parse("application/octet-stream");
                }
                builder.post(RequestBody.create(mediaType, file));
            }
        }

        // 添加取消请求标志
        if (tag != null && !tag.equals("")) {
            builder.tag(tag);
        }
        if (isRefresh == null) {
            // 缓存未过期时读缓存,缓存过期时读线上最新.
            builder.cacheControl(new CacheControl.Builder().build());
        } else {
            if (isRefresh) {
                // 强制读线上
                builder.cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                // 只读取缓存
                builder.cacheControl(new CacheControl.Builder()
                        .maxStale(this.okHttpConfig.getCacheTime(), TimeUnit.SECONDS).onlyIfCached().build());
            }
        }
        return builder;
    }

    /**
     * GET添加多个参数
     *
     * @param url
     * @return
     */
    public String attachHttpGetParams(String url, Map<String, String> paramMap) {
        if (paramMap != null && !paramMap.isEmpty()) {
            StringBuffer result = new StringBuffer();
            result.append(url).append("?");
            Set<String> keys = paramMap.keySet();
            for (String key : keys) {
                result.append(key).append("=").append(paramMap.get(key)).append("&");
            }
            return result.toString().substring(0, result.toString().length() - 1);
        } else {
            return url;
        }
    }

    /**
     * 获取POST请求实体
     *
     * @return
     */
    private RequestBody getPostBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (param.getValue() != null) {
                    builder.add(param.getKey(), param.getValue());
                } else {
                    Log.e(TAG, "key:" + param.getKey() + "的Value取值为null");
                }
            }
        }
        return builder.build();
    }
}
