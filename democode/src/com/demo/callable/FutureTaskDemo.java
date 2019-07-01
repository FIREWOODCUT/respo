package com.demo.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过FutureTask执行实现callable接口的任务
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 传入任务
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        // 启动线程
        new Thread(task).start();
        // 可监控是否处理完成
        if (!task.isDone()) {
            System.out.println("please wait...");
        }
        // 返回处理结果
        System.out.println("return " + task.get());
    }
}
