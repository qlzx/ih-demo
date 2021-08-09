package com.example.demo.io.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * @author lh0
 * @date 2021/8/7
 * @desc
 */
public abstract class SelectorThread extends Thread{

    protected final Selector selector;


    public SelectorThread(String name) throws IOException {
        setName(name);
        setDaemon(true);
        this.selector = Selector.open();
    }
}
