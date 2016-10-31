package com.mvp.rxandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.NoteBook.NoteActivity;
import com.mvp.rxandroid.activity.chat.ChatActivity;
import com.mvp.rxandroid.activity.contact.ContactActivity;
import com.mvp.rxandroid.activity.contact.ContactInfo;
import com.mvp.rxandroid.activity.imageshow.ImageShowActivity;
import com.mvp.rxandroid.activity.weather.CityListActivity;
import com.mvp.rxandroid.bean.CityListBean;
import com.mvp.rxandroid.proxy.DynamicProxy;
import com.mvp.rxandroid.proxy.IHello;
import com.mvp.rxandroid.proxy.RealHello;
import com.mvp.rxandroid.thirdparty.alimail.AlimeiSDKManager;
import com.mvp.rxandroid.util.ViewServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    String tempJson = "{\"errNum\":300202,\"errMsg\":\"Missing apikey\",\"retData\":[{\"name_cn\":\"1hao\"},{\"name_cn\":\"2hao\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ViewServer.get(this).addWindow(this);
        Snackbar.make(findViewById(R.id.example_bt),"这个是测试信息",Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 跳转点击事件
     * @param view
     */
    public void toChatActivity(View view){
        AlimeiSDKManager.test();
        Intent intent = new Intent(mContext, ChatActivity.class);
//        startActivity(intent);
        Snackbar.make(findViewById(R.id.example_bt),"这个是测试信息",Snackbar.LENGTH_SHORT).show();
        Toast.makeText(mContext,"Toast 测试",Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转点击事件
     * @param view
     */
    public void toImageActivity(View view){
        Intent intent = new Intent(mContext, ImageShowActivity.class);
        startActivity(intent);
        Gson gson1 = new Gson();
        TypeAdapter<CityListBean> cityListBean = gson1.getAdapter(CityListBean.class);
        CityListBean bean = null;
        try {
             bean = cityListBean.fromJson(tempJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("elang","" + bean);
    }

    /**
     * 跳转点击事件
     * @param view
     */
    public void toNoteActivity(View view){
        Intent intent = new Intent(mContext, NoteActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转点击事件
     * @param view
     */
    public void toProxyActivity(View view){
//        IHello realHello = (IHello) new DynamicProxy().bind(new RealHello(),new DynamicProxy());
//        realHello.sayHello();
        Intent intent = new Intent(mContext, CityListActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转点击事件
     * @param view
     */
    public void toContactActivity(View view){
        Intent intent = new Intent(mContext, ContactActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
