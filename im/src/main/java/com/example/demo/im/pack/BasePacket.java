package com.example.demo.im.pack;

import lombok.Data;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
@Data
public abstract class BasePacket {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 获取指令
     * @return 指定
     */
    public abstract Byte getCommand();
}
