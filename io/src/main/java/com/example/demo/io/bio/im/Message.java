package com.example.demo.io.bio.im;

import java.io.Serializable;

import lombok.Data;

/**
 * @author lh0
 * @date 2021/6/19
 * @desc
 */
@Data
public class Message implements Serializable {
    private String content;

    private Long sender;

    private Long receiver;
}
