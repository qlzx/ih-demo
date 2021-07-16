package com.example.demo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;

/**
 * @author lh0
 * @date 2021/6/27
 * @desc
 */
public class ZkConnectTest {

    @Test
    public void test(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory
            .newClient("localhost:2181,localhost:2182,localhost:2183", new RetryOneTime(3000));

        curatorFramework.start();

        try {
            curatorFramework.getData().forPath("/test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
