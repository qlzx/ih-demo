package com.example.demo.netty.start;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lh0
 * @date 2021/6/19
 * @desc
 */
public class MyNettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel nioSocketChannel) throws Exception {
                    System.out.println("客户端channel初始化");
                    nioSocketChannel.pipeline().addLast(new FirstClientHandler());
                }
            })
        ;
        connect(bootstrap,"127.0.0.1",8000,3);

    }

    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        connect(bootstrap, host, port, retry, 1);
    }

    private static void connect(Bootstrap bootstrap,String host,int port,int retry,int current){
        bootstrap.connect(host,port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("连接建立成功");
                } else if (retry == 0) {
                    System.err.println("连接建立失败");
                } else{
                    System.out.println("第"+current+"次重试建立连接");
                    // 以2的幂次方来进行重试
                    bootstrap.config().group().schedule(() -> {
                        connect(bootstrap, host, port, retry - 1, current + 1);
                    }, 1L << current, TimeUnit.SECONDS);
                }
            }
        });
    }
}
