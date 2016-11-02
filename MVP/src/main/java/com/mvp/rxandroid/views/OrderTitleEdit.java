package com.mvp.rxandroid.views;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by sh-xiayf on 16/8/25.
 */
public class OrderTitleEdit extends EditText {

    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    private Context mContext;

    public OrderTitleEdit(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public OrderTitleEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public OrderTitleEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    }


    // 初始化edittext 控件
    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    inputAfterText = s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    if (count >= 1) {//表情符号的字符长度最小为2
                        CharSequence input = s.subSequence(start, start + count);
                        if (containsEmoji(input.toString())) {
                            resetText = true;
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText);
                            CharSequence text = getText();
                            if (text instanceof Spannable) {
                                Spannable spanText = (Spannable) text;
                                Selection.setSelection(spanText, text.length());
                            }
                        }
                        checkMaxEdit(s, 10);
                    }
                } else {
                    resetText = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        String string = "";
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (codePoint == 0x20){
                continue;
            }
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
            if (issign(codePoint)) {
                return true;
            }
        }
        return false;
    }

    private static boolean issign(char codePoint) {
        return ((codePoint >= 0) && (codePoint <= 47)) ||
                ((codePoint >= 58) && (codePoint <= 64)) ||
                ((codePoint >= 91) && (codePoint <= 96)) ||
                ((codePoint >= 123) && (codePoint <= 127)) ||
                (codePoint == 0x3002) ||
                (codePoint == 0xFF1F) || (codePoint == 0xFF01) || (codePoint == 0xFF0C) ||
                (codePoint == 0x3001) || (codePoint == 0xFF1B) || (codePoint == 0xFF1A) ||
                (codePoint == 0x300C) || (codePoint == 0x300D) || (codePoint == 0x300E) ||
                (codePoint == 0x300F) || (codePoint == 0x2018) || (codePoint == 0x2019) ||
                (codePoint == 0x201C) || (codePoint == 0x201D) || (codePoint == 0xFF08) ||
                (codePoint == 0xFF09) || (codePoint == 0x3014) || (codePoint == 0x3015) ||
                (codePoint == 0x3010) || (codePoint == 0x3011) || (codePoint == 0x2014) ||
                (codePoint == 0x2026) || (codePoint == 0x2013) || (codePoint == 0xFF0E) ||
                (codePoint == 0x300A) || (codePoint == 0x300B) || (codePoint == 0x3008) ||
                (codePoint == 0x3009) || (codePoint == 0xff3b) || (codePoint == 0xff3d) ||
                (codePoint == 0xff5e) || (codePoint == 0xB7) || (codePoint == 0xffe5);
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 检查EditText内的字是否超过最大长度
     * 中文2个长度，英文一个长度
     *
     * @param src    text框改变后的值
     * @param maxLen 最大长度
     */
    private void checkMaxEdit(CharSequence src, int maxLen) {
        int dindex = 0;
        int count = 0;
        while (count <= maxLen && dindex < src.length()) {
            char c = src.charAt(dindex++);
            if (c < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
        }



        if (count > maxLen) {
            setText(src.subSequence(0, dindex - 1));
            CharSequence text = getText();
            if (text instanceof Spannable) {
                Spannable spanText = (Spannable) text;
                Selection.setSelection(spanText, text.length());
            }
        }
    }

}
