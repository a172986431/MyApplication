package com.mvp.rxandroid.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class CityListBean {
    public int errNum;
    public String errMsg;
    public String name_cn;
//    public @SerializedName("retData") List<City> retData;
    public @SerializedName("retData") List<HashMap<String,String>> retDatas;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("errNum:").append(errNum).append("\n");
        sb.append("errMsg:").append(errMsg).append("\n");
        sb.append("retDatas:").append(retDatas).append("\n");
//        if (retData != null)
//        {
//            for (City tmp : retData)
//            {
//                sb.append(tmp.name_cn).append("\n");
//            }
//        }

        return sb.toString();
    }
}
