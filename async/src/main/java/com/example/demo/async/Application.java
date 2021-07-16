package com.example.demo.async;

import com.example.demo.async.service.AsyncHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author lh0
 * @date 2021/7/15
 * @desc
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        AsyncHolder asyncHolder = applicationContext.getBean(AsyncHolder.class);

        asyncHolder.hello();

        asyncHolder.other();

        asyncHolder.push();
    }
}
