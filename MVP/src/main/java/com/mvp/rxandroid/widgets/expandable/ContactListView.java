package com.mvp.rxandroid.widgets.expandable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by elang on 16/5/23.
 * 自定义联系人列表
 */
public class ContactListView extends ExpandableListView{
    public ContactListView(Context context) {
        super(context);
    }

    public ContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
