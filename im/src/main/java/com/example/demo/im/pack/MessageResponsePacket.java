package com.example.demo.im.pack;

import com.example.demo.im.Command;
import lombok.Data;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
@Data
public class MessageResponsePacket extends BasePacket{

    private String message;

    private String senderId;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
