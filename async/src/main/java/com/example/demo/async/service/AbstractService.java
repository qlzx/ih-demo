package com.example.demo.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author lh0
 * @date 2021/7/15
 * @desc
 */
@Service
@Slf4j
public abstract class AbstractService {

    @Async
    public void push() throws InterruptedException {
        log.info("push[] current thread:{}", Thread.currentThread().getName());
        Thread.sleep(1000);

    }
}
