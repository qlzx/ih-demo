package com.example.demo.cache.service;

import java.util.function.Supplier;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/**
 * @author lh0
 * @date 2021/9/16
 * @desc
 */
public class DynamicCaffeineCacheManager extends CaffeineCacheManager {
    private final Supplier<String> nationSupplier;
    public DynamicCaffeineCacheManager(
        Supplier<String> nationSupplier) {
        this.nationSupplier = nationSupplier;
    }

    @Override
    public Cache getCache(String name) {
        String nation = nationSupplier.get() ;
        if (nation != null) {
            return super.getCache(nation + ":" + name);
        }
        return super.getCache(name);
    }
}
