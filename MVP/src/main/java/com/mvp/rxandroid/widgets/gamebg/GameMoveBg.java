package com.mvp.rxandroid.widgets.gamebg;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mvp.rxandroid.R;

public class GameMoveBg extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    private Resources res = this.getResources();
    private Bitmap bmpBackGround;// 游戏背景
    public static int screenW;
    public static int screenH;

    private GameBg gameBg;

    /**
     * SurfaceView初始化函数
     */
    public GameMoveBg(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    public GameMoveBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    /**
     * SurfaceView视图创建，响应此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        flag = true;
        // 实例线程
        th = new Thread(this);
        // 启动线程
        th.start();
    }

    /**
     * 加载游戏资源
     */
    private void initGame() {
        // 加载游戏资源
        bmpBackGround = BitmapFactory
                .decodeResource(res, R.drawable.contact_bg);

        gameBg = new GameBg(bmpBackGround,false);

    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            try {
                canvas = sfh.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    gameBg.draw(canvas, paint);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null)
                    sfh.unlockCanvasAndPost(canvas);
            }
            gameBg.logicX();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SurfaceView视图状态发生改变，响应此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * SurfaceView视图消亡时，响应此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}