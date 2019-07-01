package com.demo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 基于事件和回调
 */
public class AioPlainEchoServer {
    public void serve(int port) throws IOException {
        final AsynchronousServerSocketChannel asynchronousChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        asynchronousChannel.bind(inetSocketAddress);
        final CountDownLatch latch = new CountDownLatch(1);
        // 接收客户端请求，调用CompletionHandler方法
        asynchronousChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(final AsynchronousSocketChannel channel, Object attachment) {
                // 处理完成继续接收请求
                asynchronousChannel.accept(null, this);
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                // 植入一个读操作EchoCompletionHandler，一旦有数据写入buffer
                channel.read(byteBuffer, byteBuffer, new EchoCompletionHandler(channel));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                try {
                    asynchronousChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        });
    }

    private final class EchoCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private final AsynchronousSocketChannel channel;

        EchoCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            buffer.flip();
            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        // buffer中有内容则写入channel
                        channel.write(buffer, buffer, this);
                    } else {
                        // 清除内容
                        buffer.compact();
                        // 如果还有内容则继续读操作
                        channel.read(buffer, buffer, EchoCompletionHandler.this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

        }
    }
}
