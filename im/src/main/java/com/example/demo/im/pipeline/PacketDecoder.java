package com.example.demo.im.pipeline;

import java.util.List;

import com.example.demo.im.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 入站读数据
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
        List<Object> list) throws Exception {
        list.add(PacketCodeC.getInstance().decode(byteBuf));
    }
}
