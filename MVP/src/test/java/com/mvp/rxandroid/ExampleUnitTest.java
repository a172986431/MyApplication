package com.mvp.rxandroid;

import android.test.AndroidTestCase;

import com.mvp.rxandroid.activity.contact.ContactPresenter;
import com.mvp.rxandroid.app.MvpApp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends AndroidTestCase{

    @Before
    public void appStart() throws Exception {

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void con() throws Exception {
        ContactPresenter presenter = new ContactPresenter();
//        MvpApp.mvpApp = (MvpApp) getContext();
        presenter.getLocalContactsInfos(null,"");
    }
}