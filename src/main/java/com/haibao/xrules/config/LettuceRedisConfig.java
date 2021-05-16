package com.haibao.xrules.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.haibao.xrules.service.subscriber.Subscriber;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 *
 * @author ml.c
 * @date 12:10 AM 5/9/21
 **/
@Configuration
@Slf4j
public class LettuceRedisConfig extends CachingConfigurerSupport {

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }


    /**
     * 缓存管理器
     * @return
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory);
        @SuppressWarnings("serial")
        Set<String> cacheNames = new HashSet<String>() {
            {
                add("codeNameCache");
            }
        };
        builder.initialCacheNames(cacheNames);
        return builder.build();
    }


    /**
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * DependencyDescriptor
     * 重点
     * 首先判断注入的类型，如果是数组、Collection、Map，则注入的是元素数据，即查找与元素类型相同的Bean，注入到集合中。
     * 强调下Map类型，Map的 key 为Bean的 name，value 为 与定义的元素类型相同的Bean。
     *将所有相同类型（实现了同一个接口）的Bean，一次性注入到集合类型中，具体实现查看spring源码
     *
     * 获取Subscriptor接口所有的实现类
     * 注入所有实现了接口的Bean
     * 将所有的配置消息接收处理类注入进来，那么消息接收处理类里面的注解对象也会注入进来
     */
    @Autowired
    private transient List<Subscriber> subscriptorList;


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        //创建一个消息监听对象
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        //将监听对象放入到容器中
        container.setConnectionFactory(connectionFactory);

        if (this.subscriptorList != null && this.subscriptorList.size() > 0) {
            for (Subscriber subscriber : this.subscriptorList) {

                log.info("loading {} , addMessageListener",subscriber.getTopic());
                if (null == subscriber || StrUtil.isBlank(subscriber.getTopic())) {
                    continue;
                }
                //一个订阅者对应一个主题通道信息
                container.addMessageListener(subscriber, new PatternTopic(subscriber.getTopic()));
            }
        }

        return container;
    }


}
