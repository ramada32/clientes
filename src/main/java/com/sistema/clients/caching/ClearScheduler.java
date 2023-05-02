package com.sistema.clients.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@EnableScheduling
public class ClearScheduler {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Scheduled(cron = "0 0 0/8 * * ?")
    public void clearCacheSchedule(){

        Cache cache = redisCacheManager.getCache("credentialCache");

        if(Objects.nonNull(cache)){
            cache.clear();
        }
    }
}
