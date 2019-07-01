package com.demo.classloader;

public class CheckClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader my = new MyClassLoader("MyClassLoader", "D:/IntelliJ IDEA 2019.1.3/myspace/democode/src/com/demo/classloader/");
        Class cl = my.findClass("Robot");
        cl.newInstance();
        //Class c = Class.forName("Robot");
        //ClassLoader c = Robot.class.getClassLoader();
    }
}
