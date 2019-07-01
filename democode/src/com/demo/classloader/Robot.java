package com.demo.classloader;

public class Robot {

    static {
        System.out.println("loading...");
    }

    private String name;

    /**
     * @param str
     */
    public void sayHi(String str) {
        System.out.println(str + " " + name);
    }

    /**
     * @param str
     * @return
     */
    private String throwHello(String str) {
        return "hello " + str;
    }

}
