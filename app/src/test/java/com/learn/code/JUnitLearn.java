package com.learn.code;

import com.lfp.androidrapiddevelopmentframework.net.UnifyRespond;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * <pre>
 * desc:
 *      JUnit4 单元测试学习
 *
 *                                      循环执行所有未被忽略的Test方法
 *                                       ┌────────────┐
 *                                       ↓                        ↑
 *      执行顺序        :@BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 *      单个方法执行顺序:@Before -> @Test -> @After;
 *
 *
 *      执行方法:
 *      1.在标记为@Test的方法中 右键 → Run ** 执行单个测试用例
 *      2.在类中空白区域 右键 → Run ** 顺序执行本类中所有标记为@Test的测试用例
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
public class JUnitLearn {


    @Before  /*初始化方法 对于每一个测试方法都要执行一次*/
    public void Before() {
        System.out.println("-->> Before()");
    }

    @BeforeClass /*针对所有测试，只执行一次，且必须为static void*/
    public static void BeforeClass() {
        System.out.println("-->> BeforeClass()");
    }

    @Test/*测试方法*/
    public void test() {
        int test = UnifyRespond.FLAG_SHOW_DIALOG;
        /*通过断言来判断输出数据的正确性*/
        assertEquals(test, 0x1 << 1);
    }

    @Test(expected = ArithmeticException.class) /*测试方法 - 检查方法是否抛出ArithmeticException异常*/
    public void test2() {
        int test = UnifyRespond.FLAG_SHOW_DIALOG;
        /*通过断言来判断输出数据的正确性*/
        assertEquals(test, 0x1 << 1);
    }

    @Ignore /*忽略这个测试方法*/
    @Test/*测试方法*/
    public void test3() {
        int test = UnifyRespond.FLAG_SHOW_DIALOG;
        /*通过断言来判断输出数据的正确性*/
        assertEquals(test, 0x1 << 1);
    }

    @After /*释放资源 对于每一个测试方法都要执行一次*/
    public void After() {
        System.out.println("-->> After()");
    }

    @AfterClass /*释放资源 针对所有测试，只执行一次*/
    public static void AfterClass() {
        System.out.println("-->> AfterClass()");
    }

}