package com.example.demo.cache.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author lh0
 * @date 2021/9/16
 * @desc
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "salary.config")

public class ConfigService {

    public static String getNation(){
        return "th";
    }

    @Cacheable(key = "'WORK_DAY:'+#bizType")
    public List<Object> getDayConfig(String bizType){
        if("A".equals(bizType)) {
            log.info("query Config A");
            return Arrays.asList("a","a","a");
        }

        if("B".equals(bizType)) {
            log.info("query Config B");
            return Arrays.asList("b","b","b");
        }
        return Collections.emptyList();
    }

    @Cacheable(cacheNames = "customer",key = "#bizKey")
    public Object getCustomer(String bizKey){
        return getCustomerConfig(bizKey);
    }



    @Cacheable(key = "'CUSTOMER_FACTOR:'+#bizKey")
    public Object getCustomerConfig(String bizKey) {
        log.info("getConfig CUSTOMER_FACTOR bizKey:{}", bizKey);
        return bizKey;
    }

    @Caching(evict = {
        @CacheEvict(key = "#salaryConfig.bizClass+':'+#salaryConfig.bizType"),
        @CacheEvict(key = "#salaryConfig.bizClass+':'+#salaryConfig.bizKey")
    })
    public void save(SalaryConfig salaryConfig){
        log.info("save salaryConfig:{}",salaryConfig);
    }
}
