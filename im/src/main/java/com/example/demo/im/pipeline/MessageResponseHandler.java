package com.example.demo.im.pipeline;

import java.util.Date;

import com.example.demo.im.pack.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
        MessageResponsePacket messageResponseHandler) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponseHandler.getMessage());
    }
}
