package com.mvp.rxandroid.widgets.gamebg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBg {
 // 游戏背景的图片资源
 // 为了循环播放，这里定义两个位图对象，
 // 其资源引用的是同一张图片
 private Bitmap bmpBackGround1;
 private Bitmap bmpBackGround2;
 
 // 游戏背景坐标
 private int bg1x, bg1y, bg2x, bg2y;
 
 private int speed = 3;
 
 public GameBg(Bitmap bmpBackGround,boolean vertical) {
  this.bmpBackGround1 = bmpBackGround;
  this.bmpBackGround2 = bmpBackGround;
  if (vertical) {
   // 首先让第一张填满屏幕
   bg1y = -Math.abs(bmpBackGround.getHeight() - GameMoveBg.screenH);

   bg2y = bg1y - bmpBackGround1.getHeight() + 50;
   bg1x = 0;
   bg2x = 0;
  }else {
   bg1x = -Math.abs(bmpBackGround.getWidth() - GameMoveBg.screenW);
   bg2x = bg1x - bmpBackGround1.getWidth() + 50;
   bg1y = 0;
   bg2y = 0;
  }
 }
 
 public void draw(Canvas canvas, Paint paint){
 
  canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
  canvas.drawBitmap(bmpBackGround2, bg2x, bg2y, paint);
 }
 public void logic(){
  bg1y +=speed;
  bg2y +=speed;
 
  if(bg1y > GameMoveBg.screenH){
   bg1y = bg2y - bmpBackGround1.getHeight() +50;
  }
  if(bg2y > GameMoveBg.screenH){
   bg2y = bg1y - bmpBackGround1.getHeight() +50;
  }
 }

 public void logicX(){
  bg1x +=speed;
  bg2x +=speed;

  if(bg1x > GameMoveBg.screenW){
   bg1x = bg2x - bmpBackGround1.getWidth() +50;
  }
  if(bg2x > GameMoveBg.screenW){
   bg2x = bg1x - bmpBackGround1.getWidth() +50;
  }
 }
 
}