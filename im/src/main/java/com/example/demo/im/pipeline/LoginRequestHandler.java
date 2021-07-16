package com.example.demo.im.pipeline;

import com.example.demo.im.pack.LoginRequestPacket;
import com.example.demo.im.pack.LoginResponsePacket;
import com.example.demo.im.session.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
        LoginRequestPacket loginRequestPacket) throws Exception {

        LoginResponsePacket loginResponsePacket = login(channelHandlerContext,loginRequestPacket);
        channelHandlerContext.writeAndFlush(loginResponsePacket);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx,LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valida(loginRequestPacket)) {
            System.out.println("登陆成功:" + loginRequestPacket.getUsername());
            loginResponsePacket.setSuccess(true);
            SessionUtils.markLogin(ctx.channel());
        } else {
            System.out.println("登陆校验失败:" + loginRequestPacket.getUsername());
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("pwd error");
        }
        return loginResponsePacket;
    }

    private boolean valida(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
