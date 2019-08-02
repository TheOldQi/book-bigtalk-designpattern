package com.qixiafei.bookbigtalkdesignpattern;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE AT: 2019/3/29 19:41</P>
 * <P>UPDATE AT: 2019/3/29 19:41</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
public class NioClient implements Runnable {


    @Override
    public void run() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.connect(new InetSocketAddress("localhost", 8573));
            while (true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                buffer.put(bytes);
                buffer.flip();
                sc.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new NioClient()).start();
    }
}
