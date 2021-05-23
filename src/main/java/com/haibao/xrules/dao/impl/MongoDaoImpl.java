package com.haibao.xrules.dao.impl;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.dao.MongoDao;
import com.haibao.xrules.utils.GsonUtils;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author ml.c
 * @date 8:31 PM 5/23/21
 **/
@Component
public class MongoDaoImpl<T> implements MongoDao<T> {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(String collectionName, T event) {
        mongoTemplate.save(event, collectionName);
    }

    @Override
    public void remove(String collectionName, String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, collectionName);
    }

    @Override
    public void update(String collectionName, String id, T event, Class<T> entityClass) {

        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();
        Map map = GsonUtils.gsonToMaps(GsonUtils.gsonString(event));
        map.forEach((key, value) -> {
            update.set(String.valueOf(key), value);
        });

        mongoTemplate.updateFirst(query, update, entityClass, collectionName);
    }

    @Override
    public T findEventById(String collectionName, String id, Class<T> entityClass) {
        Query query = new Query(Criteria.where("id").is(id));
        T event = mongoTemplate.findOne(query, entityClass, collectionName);
        return event;
    }
}
