package com.mvp.rxandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.mvp.rxandroid.app.MvpApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by elang on 16/5/23.
 * 管理图片操作
 */
public class BitmapManager {
    /**
     * 通过传入位图,新的宽.高比进行位图的缩放操作
     *
     * @param bitmap
     * @param w
     *            缩放后的宽度
     * @param h
     *            缩放后的高度
     * @return
     */
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {

        // load the origial Bitmap
        Bitmap bitmapOrg = bitmap;

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // 缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        if (scaleWidth > scaleHeight) {
            matrix.postScale(scaleHeight, scaleHeight);
        } else {
            matrix.postScale(scaleWidth, scaleWidth);
        }

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
                height, matrix, true);
        if (bitmapOrg.isRecycled()) {
            bitmapOrg.recycle();
            bitmapOrg = null;
        }
        return resizedBitmap;
    }

    /**
     * 将bitmap转为二进制数组
     *
     * @param bmp
     * @param needRecycle
     *            是否需要回收
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 极大的减少图片对内存的消耗
     */
    @Deprecated
    public static Bitmap readBitmap(Context context, int id,
                                    Bitmap.Config config) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = config; // Bitmap.Config.RGB_565;//表示16位位图
        // 565代表对应三原色占的位数图片有损,默认为RGB_8888
        opt.inInputShareable = true;
        opt.inPurgeable = true;// 设置图片可以被回收
        InputStream is = context.getResources().openRawResource(id);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    public static BitmapFactory.Options getResourceInfo(int id) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true; // 只读取信息，不消耗内存
        opt.inPurgeable = true;// 设置图片可以被回收
        InputStream is = MvpApp.mvpApp.getResources()
                .openRawResource(id);
        BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return opt;
    }

    /**
     * 调整图片的方向
     * @param bitmap
     * @param pathName 图片路径
     * @return
     */
    public static Bitmap digreeBitmap(Bitmap bitmap,String pathName){
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(pathName);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        Matrix matrix = new Matrix();
        if (digree != 0) {
            // 旋转图片
            matrix.postRotate(digree);
        }
        Bitmap resizedBitmap = null;
        if (bitmap != null) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
    /**
     * 调整图片的方向
     * @param bitmap
     * @digree 旋转角度
     * @return
     */
    public static Bitmap digreeBitmap(Bitmap bitmap,int digree){
        Matrix matrix = new Matrix();
        if (digree != 0) {
            // 旋转图片
            matrix.postRotate(digree);
        }
        if (bitmap != null) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}
