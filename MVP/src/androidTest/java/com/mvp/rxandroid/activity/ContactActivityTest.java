package com.mvp.rxandroid.activity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.mvp.rxandroid.activity.contact.ContactActivity;
import com.mvp.rxandroid.activity.contact.ContactInfo;
import com.mvp.rxandroid.activity.contact.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuMH on 16/11/22.
 */

public class ContactActivityTest extends ActivityInstrumentationTestCase2<ContactActivity> {
    private Activity activity;

    public ContactActivityTest() {
        super(ContactActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testContactPresenter(){
        ContactPresenter presenter = new ContactPresenter();
        Log.e("elang","begin");
        List<ContactInfo> list = presenter.getLocalContactsInfos(new ArrayList<ContactInfo>(),activity);
        Log.e("elang","end");
        assertTrue("成功->" + list.size(),list.size() > 0);
    }
}
