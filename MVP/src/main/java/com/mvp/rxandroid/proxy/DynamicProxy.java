package com.mvp.rxandroid.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by elang on 16/7/4.
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * 操作者
     */
    private Object proxy;

    /**
     * 要处理的对象
     */
    private Object delegate;

    /**
     * 动态生成方法被处理过后的对象(写法固定)
     *
     * @param delegate
     * @param proxy
     * @return Object
     */
    public Object bind(Object delegate, Object proxy) {
        this.delegate = delegate;
        this.proxy = proxy;
        return Proxy.newProxyInstance(
                this.delegate.getClass().getClassLoader(), this.delegate
                        .getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object result = null;
        try {
            // 执行原来的方法之前记录日志
            Log.e("elang", method.getName() + " method start");

            // JVM通过这条语句执行原来的方法(反射机制)
            result = method.invoke(this.delegate, objects);

            // 执行原来的方法之后记录日志
            Log.e("elang", method.getName() + " method end");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回方法返回值给调用者
        return result;
    }
}
