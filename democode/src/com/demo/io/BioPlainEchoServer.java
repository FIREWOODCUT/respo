package com.demo.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建线程池避免频繁创建销毁线程的开销.
 * 但如果连接数过多，很多请求难以得到响应.
 */
public class BioPlainEchoServer {
    public void serve(int port) throws IOException {
        // 将ServerSocket绑定到指定端口
        final ServerSocket socket = new ServerSocket(port);
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        while (true) {
            // 阻塞直到收到新的客户端连接
            final Socket clientsocket = socket.accept();
            System.out.println("接收到连接来自" + clientsocket);
            // 将请求交给线程池去执行
            executorService.execute(() -> {
                // 执行业务
            });
        }
    }
}
