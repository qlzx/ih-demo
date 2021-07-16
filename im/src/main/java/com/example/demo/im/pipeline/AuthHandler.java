package com.example.demo.im.pipeline;

import com.example.demo.im.session.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtils.isLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
