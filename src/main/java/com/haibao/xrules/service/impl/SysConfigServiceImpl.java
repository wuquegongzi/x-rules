package com.haibao.xrules.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.haibao.xrules.dao.RedisDao;
import com.haibao.xrules.mapper.SysConfigRepository;
import com.haibao.xrules.model.SysConfig;
import com.haibao.xrules.service.SysConfigService;
import com.haibao.xrules.utils.GsonUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author ml.c
 * @date 8:57 PM 5/16/21
 **/
@Service
public class SysConfigServiceImpl implements SysConfigService {

    private static Logger logger = LoggerFactory.getLogger(SysConfigService.class);

    private String channel = this.getClass().getName();

    @Autowired
    private SysConfigRepository sysConfigRepository;

    @Qualifier("sysConfigCache")
    @Autowired
    Cache<String, SysConfig> sysConfigCache;

    @Autowired
    RedisDao redisDao;

    @PostConstruct
    public void init() {
        updateCache();
    }

    @Override
    public List<SysConfig> queryAll() {
        return sysConfigCache.asMap().values().stream().collect(Collectors.toList());
    }

    private SysConfig update(SysConfig sysConfig) {
        return sysConfigRepository.saveAndFlush(sysConfig);
    }

    @Override
    public boolean pub(SysConfig sysConfig) {
        update(sysConfig);
        redisDao.publish(this.channel, GsonUtils.gsonString(sysConfig));
        return true;
    }

    @Override
    public SysConfig get(String key) {
        return sysConfigCache.getIfPresent(key);
    }

    @Override
    public String getTopic() {
        return this.channel;
    }

    @Override
    public void updateCache() {
        List<SysConfig> configs = sysConfigRepository.findAll();
        Map<String, SysConfig> tempMap = new ConcurrentHashMap<>();
        for (SysConfig config : configs) {
            tempMap.put(config.getKey(), config);
        }
        sysConfigCache.putAll(tempMap);
        logger.info("update sys_config cache success");
    }
}
