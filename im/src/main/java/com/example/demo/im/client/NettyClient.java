package com.example.demo.im.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.example.demo.im.codec.PacketCodeC;
import com.example.demo.im.pack.MessageRequestPacket;
import com.example.demo.im.pipeline.LoginResponseHandler;
import com.example.demo.im.pipeline.MessageResponseHandler;
import com.example.demo.im.pipeline.PacketDecoder;
import com.example.demo.im.pipeline.PacketEncoder;
import com.example.demo.im.pipeline.Spliter;
import com.example.demo.im.session.SessionUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class NettyClient {

    public static void main(String[] args) {
        connect("127.0.0.1", 8000);
    }

    public static void connect(String host, int port) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
            .group(workGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    System.out.println("initChannel 开始执行");
                    nioSocketChannel.pipeline().addLast(new Spliter());
                    nioSocketChannel.pipeline().addLast(new PacketDecoder());
                    nioSocketChannel.pipeline().addLast(new LoginResponseHandler());
                    nioSocketChannel.pipeline().addLast(new MessageResponseHandler());
                    nioSocketChannel.pipeline().addLast(new PacketEncoder());
                }
            });
        connect(bootstrap, host, port, 3);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        connect(bootstrap, host, port, retry, 0);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry,
        int hasRetry) {
        bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("服务连接建立成功");

                    startConsoleThread(channelFuture.channel());
                } else if (retry == 0) {
                    System.out.println("服务连接建立失败");
                } else {
                    bootstrap.config().group().schedule(() -> {
                        connect(bootstrap, host, port, retry - 1, hasRetry + 1);
                    }, 1L << hasRetry, TimeUnit.SECONDS);
                }

            }
        });

    }

    private static void startConsoleThread(Channel channel) {
        new Thread(()->{
            while (!Thread.interrupted()) {
                //if (SessionUtils.isLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setMessage(line);
                    channel.writeAndFlush(
                        PacketCodeC.getInstance().encode(channel.alloc(), messageRequestPacket));
                }
            //}
        }).start();
    }
}
