package com.haibao.xrules.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.model.SysConfig;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * cache
 *
 * @author ml.c
 * @date 10:03 PM 5/15/21
 **/
@Configuration
public class CacheConfig {

    @Bean(value = "blackListCache")
    public Cache<String, BlackList> blackListCache() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(10_000)
                .build();

    }

    @Bean(value = "sysConfigCache")
    public Cache<String, SysConfig> sysConfigCache() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(100)
                .build();

    }

}
