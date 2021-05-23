package com.haibao.xrules.service.impl;

import com.haibao.xrules.dao.MongoDao;
import com.haibao.xrules.service.DimensionService;
import com.haibao.xrules.utils.GsonUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author ml.c
 * @date 8:12 PM 5/23/21
 **/
@Service
public class DimensionServiceImpl<T> implements DimensionService<T> {

    @Autowired
    private MongoDao mongoDao;

    @Override
    public void insertEvent(String collectionName,T event) {
        mongoDao.save(collectionName,event);
    }
}
