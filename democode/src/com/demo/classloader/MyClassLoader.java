package com.demo.classloader;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.nio.file.Files;

public class MyClassLoader extends ClassLoader {
    // 类名
    private String name;
    // 路径
    private String path;

    public MyClassLoader(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * 返回一个Class
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 获取二进制流
        byte[] b = new byte[1024];
        try {
            b = Files.readAllBytes(new File(path + name + ".class").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //loadClassData(name);
        // 传入流返回Class
        return defineClass(name, b, 0, b.length);
    }

    /**
     * 读取.class字节码文件，返回byte数组
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        name = path + name + ".class";
        InputStream in = null;
        ByteArrayOutputStream ou = null;
        try {
            in = new FileInputStream(new File(name));
            ou = new ByteArrayOutputStream();
            int i = 0;
            while (((i = in.read()) != -1)) {
                ou.write(i);
            }
            ou.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ou.toByteArray();
    }
}
