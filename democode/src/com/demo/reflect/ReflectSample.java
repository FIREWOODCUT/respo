package com.demo.reflect;

import com.demo.classloader.Robot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射获取类方法和成员变量
 */
public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        // 反射获取类
        Class<?> cl = Class.forName("com.demo.classloader.Robot");
        //Class<?> cl = Robot.class;
        System.out.println("class name is " + cl.getName());
        // 实例化
        Robot ro = (Robot) cl.newInstance();
        // 获取所有的方法
        Method[] method = cl.getDeclaredMethods();
        for (Method item : method) {
            System.out.println(item.getName());
            // 设置方法可访问
            item.setAccessible(true);
            // 获取参数类型
            Class<?>[] paramType = item.getParameterTypes();
            // 遍历
            Arrays.stream(paramType).forEach(it -> System.out.println("参数类型：" + it.getName()));
        }
//        Method me = cl.getDeclaredMethod("throwHello", String.class);
//        me.setAccessible(true);
//        Object obj = me.invoke(ro,"徐军");
//        System.out.println(obj);
        // 获取变量
        Field fi = cl.getDeclaredField("name");
        fi.setAccessible(true);
        fi.set(ro, "杰克");
        cl.getDeclaredMethod("sayHi", String.class).invoke(ro, "我是");
    }
}

