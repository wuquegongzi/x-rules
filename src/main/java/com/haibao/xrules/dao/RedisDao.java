package com.haibao.xrules.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * redis 接口封装
 *
 * @author ml.c
 * @date 11:12 AM 5/16/21
 **/
@Repository
public class RedisDao {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     * 发布消息到指定的频道
     *
     * @param channel
     * @param message
     */
    public void publish(final String channel, final String message) {
        redisTemplate.convertAndSend(channel,message);
    }

}


