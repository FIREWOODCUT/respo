package com.demo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 利用单线程轮询的机制，高效定位就绪的channel，仅仅select阶段是阻塞的，避免频繁切换线程带来的问题
 */
public class NioPlainEchoServer {
    public void serve(int port) throws IOException {
        // 创建Channel实例
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 创建ServerSocket
        ServerSocket serverSocket = socketChannel.socket();
        // 指定端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        serverSocket.bind(inetSocketAddress);
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        // 将channel注册到selector，并说明selector的关注点，这里关注的是建立连接
        // 阻塞状态无法注册该事件
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            try {
                // 阻塞等待就绪的channel，反复轮询新的连接
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                // 异常处理的逻辑省略
                break;
            }
            // 获取轮询结果
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理之前先移除
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        // 同样注册selector，关注读写，传入ByteBuffer实例作为读写缓存
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, ByteBuffer.allocate(100));
                    }
                    // SelectedKeys处于可读状态
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer out = (ByteBuffer) key.attachment();
                        client.read(out);
                    }
                    // 可写
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer out = (ByteBuffer) key.attachment();
                        // 反转
                        out.flip();
                        // 从缓存写入channel
                        client.write(out);
                        // 从缓存移除
                        out.compact();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
