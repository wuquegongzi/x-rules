package com.haibao.xrules.dao;

import com.haibao.xrules.common.base.BaseEvent;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Mongo 接口封装
 *
 * @author ml.c
 * @date 8:14 PM 5/23/21
 **/
public interface MongoDao<T> {

    void save(String collectionName,T event);

    void remove(String collectionName,String id);

    void update(String collectionName, String id, T event, Class<T> entityClass);

    T findEventById(String collectionName, String id, Class<T> entityClass);

}
