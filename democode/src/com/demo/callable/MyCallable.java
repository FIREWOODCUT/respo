package com.demo.callable;

import java.util.concurrent.Callable;

/**
 * Callable和Runnable有几点不同：
 * （1）Callable规定的方法是call()，而Runnable规定的方法是run().
 * （2）Callable的任务执行后可返回值，而Runnable的任务是不能返回值的.
 * （3）call()方法可抛出异常，而run()方法是不能抛出异常的.
 * （4）运行Callable任务可拿到一个Future对象.
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        String value = "test";
        System.out.println("ready");
        Thread.sleep(5000);
        System.out.println("done");
        return value;
    }
}
