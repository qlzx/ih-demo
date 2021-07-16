package com.example.demo.zk;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lh0
 * @date 2021/7/2
 * @desc
 */
public class DistributedLock{

    private String LOCK_NAME = "lock-";

    private CountDownLatch cdl = new CountDownLatch(1);

    private CuratorFramework client;

    public void lock(String lockKey) throws Exception {

        // prepare
        String lockNode = client.create().creatingParentContainersIfNeeded().withMode(
            CreateMode.EPHEMERAL_SEQUENTIAL)
            .forPath(ZKPaths.makePath(lockKey, LOCK_NAME), "1".getBytes(StandardCharsets.UTF_8));

        // 1. tryLock
        // 2. tryLock失败则阻塞等待 信号量
        while (!tryLock(lockKey,lockNode)) {


            try {
                cdl.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedException();
            }
        }


    }

    private boolean tryLock(String lockKey,String current) throws Exception {
        // getChild
        List<String> children = client.getChildren().forPath(lockKey);
        if (current.equals(getFirst(children))) {
            return true;
        }
        // 添加watcher
        return false;
    }

    private String getFirst(List<String> children) {
        return null;
    }

    public void unlock(){


    }

    public static void main(String[] args) {
        System.out.println(10 & 3);
    }

}
