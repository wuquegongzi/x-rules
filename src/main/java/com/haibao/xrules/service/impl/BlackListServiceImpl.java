package com.haibao.xrules.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.haibao.xrules.dao.RedisDao;
import com.haibao.xrules.mapper.BlackListRepository;
import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
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
 * @date 9:56 PM 5/15/21
 **/
@Service
public class BlackListServiceImpl implements BlackListService{

    private static Logger logger = LoggerFactory.getLogger(BlackListService.class);

    private String channel = this.getClass().getName();

    @Autowired
    private BlackListRepository blackListRepository;

    @Qualifier("blackListCache")
    @Autowired
    Cache<String,BlackList> blackListCache;

    @Autowired
    RedisDao redisDao;

    @PostConstruct
    public void init() {
        updateCache();
    }

    @Override
    public List<BlackList> queryAll() {
        return blackListCache.asMap().values().stream().collect(Collectors.toList());
    }

    private BlackList add(BlackList blackList) {

        boolean exist = blackListRepository.existsByDimensionAndTypeAndValue(blackList.getDimension(),blackList.getType(),blackList.getValue());
        if(exist){
          logger.info("blackList exists：{},dont need add {}",exist,blackList);
          return blackListRepository.findByDimensionAndTypeAndValue(blackList.getDimension(),blackList.getType(),blackList.getValue());
        }
        return blackListRepository.save(blackList);
    }

    @Override
    public BlackList get(String key) {
        return blackListCache.getIfPresent(key);
    }

    @Override
    public boolean pub(BlackList blackList) {
        BlackList newBlackList = null;
        try {
            newBlackList = add(blackList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null != newBlackList && newBlackList.getId() > 0){
            redisDao.publish(this.channel, GsonUtils.gsonString(blackList));
            return true;
        }

        return false;
    }

    @Override
    public String getTopic() {
        return this.channel;
    }

    @Override
    public void updateCache() {
        List<BlackList> blackLists = blackListRepository.findAll();
        Map<String, BlackList> tempMap = new ConcurrentHashMap<>();
        for (BlackList blackList : blackLists) {
            tempMap.put(blackList.getValue(), blackList);
        }
        blackListCache.putAll(tempMap);
        logger.info("update balck_list cache success");
    }
}
