package com.example.demo.im.pack;

import com.example.demo.im.Command;
import lombok.Data;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
@Data
public class LoginResponsePacket extends BasePacket {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
