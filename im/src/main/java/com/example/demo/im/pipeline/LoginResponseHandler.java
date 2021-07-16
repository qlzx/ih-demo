package com.example.demo.im.pipeline;

import java.util.UUID;

import com.example.demo.im.pack.LoginRequestPacket;
import com.example.demo.im.pack.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
        LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            System.out.println("登陆成功");
        }else{
            System.out.println("登陆失败" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }


}
