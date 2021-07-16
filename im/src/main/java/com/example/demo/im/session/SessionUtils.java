package com.example.demo.im.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class SessionUtils {

    private static final String LOGIN_ATTR = "LOGIN";
    private static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance(LOGIN_ATTR);



    public static void markLogin(Channel channel){
        channel.attr(LOGIN).set(true);
    }

    public static boolean isLogin(Channel channel){
        Boolean isLogin = channel.attr(LOGIN).get();
        return isLogin != null ? isLogin : false;

    }
}
