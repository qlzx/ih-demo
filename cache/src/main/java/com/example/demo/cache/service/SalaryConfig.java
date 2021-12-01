package com.example.demo.cache.service;

import lombok.Data;

/**
 * @author lh0
 * @date 2021/9/2
 * @desc
 */
@Data
public class SalaryConfig {
    /**
     * 配置id
     */
    private String docId;

    /**
     * 配置类
     */
    private String bizClass;

    /**
     * 配置业务类型
     */
    private String bizType;

    /**
     * 配置资源关联键
     */
    private String bizKey;

    /**
     * 配置值
     */
    private String config;
}
