package com.mvp.rxandroid.activity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.mvp.rxandroid.activity.contact.ContactInfo;
import com.mvp.rxandroid.activity.contact.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuMH on 16/11/23.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    private Activity activity;

    public MainActivityTest() {
        super(MainActivity.class);
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
