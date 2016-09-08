package com.mvp.rxandroid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class itemDecoration extends RecyclerView.ItemDecoration {

    private int Orieation = LinearLayoutManager.VERTICAL;

    private int itemsize = 1;

    private Paint mPaint;

    private Context mContext;

    public itemDecoration(Context m) {
        this.mContext = m;
        if (Orieation != LinearLayoutManager.VERTICAL && Orieation != LinearLayoutManager.HORIZONTAL)
        {
            throw new IllegalArgumentException("error Orieation");
        }

        itemsize = (int) TypedValue.applyDimension(itemsize,TypedValue.COMPLEX_UNIT_DIP,m.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (Orieation == LinearLayoutManager.VERTICAL)
        {
            drawVertical(c,parent);
        }
        else
        {
            drawHorizontal(c,parent);
        }
    }

    private void drawHorizontal(Canvas canvas,RecyclerView parent)
    {
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childsize = parent.getChildCount();
        for (int i=0;i<childsize;i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + itemsize;
            canvas.drawRect(left,top,right,bottom,mPaint);
        }
    }

    private void drawVertical(Canvas canvas,RecyclerView parent)
    {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childsize = parent.getChildCount() - 1;
        for (int i = 0;i<childsize;i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + itemsize;
            canvas.drawRect(left,top,right,bottom,mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (Orieation == LinearLayoutManager.VERTICAL)
        {
            outRect.set(0,0,0,itemsize);
        }
        else
        {
            outRect.set(0,0,itemsize,0);
        }
    }
}
