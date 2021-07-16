package com.example.demo.netty.start;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lh0
 * @date 2021/6/19
 * @desc
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));

        ctx.writeAndFlush(getByteBuf(ctx));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("server response".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }
}
