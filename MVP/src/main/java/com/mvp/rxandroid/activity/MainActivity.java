package com.mvp.rxandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widge.SnackToast;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.NoteBook.NoteActivity;
import com.mvp.rxandroid.activity.chat.ChatActivity;
import com.mvp.rxandroid.activity.contact.ContactActivity;
import com.mvp.rxandroid.activity.imageshow.ImageShowActivity;
import com.mvp.rxandroid.activity.weather.CityListActivity;
import com.mvp.rxandroid.app.MvpApp;
import com.mvp.rxandroid.bean.CityListBean;
import com.mvp.rxandroid.util.ViewServer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    String tempJson = "{\"errNum\":300202,\"errMsg\":\"Missing apikey\",\"retData\":[{\"name_cn\":\"1hao\"},{\"name_cn\":\"2hao\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ViewServer.get(this).addWindow(this);
        Snackbar.make(findViewById(R.id.example_bt), "这个是测试信息", Snackbar.LENGTH_SHORT).show();
        Log.e("elang", MvpApp.mvpApp.getDatabasePath(".db").getAbsolutePath());
    }

    /**
     * 跳转点击事件
     *
     * @param view
     */
    public void toChatActivity(View view) {
//        AlimeiSDKManager.test();
        Intent intent = new Intent(mContext, ChatActivity.class);
//        startActivity(intent);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.toastTx), "这个是测试信息", Snackbar.LENGTH_SHORT);
        try {
//            Class cla=Class.forName("android.support.design.widget.Snackbar");
//            Constructor c = snackbar.getClass().getDeclaredConstructor(ViewGroup.class);
//            c.setAccessible(true);
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(snackbar, c.getModifiers() & ~Modifier.FINAL);
//            c.set(null, snackbar);


            Field field = Snackbar.class.getDeclaredField("ANIMATION_DURATION");
            setFinalStatic(field,0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("elang", "field ->" + e.getMessage());
        }
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//        CoordinatorLayout.LayoutParams cl = new CoordinatorLayout.LayoutParams(
//                CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.WRAP_CONTENT);
//        cl.bottomMargin = 50 * 3;
//        cl.gravity = Gravity.CENTER_HORIZONTAL;
//        snackbarLayout.setLayoutParams(cl);
        snackbarLayout.setBackgroundResource(R.drawable.toast_shape_gray_bg);
        snackbar.show();
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                snackbar.getView().clearAnimation();
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_miss);
                snackbar.getView().setAnimation(animation);
                Log.e("elang", "dismiss " + event);
                super.onDismissed(snackbar, event);
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
            }
        });
        snackbar.getView().clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_miss);
//        snackbar.getView().setAnimation(animation);
        Toast toast = Toast.makeText(mContext, "Toast 测试", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /**
     * 跳转点击事件
     *
     * @param view
     */
    public void toImageActivity(View view) {
        SnackToast snackbar = SnackToast.make(findViewById(R.id.contact_bt), "这个是测试信息", SnackToast.LENGTH_SHORT);
        SnackToast.SnackbarLayout snackbarLayout = (SnackToast.SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackgroundResource(R.drawable.toast_shape_gray_bg);
        snackbar.show();
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
        Log.e("elang", "" + bean);
    }

    /**
     * 跳转点击事件
     *
     * @param view
     */
    public void toNoteActivity(View view) {
        Intent intent = new Intent(mContext, NoteActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转点击事件
     *
     * @param view
     */
    public void toProxyActivity(View view) {
//        IHello realHello = (IHello) new DynamicProxy().bind(new RealHello(),new DynamicProxy());
//        realHello.sayHello();
        Intent intent = new Intent(mContext, CityListActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转点击事件
     *
     * @param view
     */
    public void toContactActivity(View view) {
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

    private void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
