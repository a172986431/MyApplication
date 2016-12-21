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


public class ReadMoreTextView extends TextView {

    private int mMaxLines;
    private BufferType mBufferType = BufferType.NORMAL;
    private CharSequence mText;
    //用来整理中英文混编
    private String fixText;
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
        fixText = text.toString();
        if (fixText != null && fixText.length() > 0 && fixText.charAt(fixText.length() - 1) == '\n') {
            fixText = fixText.substring(0, fixText.length() - 1);
        }
        mBufferType = type;
        setup();
        super.setText(fixText, type);
    }

    @Override
    public CharSequence getText() {
        return mText;
    }

    private void setup() {
        if (mListener == null || mMaxLines < 1 || fixText == null) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(mListener);

    }

    private ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
            fixTextWithEnter(0);
            if (getLineCount() <= mMaxLines) {
                return;
            }

            final CharSequence summary = createSummary();
            setTextInternal(summary);
            setOnClickListener(new OnClick(summary));
        }
    };

    /**
     * 给添加的字段设置颜色
     * @param content 文本的字段
     * @param label 新添加的字段
     * @param color 新添加字段的颜色
     * @return
     */
    private Spanned create(CharSequence content, String label, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(label);
        builder.setSpan(new ForegroundColorSpan(color), 0, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(content).append(builder);
    }

    /**
     * 展开文本，并判断最后展开提示是否需要换行
     *
     * @return
     */
    private CharSequence createContent() {
        //必须先设置text才能进行修改
        setTextInternal(fixText);
        if (mLessText == null || mLessText.length() == 0) {
            return fixText;
        }
        Layout layout = getLayout();
        //获得textView的最后一行
        int start = layout.getLineStart(getLineCount() - 1);
        //把最后一行和点击的文字加起来判断是否超出。
        String content = fixText.toString().substring(start, fixText.length()) + mLessText;
        int len = getPaint().breakText(content, 0, content.length(), true, layout.getWidth(), null);
        //len小于content的大小的话说明要换行
        if (len < content.length()) {
            content = content.substring(len, content.length());
        }
        //填充的字符，如果是空格的话在开头会被textView自动删除所以赋值其他字符
        space = mLessText.substring(0, 1);
        String moreSpace = "";
        float width = 0;
        //判断是否填满了一行
        while (width < layout.getWidth()) {
            content = content + space;
            width = getPaint().measureText(content, 0, content.length());
            if (width < layout.getWidth()) {
                moreSpace = moreSpace + space;
            }
        }
        //将填充的字符设为透明
        Spanned fill = create(fixText, moreSpace, android.R.color.transparent);
        return create(fill, mLessText, mLessColor);
    }

    /**
     * 创建收起后的文本并填充有换行符的行
     *
     * @return
     */
    private CharSequence createSummary() {
        if (mMoreText == null || mMoreText.length() == 0) {
            return fixText;
        }
        Layout layout = getLayout();
        //返回指定的文本偏移的开始
        int start = layout.getLineStart(mMaxLines - 1);
        //返回文本抵消在指定行最后一个字符之后。
        int end = layout.getLineEnd(mMaxLines - 1) - start;

        //返回一个新CharSequence进行这个序列的子序列。
        CharSequence content = fixText.subSequence(start, start + end);
        CharSequence extra = ellipsis + mMoreText;

        float moreWidth = getPaint().measureText(extra, 0, extra.length());
        float maxWidth = layout.getWidth() - moreWidth;
        //测量的文本,早期如果测量宽度超过maxWidth停止
        int len = getPaint().breakText(content, 0, content.length(), true, maxWidth, null);
        if (content.charAt(end - 1) == '\n') {
            end = end - 1;
        }
        content = fixText.subSequence(start, start + len);
        String moreSpace = "";
        float width = 0;
        //判断是否填满了一行
        while (width < maxWidth) {
            content = content + space;
            width = getPaint().measureText(content, 0, content.length());
            if (width < maxWidth) {
                moreSpace = moreSpace + space;
            }
        }
        len = Math.min(len, end);
        //将填充的字符设为透明
        Spanned fill = create(fixText.subSequence(0, start + len) + ellipsis, moreSpace, android.R.color.transparent);
        return create(fill, mMoreText, mMoreColor);
    }

    /**
     * 整理中英文混合排序提前换行的问题
     *
     * @param line 从那一行开始往下整理【排列
     */
    private void fixTextWithEnter(int line) {
        if (fixText == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(fixText);
        int textLine = getLineCount();
        Layout layout = getLayout();
        int start = layout.getLineStart(line);
        int end = layout.getLineEnd(line);
        try {
            String content = fixText.substring(start, end);
            int len = getPaint().breakText(fixText, start, fixText.length(), true, layout.getWidth(), null);
            //判断如果提前换行了则加换行符进行截断
            if (content.contains("\n") || len == end - start) {
            } else {
                sb.insert(start + len, "\n");
                fixText = sb.toString();
                setTextInternal(fixText);
            }
        } catch (Exception e) {
        }
        if (line + 1 < textLine) {
            fixTextWithEnter(line + 1);
        }
    }

    /**
     * 设置文本，不用自己的setText不然getText会改变了
     *
     * @param text
     */
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
            if (!expand) {
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
