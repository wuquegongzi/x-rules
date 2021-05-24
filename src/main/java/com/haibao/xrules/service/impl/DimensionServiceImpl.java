package com.haibao.xrules.service.impl;

import com.haibao.xrules.dao.MongoDao;
import com.haibao.xrules.dao.impl.DocumentDecoder;
import com.haibao.xrules.service.DimensionService;
import com.haibao.xrules.utils.GsonUtils;
import org.bson.Document;
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

    private String riskEventCollection = "riskevent";

    @Override
    public void insertEvent(String collectionName,T event) {
        mongoDao.save(collectionName,event);
    }

    @Override
    public void insertRiskEvent(T event, String rule) {
        Document document = Document.parse(GsonUtils.gsonString(event), new DocumentDecoder());
        document.append("rule", rule);
        mongoDao.insert(riskEventCollection,document);
    }
}
