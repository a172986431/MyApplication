/*
 * Copyright 2014 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvp.rxandroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.mvp.rxandroid.R;

/**
 * 可展开的文本框
 */
public class ReadMoreTextView extends TextView {

    private int mMaxLines;
    private BufferType mBufferType = BufferType.NORMAL;
    private CharSequence mText;
    private String mMoreText;
    private String mLessText;
    private int mMoreColor;
    private int mLessColor;
    //省略符号，可以自行定义
    private String ellipsis = "...";
    //填充空格
    private String space = " ";

    public ReadMoreTextView(Context context) {
        super(context);
        setup();
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setup();
    }

    public ReadMoreTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        setup();
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.ReadMoreTextView);
        mMoreText = a.getString(R.styleable.ReadMoreTextView_rmtMoreText);
        mLessText = a.getString(R.styleable.ReadMoreTextView_rmtLessText);
        mMoreColor = a.getInteger(R.styleable.ReadMoreTextView_rmtMoreColor, Color.BLUE);
        mLessColor = a.getInteger(R.styleable.ReadMoreTextView_rmtLessColor, Color.BLUE);
        a.recycle();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public void setMoreText(String more) {
        mMoreText = more;
    }

    public void setLessText(String less) {
        mLessText = less;
    }


    @Override
    public void setMaxLines(int maxlines) {
        mMaxLines = maxlines;
        setup();
        requestLayout();
    }

    @Override
    public void setText(final CharSequence text, BufferType type) {
        mText = text;
        mBufferType = type;
        setup();
        super.setText(text, type);
    }

    @Override
    public CharSequence getText() {
        return mText;
    }

    private void setup() {
        if (mListener == null || mMaxLines < 1 || mText == null) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(mListener);

    }

    private ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);

            if (getLineCount() <= mMaxLines) {
                return;
            }

            final CharSequence summary = createSummary();
            setTextInternal(summary);
            setOnClickListener(new OnClick(summary));
        }
    };

    private Spanned create(CharSequence content, String label, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(label);
        builder.setSpan(new ForegroundColorSpan(color), 0, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(content).append(builder);
    }

    private CharSequence createContent() {
        //必须先设置text才能进行修改
        setTextInternal(mText);
        if (mLessText == null || mLessText.length() == 0) {
            return mText;
        }
        Layout layout = getLayout();
        //获得textView的最后一行
        int start = layout.getLineStart(getLineCount() - 1);
        //把最后一行和点击的文字加起来判断是否超出。
        String content = mText.toString().substring(start, mText.length()) + mLessText;
        int len = getPaint().breakText(content, 0, content.length(), true, layout.getWidth(), null);
        //len小于content的大小的话说明要换行
        if (len < content.length()){
            content = content.substring(len,content.length());
        }
        //填充的字符，如果是空格的话在开头会被textView自动删除所以赋值其他字符
        space = mLessText.substring(0,1);
        String moreSpace = "";
        float width = 0;
        //判断是否填满了一行
        while (width < layout.getWidth()){
            content = content + space;
            width = getPaint().measureText(content, 0, content.length());
            if (width < layout.getWidth()) {
                moreSpace = moreSpace + space;
            }
        }
        //将填充的字符设为透明
        Spanned fill = create(mText, moreSpace, android.R.color.transparent);
        return create(fill, mLessText, mLessColor);
    }

    private CharSequence createSummary() {
        if (mMoreText == null || mMoreText.length() == 0) {
            return mText;
        }
        Layout layout = getLayout();
        //返回指定的文本偏移的开始
        int start = layout.getLineStart(mMaxLines - 1);
        //返回文本抵消在指定行最后一个字符之后。
        int end = layout.getLineEnd(mMaxLines - 1)  - start;

        //返回一个新CharSequence进行这个序列的子序列。
        CharSequence content = mText.subSequence(start, mText.length());
        CharSequence extra = ellipsis + mMoreText;

        float moreWidth = getPaint().measureText(extra, 0, extra.length());
        float maxWidth = layout.getWidth() - moreWidth;
        //测量的文本,早期如果测量宽度超过maxWidth停止
        int len = getPaint().breakText(content, 0, content.length(), true, maxWidth, null);
        if (content.charAt(end - 1) == '\n') {
            end = end - 1;
        }
        len = Math.min(len, end);
        return create(mText.subSequence(0, start + len) + ellipsis, mMoreText, mMoreColor);
    }

    private void setTextInternal(CharSequence text) {
        super.setText(text, mBufferType);
    }

    private class OnClick implements View.OnClickListener {

        boolean expand = false;
        CharSequence summary;
        CharSequence content;

        OnClick(CharSequence s) {
            this.summary = s;
        }

        @Override
        public void onClick(View view) {
            if (expand) {
                if (content == null) {
                    content = createContent();
                }
                setTextInternal(content);
            } else {
                setTextInternal(summary);
            }
            expand = !expand;
        }
    }
}
