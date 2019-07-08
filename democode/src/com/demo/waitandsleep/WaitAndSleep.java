package com.demo.waitandsleep;

public class WaitAndSleep {
    public static void main(String[] args) {
        final Object obj = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程A等待获取锁...");
                synchronized (obj) {
                    System.out.println("A获取了锁！");
                    try {
                        Thread.sleep(20);
                        System.out.println("A执行wait方法...");
//                        obj.wait(1000);
                        obj.wait();
                        System.out.println("A结束！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程B等待获取锁...");
                synchronized (obj) {
                    System.out.println("B获取了锁！");
                    try {
                        System.out.println("B执行sleep方法...");
                        Thread.sleep(10);
                        // 唤醒线程
                        obj.notify();
                        System.out.println("B结束！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
