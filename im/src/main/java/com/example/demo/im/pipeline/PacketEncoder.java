package com.example.demo.im.pipeline;

import com.example.demo.im.codec.PacketCodeC;
import com.example.demo.im.pack.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BasePacket basePacket,
        ByteBuf out) throws Exception {
        PacketCodeC.getInstance().encode(out, basePacket);
    }
}
