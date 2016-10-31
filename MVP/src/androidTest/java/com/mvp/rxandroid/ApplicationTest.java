package com.mvp.rxandroid;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.mvp.rxandroid.activity.contact.ContactPresenter;

import org.testng.annotations.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Test
    public void con() throws Exception {
        ContactPresenter presenter = new ContactPresenter();
        presenter.getLocalContactsInfos(null,"");
    }
}