package com.example.demo.im.client;

import java.util.Date;
import java.util.UUID;

import com.example.demo.im.codec.PacketCodeC;
import com.example.demo.im.pack.BasePacket;
import com.example.demo.im.pack.LoginRequestPacket;
import com.example.demo.im.pack.LoginResponsePacket;
import com.example.demo.im.pack.MessageResponsePacket;
import com.example.demo.im.session.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");
        System.out.println("开始登陆请求");
        ByteBuf byteBuf = PacketCodeC.getInstance().encode(ctx.alloc(),loginRequestPacket);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("获取到服务端响应");
        ByteBuf buffer = (ByteBuf) msg;
        BasePacket basePacket = PacketCodeC.getInstance()
            .decode(buffer);

        if (basePacket instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) basePacket;
            if (loginResponsePacket.isSuccess()) {
                System.out.println("登陆成功");
                SessionUtils.markLogin(ctx.channel());
            } else {
                System.out.println("登陆失败:" + loginResponsePacket.getReason());
            }
        } else if (basePacket instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) basePacket;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
