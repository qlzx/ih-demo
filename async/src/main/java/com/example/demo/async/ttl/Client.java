package com.example.demo.async.ttl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

/**
 * @author lh0
 * @date 2021/8/6
 * @desc
 */
public class Client {

    private static ThreadLocal<Integer> context = new ThreadLocal<>();

    private static InheritableThreadLocal<Integer> inheritableContext = new InheritableThreadLocal<>();

    TransmittableThreadLocal<Integer> ttl = new TransmittableThreadLocal<>();




    private static ExecutorService executorService = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(1), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    });

    /**
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        //TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();

        Client client = new Client();
        for (int i = 0; i < 10; i++) {
            client.correctPollWithTTLRunnable(i);
        }

        executorService.shutdown();

    }

    public void sync(int i){
        context.set(i);
        try{
            // doSomething
            Thread.sleep(1000);
            System.out.println(context.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            context.remove();
        }
    }

    public void errorAsync(int i){
        context.set(i);
        try{
            // doSomething
            new Thread(() -> {
                System.out.println("async thread:" + context.get());
            }).start();
            Thread.sleep(100);
            System.out.println("async" + context.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            context.remove();
        }
    }

    public void correctAsync(int i){
        inheritableContext.set(i);
        try{
            new Thread(() -> {
                try {
                    System.out.println(
                        "async current:" + inheritableContext.get() + ",expect:" + i + ",thread:" + Thread
                            .currentThread());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    inheritableContext.remove();
                }
            }).start();
            // doSomething
            Thread.sleep(100);
            System.out.println("async" + inheritableContext.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            inheritableContext.remove();
        }
    }

    public void correctAsyncWithTTL(int i){
        ttl.set(i);
        try{
            new Thread(() -> {
                try {
                    System.out.println(
                        "async current:" + ttl.get() + ",expect:" + i + ",thread:" + Thread
                            .currentThread());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ttl.remove();
                }
            }).start();
            // doSomething
            Thread.sleep(100);
            System.out.println("async" + ttl.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ttl.remove();
        }
    }

    public void incorrectPoll(int i){
        inheritableContext.set(i);
        try{

            executorService.submit(()->{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("async current:" + inheritableContext.get() +",expect:"+i+",thread:"+Thread.currentThread());
            });
            // doSomething
            Thread.sleep(100);
            System.out.println("async" + inheritableContext.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            inheritableContext.remove();
        }
    }


    public void correctPollWithTTLRunnable(int i){
        ttl.set(i);
        try{

            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(
                            "async current:" + ttl.get() + ",expect:" + i + ",thread:" + Thread
                                .currentThread());
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        ttl.remove();
                    }
                }
            };
            Runnable ttlRunnable = TtlRunnable.get(task);

            executorService.submit(ttlRunnable);

            // doSomething
            Thread.sleep(100);
            System.out.println("async" + ttl.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ttl.remove();
        }
    }


}
