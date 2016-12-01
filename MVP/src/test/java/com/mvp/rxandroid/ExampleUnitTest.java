package com.mvp.rxandroid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
// 指定运行器runner:使用参数化运行器来运行
@RunWith(Parameterized.class)//表示带参数运行，不加就只能默认运行无参构造函数
public class ExampleUnitTest {

    private int one;
    private int two;
    private int sum;
    private String result;

    public ExampleUnitTest(int sum, int one, int two,String result) {
        this.sum = sum;
        this.one = one;
        this.two = two;
        this.result = result;
    }

    @Parameterized.Parameters(name = "{3}") //添加多重参数,name表示测试的名字，{3}表示传入第四个参数作为名字
    public static Collection prepareData() {
        // 测试数据
        Object[][] objects = {{3, 1, 2,"123"}, {-4, -2, -3,"234"}, {5, 2, 3,"235"},
                {4, -4, 9,"-494"}};
        return Arrays.asList(objects);
    }

    @Before
    public void appStart() throws Exception {

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void con() throws Exception {
        assertFalse("result->" + (4 > 2), 4 > 2);
    }

    @Test
    public void plus() {
        assertEquals(sum, one + two);
    }

    @Test
    public void result() {
        assertEquals(result, one + "" + two + "" + sum);
    }
}