package com.example.demo.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author lh0
 * @date 2021/7/15
 * @desc
 */
@Component
@Slf4j
public class AsyncHolder extends AbstractService{

    @Async("taskExecutor")
    public String hello(){
        log.info("hello[] current thread :{}", Thread.currentThread().getName());
        return "hello";
    }


    @Async("otherExecutor")
    public String other(){
        log.info("other[] current thread :{}", Thread.currentThread().getName());
        return "hello";
    }
}
