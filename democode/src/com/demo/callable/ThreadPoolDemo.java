package com.demo.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池提交执行实现callable接口的任务
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 定义一个线程池
        ExecutorService newpool = Executors.newCachedThreadPool();
        // 提交任务接收Future
        Future<String> future = newpool.submit(new MyCallable());
        if (!future.isDone()) {
            System.out.println("please wait...");
        }
        try {
            System.out.println("return " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            newpool.shutdown();
        }
    }
}
