package com.mvp.rxandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 自定义显示星星的布局
 */
public class StarLayout extends LinearLayout {
	
	private Context context;
	/**
	 * 亮的星星的Id
	 */
	private int lightStar;
	/**
	 * 暗的星星Id
	 */
	private int darkStar;

	public StarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void setLightStarRes(int res){
		lightStar = res;
	}
	
	public void setDarkStarRes(int res){
		darkStar = res;
	}

	/**
	 * 设置星星
	 * @param num 多少个亮起来的
	 * @param total 星星的总数
     */
	public void setLightStarNum(int num,int total){
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		removeAllViews();
		for (int i = 0; i < total; i++) {
			ImageView imageView = new ImageView(context);
			
			if (i < num) {				
				imageView.setBackgroundResource(lightStar);
			}else {
				imageView.setBackgroundResource(darkStar);
			}
			params.leftMargin=6;
			this.addView(imageView, params);
		}
	}

}
