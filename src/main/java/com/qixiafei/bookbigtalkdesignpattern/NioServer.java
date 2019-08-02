package com.qixiafei.bookbigtalkdesignpattern;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE AT: 2019/3/26 18:16</P>
 * <P>UPDATE AT: 2019/3/26 18:16</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
public class NioServer implements Runnable {

    private Selector selector;
    private ByteBuffer inBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer outBuffer = ByteBuffer.allocate(1024);

    public NioServer(int port) {
        try {
            this.selector = Selector.open();
            final ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.bind(new InetSocketAddress("localhost", port));
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server socket start at port:" + port);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("准备select上阻塞");
                this.selector.select();
                System.out.println("发现有请求");
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                System.out.println("selectionKeys：" + iterator);
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            accept(key);
                        }
                        if (key.isReadable()) {
                            read(key);
                        }
                        if (key.isWritable()) {
                            write(key);
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(SelectionKey key) {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//        try {
//            channel.register(this.selector, SelectionKey.OP_WRITE);
//        } catch (ClosedChannelException e) {
//            e.printStackTrace();
//        }

    }

    private void accept(SelectionKey key) {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel accept = channel.accept();
            accept.configureBlocking(false);
            accept.register(this.selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        this.inBuffer.clear();
        try {
            int count = channel.read(this.inBuffer);
            if (count == -1) {
                channel.close();
                key.cancel();
                return;
            }
            this.inBuffer.flip();
            byte[] bytes = new byte[this.inBuffer.remaining()];
            this.inBuffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server: " + body);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new NioServer(8573)).start();
    }
}
