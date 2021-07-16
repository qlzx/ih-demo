package com.example.demo.async.configure;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author lh0
 * @date 2021/7/15
 * @desc
 */
@EnableAsync
@Configuration
public class AsyncConfigure{

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(15);
        // 设置最大线程数
        executor.setMaxPoolSize(72);
        // 设置队列容量
        executor.setQueueCapacity(1000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(200);
        // 设置默认线程名称
        executor.setThreadNamePrefix("alpha");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;

    }

    @Bean
    public Executor otherExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(15);
        // 设置最大线程数
        executor.setMaxPoolSize(72);
        // 设置队列容量
        executor.setQueueCapacity(1000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(200);
        // 设置默认线程名称
        executor.setThreadNamePrefix("beta");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;

    }

}
