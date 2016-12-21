package com.mvp.rxandroid;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;

/**
 * Created by zhuMH on 16/12/19.
 */

public class JavaMethodTest {

    @Test
    public void testInteger(){
        Integer a = 1000;
        Integer b = 1000;
//        Assert.assertTrue("a==b?"+ (a==b),a == b);
//        Assert.assertEquals(a,b);
        File file = new File("");
        Assert.assertEquals("file--" + file.mkdir() , file == null);
    }

    @Test
    public void testInteger100(){
        Integer a = 100;
        Integer b = 100;
        Assert.assertTrue("a==b?"+ (a==b),a == b);
    }

}
