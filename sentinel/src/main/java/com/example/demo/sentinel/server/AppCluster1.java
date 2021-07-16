package com.example.demo.sentinel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lh0
 * @date 2021/7/14
 * @desc
 */
@SpringBootApplication
public class AppCluster1 {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread());
        SpringApplication.run(AppCluster1.class, args);
    }
}
