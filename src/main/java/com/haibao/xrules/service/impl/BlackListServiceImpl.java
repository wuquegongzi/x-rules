package com.haibao.xrules.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.haibao.xrules.mapper.BlackListRepository;
import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
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
public class BlackListServiceImpl implements BlackListService {

    private static Logger logger = LoggerFactory.getLogger(BlackListService.class);

    @Autowired
    private BlackListRepository blackListRepository;

    @Qualifier("blackListCache")
    @Autowired
    Cache<String,BlackList> blackListCache;


    @PostConstruct
    public void init() {
        updateCache();
    }

    @Override
    public List<BlackList> queryAll() {
        return blackListCache.asMap().values().stream().collect(Collectors.toList());
    }

    @Override
    public void add(BlackList blackList) {
        blackListRepository.save(blackList);
    }

    @Override
    public BlackList get(String key) {
        return blackListCache.getIfPresent(key);
    }

    @Override
    public void pub(BlackList blackList) {

    }

    private void updateCache() {
        List<BlackList> blackLists = blackListRepository.findAll();
        Map<String, BlackList> tempMap = new ConcurrentHashMap<>();
        for (BlackList blackList : blackLists) {
            tempMap.put(blackList.getValue(), blackList);
//            blackListCache.put(blackList.getValue(),blackList);
        }
        blackListCache.putAll(tempMap);
        logger.info("update balck_list cache success");
    }
}
