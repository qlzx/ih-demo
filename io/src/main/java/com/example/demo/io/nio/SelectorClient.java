package com.example.demo.io.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * @author lh0
 * @date 2021/8/4
 * @desc
 */
public class SelectorClient {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        // selector.select会一直处于running状态 等待wakeup 和超时信号

        while(true){
            selector.select();



        }
    }
}
