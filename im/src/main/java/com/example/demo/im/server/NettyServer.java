package com.example.demo.im.server;

import com.example.demo.im.pipeline.AuthHandler;
import com.example.demo.im.pipeline.LoginRequestHandler;
import com.example.demo.im.pipeline.MessageRequestHandler;
import com.example.demo.im.pipeline.PacketDecoder;
import com.example.demo.im.pipeline.PacketEncoder;
import com.example.demo.im.pipeline.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class NettyServer {
    public static void main(String[] args) {
        start(8000);
    }

    public static void start(int port){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
            .group(boss, worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioServerSocketChannel)
                    throws Exception {
                    System.out.println("工作线程开始initChannel");
                    nioServerSocketChannel.pipeline().addLast(new Spliter());
                    nioServerSocketChannel.pipeline().addLast(new PacketDecoder());
                    nioServerSocketChannel.pipeline().addLast(new LoginRequestHandler());
                    nioServerSocketChannel.pipeline().addLast(new AuthHandler());
                    nioServerSocketChannel.pipeline().addLast(new MessageRequestHandler());
                    nioServerSocketChannel.pipeline().addLast(new PacketEncoder());
                }
            });
        bind(serverBootstrap, port);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
