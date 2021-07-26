package com.example.demo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author lh0
 * @date 2021/7/25
 * @desc
 */
// SpringBootConfiguration保证ConfigurationClassPostProcessor执行注册流程
@SpringBootConfiguration
// EnableAutoConfiguration保证注册spring.factories中自动装配的BeanDefinition
@EnableAutoConfiguration
// componentScan保证了对项目中的bean的扫描
@ComponentScan

// @SpringBootApplication = SpringBootConfiguration + EnableAutoConfiguration + ComponentScan
public class DebugWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(DebugWebApplication.class, args);
    }
}
