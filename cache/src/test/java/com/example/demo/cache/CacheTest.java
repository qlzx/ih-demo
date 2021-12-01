package com.example.demo.cache;

import java.util.List;

import com.example.demo.cache.service.ConfigService;
import com.example.demo.cache.service.SalaryConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lh0
 * @date 2021/9/16
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CacheTest {

    @Autowired
    private ConfigService configService;

    @Test
    public void test() {
        //List<Object> result = configService.getDayConfig("A");
        //log.info("第一次查询DayConfig:A,没有缓存 :{}", result);
        //result = configService.getDayConfig("A");
        //log.info("第二次查询DayConfig:A,有缓存 :{}", result);
        //
        //Object customerConfig = configService.getCustomerConfig(String.valueOf(1L));
        //log.info("第一次查询CustomerConfig:1,没有缓存 :{}",customerConfig);
        //customerConfig = configService.getCustomerConfig(String.valueOf(1L));
        //log.info("第一次查询CustomerConfig:1,1有缓存:{}", customerConfig);
        //
        //log.info("新增DayConfig配置:B");
        //SalaryConfig salaryConfig = new SalaryConfig();
        //salaryConfig.setBizClass("WORK_DAY");
        //salaryConfig.setBizType("A");
        //configService.save(salaryConfig);
        //
        //log.info("缓存清空后查询DayConfig:A,没有缓存");
        //result = configService.getDayConfig("A");
        //
        //
        //log.info("新增CustomerConfig配置:1");
        //SalaryConfig customerConf = new SalaryConfig();
        //customerConf.setBizClass("CUSTOMER_FACTOR");
        //customerConf.setBizKey("1");
        //configService.save(customerConf);
        //
        //log.info("Customer:1的缓存会清空");
        //configService.getCustomerConfig("1");

        configService.getCustomer("1");

        configService.getCustomer("1");


        configService.getCustomerConfig("1");
        configService.getCustomerConfig("1");


        //System.out.println(configService.getConfig("C","1"));
        //System.out.println(configService.getConfig("C","1"));
        //System.out.println(configService.getConfig("A","1"));
        //System.out.println(configService.getConfig("A","1"));
        //
        //configService.save("A", "1", "0");
        //System.out.println(configService.getConfig("A","1"));
        //System.out.println(configService.getConfig("A"));

    }
}
