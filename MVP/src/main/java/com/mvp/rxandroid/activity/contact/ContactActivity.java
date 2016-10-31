package com.mvp.rxandroid.activity.contact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mvp.rxandroid.R;

import java.util.List;

/**
 * Created by zhuMH on 16/10/18.
 */

public class ContactActivity extends Activity {

    private List<ContactInfo> contactInfos;
    private ContactPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        presenter = new ContactPresenter();
        presenter.getLocalContactsInfos(contactInfos,"");
    }

    public void queryContact(View view){

    }
}
