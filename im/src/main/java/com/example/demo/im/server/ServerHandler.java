package com.example.demo.im.server;

import java.util.Date;

import com.example.demo.im.codec.PacketCodeC;
import com.example.demo.im.pack.BasePacket;
import com.example.demo.im.pack.LoginRequestPacket;
import com.example.demo.im.pack.LoginResponsePacket;
import com.example.demo.im.pack.MessageRequestPacket;
import com.example.demo.im.pack.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;

        BasePacket basePacket = PacketCodeC.getInstance()
            .decode(buffer);

        if (basePacket instanceof LoginRequestPacket) {
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) basePacket;
            if (valida(loginRequestPacket)) {
                System.out.println("登陆成功:" + loginRequestPacket.getUsername());
                loginResponsePacket.setSuccess(true);
            } else {
                System.out.println("登陆校验失败:" + loginRequestPacket.getUsername());
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("pwd error");
            }
            // 响应客户端的登陆请求
            response(ctx, loginResponsePacket);
        } else if (basePacket instanceof MessageRequestPacket) {
            // 处理消息
            MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) basePacket);
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodeC.getInstance().encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }



    }

    private void response(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        ByteBuf response = PacketCodeC.getInstance().encode(ctx.alloc(),loginResponsePacket);
        ctx.channel().writeAndFlush(response);
    }

    private boolean valida(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
