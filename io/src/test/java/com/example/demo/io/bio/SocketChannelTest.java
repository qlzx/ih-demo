package com.example.demo.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.demo.io.nio.SelectorThread;
import org.junit.Test;

/**
 * @author lh0
 * @date 2021/8/7
 * @desc
 */
public class SocketChannelTest {

    @Test
    public void client() throws IOException, InterruptedException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1", 8000));
        ByteBuffer b = ByteBuffer.allocate(256);
        b.putInt(2321);
        b.flip();
        client.write(b);
        Thread.sleep(5000);
        client.close();
    }

    @Test
    public void bioClient() throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8000);

        socket.getOutputStream().write("123".getBytes());

        socket.getOutputStream().flush();

        socket.close();

    }

    @Test
    public void server() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8000));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功");
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey k = keyIterator.next();
                keyIterator.remove();
                if (k.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) k.channel();
                    SocketChannel clientChannel = channel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (k.isReadable()) {
                    SocketChannel channel = null;
                    try {
                        System.out.println("客户端可读");
                        channel = (SocketChannel) k.channel();
                        ByteBuffer b = ByteBuffer.allocate(256);
                        int read = channel.read(b);
                        System.out.println(read);
                        if (read == -1) {
                            channel.close();
                        }
                        if (read == 0) {
                            System.out.println("客户端输入空");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        channel.close();
                    }
                }
            }

        }


    }

    @Test
    public void round(){
        List<String> list = Arrays.asList("1", "2");
        List<String> strings = Collections.unmodifiableList(new ArrayList<String>(2));

        strings.iterator().next();


    }
}
