package com.mvp.rxandroid.http;

/**
 * Created by elang on 16/5/23.
 * 网络请求的常量
 */
public class HttpConstant {
    /**
     * 网络故障错误
     **/
    public static final int HTTP_ERROR = 101;
    /**
     * 返回404等错误
     **/
    public static final int RESULT_ERROR = 102;
    /**
     * 数据解析错误
     **/
    public static final int JSON_ERROR = 103;

    /**
     * 每个接口回调的标识
     **/
    public static int messageWhat = 10001;

    public static int getMessageWhat() {
        return messageWhat++;
    }

    /**
     * 登录
     **/
    public static final String LOGIN_URL = "MyConstant.httpHost" + "login?";
    public static final String LOGIN_ACTION = "Login";
    public static final int LOGIN_TAG = getMessageWhat();
}
