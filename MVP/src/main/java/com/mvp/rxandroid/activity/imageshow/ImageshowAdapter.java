package com.mvp.rxandroid.activity.imageshow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mvp.rxandroid.R;
import com.mvp.rxandroid.app.MvpApp;
import com.mvp.rxandroid.util.ImageLoader;

import java.util.List;

/**
 * Created by elang on 16/6/3.
 */
public class ImageshowAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> images;
    private ImageLoader imageLoader;

    public ImageshowAdapter(Context context,List<String> list){
        mContext = context;
        images = list;
        imageLoader = new ImageLoader(context);
        imageLoader.setBitmapSizeBy((int)(MvpApp.screenWidth/3),(int)(MvpApp.screenWidth/3));
    }

    /**
     * 用于更新列表数据
     * @param list
     */
    public void setData(List<String> list){
        images = list;
    }

    @Override
    public int getCount() {
        if (images == null){
            return 0;
        }
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = (ImageView) view;
        if (imageView == null){
            imageView = new ImageView(mContext);
            int anim;
            if (position%3 == 0){
                anim = R.anim.image_appear_left;
            }else if (position%3 == 1){
                anim = R.anim.image_appear_middle;
            }else {
            }
                anim = R.anim.image_appear_right;
            Animation animation = AnimationUtils.loadAnimation(mContext, anim);
            imageView.startAnimation(animation);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)(MvpApp.screenWidth/3)
                ,(int)(MvpApp.screenWidth/3));
        imageView.setLayoutParams(params);
        imageLoader.displayImage(images.get(position),imageView);
        return imageView;
    }
}
