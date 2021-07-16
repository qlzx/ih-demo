package com.example.demo.im.pack;

import com.example.demo.im.Command;
import lombok.Data;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
@Data
public class LoginRequestPacket extends BasePacket {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
