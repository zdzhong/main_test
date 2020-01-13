package com.zhong.test;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class TestUtil {

    private static final Logger logger = LoggerFactory.getLogger(TestUtil.class);

    @Before
    public void initData(){

    }

    @Test
    public void regularTest(){
        long startTime = System.currentTimeMillis();
        function1();
        function2();
        function3();
        int result = function4();
        result = function5(result);
        long endTime = System.currentTimeMillis();
        System.out.println("运行结果：" + result + ",耗时：" + (endTime - startTime));
    }

    @Test
    public void completableFutureTest(){
        long startTime = System.currentTimeMillis();
        // 在进入主线程时首先异步调用function4
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(this::function4);
        // 其他接口同步进行
        function1();
        function2();
        function3();
        int result = 0;
        // function5依赖function4返回的结果
        try {
            result = future.get();
        } catch (Exception e){
            result = 1;
            logger.error("error!");
        }
        result = function5(result);
        long endTime = System.currentTimeMillis();
        System.out.println("运行结果：" + result + ",耗时：" + (endTime - startTime));
    }

    private void function1(){
        // 模拟方法耗时100ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void function2(){
        // 模拟方法耗时200ms
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void function3(){
        // 模拟方法耗时200ms
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int function4(){
        // 模拟方法耗时500ms
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 4;
    }

    private int function5(int a){
        // 模拟方法耗时100ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a + 5;
    }
}
