package com.mvp.rxandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mvp.rxandroid.app.MvpApp;

/**
 * Created by elang on 16/5/23.
 * 处理图片加载
 */
public class ImageLoader {

    private Context mContext;
    /**
     * 初始设置加载图片的大小
     */
    private int bitmapWidthSize = 90;
    private int bitmapHeightSize = 90;

    public ImageLoader(Context context){
        mContext = context;
    }

    /**
     * 直接设置加载图片的大小
     * @param width
     * @param height
     */
    public void setBitmapSizeBy(int width,int height) {
        bitmapWidthSize = width;
        bitmapHeightSize = height;
    }

    /**
     * 设置加载图片默认图的大小
     * @param resource 默认图的资源id
     */
    public void setBitmapSizeBy(int resource) {
        Bitmap bitmapSize = BitmapFactory
                .decodeResource(MvpApp.mvpApp.getResources(), resource);
        this.bitmapWidthSize = bitmapSize.getWidth();
        this.bitmapHeightSize = bitmapSize.getHeight();
        if (bitmapSize.isRecycled()) {
            bitmapSize.recycle();
            bitmapSize = null;
        }
}

    /**
     * 加载图片到imageView
     * @param url 图片的url
     * @param imageView 载入的控件
     */
    public void displayImage(String url, ImageView imageView) {
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        if (width != 0 && height != 0){
            bitmapWidthSize = width;
            bitmapHeightSize = height;
        }
        Glide.with(mContext).load(url)
                .override(bitmapWidthSize,bitmapHeightSize) //定义加载图片的大小
//                .placeholder(R.drawable.placeholder)  //占位图
//                .error(R.drawable.imagenotfound)      //加载出错图
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //加载图片完成的监听
                        Log.e("elang","失败  " + e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //加载图片完成的监听
                        Log.e("elang","成功  " +resource.getIntrinsicWidth() + " & " + resource.getIntrinsicHeight());
                        return false;
                    }
                })
                .centerCrop().into(imageView);
    }

}
