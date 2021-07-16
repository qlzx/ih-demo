package com.example.demo.im.pipeline;

import java.util.Date;

import com.example.demo.im.pack.MessageRequestPacket;
import com.example.demo.im.pack.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
        MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket messageResponsePacket = receiveMessage(messageRequestPacket);
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
        // 处理消息
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        return messageResponsePacket;
    }
}
