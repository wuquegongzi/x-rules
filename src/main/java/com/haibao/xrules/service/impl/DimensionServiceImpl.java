package com.haibao.xrules.service.impl;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.common.enums.TimePeriodEnums;
import com.haibao.xrules.dao.MongoDao;
import com.haibao.xrules.dao.RedisDao;
import com.haibao.xrules.dao.impl.DocumentDecoder;
import com.haibao.xrules.service.DimensionService;
import com.haibao.xrules.utils.GsonUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 *
 * @author ml.c
 * @date 8:12 PM 5/23/21
 **/
@Service
public class DimensionServiceImpl<T> implements DimensionService<T> {

    private static Logger logger = LoggerFactory.getLogger(DimensionService.class);

    @Autowired
    private MongoDao mongoDao;
    @Autowired
    private RedisDao redisDao;

    private String riskEventCollection = "riskevent";

    private final static  String lua = "redis/x-rules.lua";

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

    @Override
    public int distinctCount(BaseEvent event, String[] condDimensions, TimePeriodEnums enumTimePeriod, String aggrDimension) {

        return distinctCountWithRedis(event, condDimensions, enumTimePeriod, aggrDimension);
    }

    @Override
    public int count(BaseEvent event, String[] condDimensions, TimePeriodEnums enumTimePeriod) {
        if (event == null || ArrayUtils.isEmpty(condDimensions) || enumTimePeriod == null) {
            logger.error("参数错误");
            return 0;
        }

        //todo
//        Query query = new Query(Criteria.where("age").lt(20));
//        return mongoDao.count(event.getScene(), query);
        return 0;
    }

    private int distinctCountWithRedis(BaseEvent event, String[] condDimensions, TimePeriodEnums enumTimePeriod, String aggrDimension) {

        return addQueryHabit(event, condDimensions, enumTimePeriod, aggrDimension);
    }

    /**
     * @param event          事件
     * @param condDimensions 条件维度数组,注意顺序
     * @param enumTimePeriod 查询时间段
     * @param aggrDimension  聚合维度
     * @return
     */
    public int addQueryHabit(BaseEvent event, String[] condDimensions, TimePeriodEnums enumTimePeriod, String aggrDimension) {
        if (event == null || ArrayUtils.isEmpty(condDimensions) || enumTimePeriod == null || aggrDimension == null) {
            logger.error("参数错误");
            return 0;
        }

        Date operate = event.getOperateTime();
        String key1 = String.join(".", String.join(".", condDimensions), aggrDimension);
        String[] key2 = new String[condDimensions.length];
        for (int i = 0; i < condDimensions.length; i++) {
            Object value = getProperty(event, condDimensions[i]);
            if (value == null || "".equals(value)) {
                return 0;
            }
            key2[i] = value.toString();
        }
        String key = event.getScene() + "_sset_" + key1 + "_" + String.join(".", key2);

        Object value = getProperty(event, aggrDimension);
        if (value == null || "".equals(value)) {
            return 0;
        }

        int expire = 0;
        String remMaxScore = "0";
        if (!enumTimePeriod.equals(TimePeriodEnums.ALL)) {
            //如果需要过期，则保留7天数据,满足时间段计算
            expire = 7 * 24 * 3600;
            remMaxScore = dateScore(new Date(operate.getTime() - expire * 1000L));
        }

        Long ret = runSha(key, remMaxScore, String.valueOf(expire), dateScore(operate), value.toString(), dateScore(enumTimePeriod.getMinTime(operate)), dateScore(enumTimePeriod.getMaxTime(operate)));
        return ret == null ? 0 : ret.intValue();
    }


    /**
     * lua 添加行为数据并获取结果
     * @param key
     * @param remMaxScore
     * @param expire
     * @param score
     * @param value
     * @param queryMinScore
     * @param queryMaxScore
     * @return
     */
    private Long runSha(String key, String remMaxScore, String expire, String score, String value, String queryMinScore, String queryMaxScore) {
        return redisDao.evalsha("", "1", new String[]{key, remMaxScore, expire, score, value, queryMinScore, queryMaxScore});
    }

    /**
     * 计算sortedset的score
     *
     * @param date
     * @return
     */
    private String dateScore(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    private Object getProperty(BaseEvent event, String field) {
        try {
            return PropertyUtils.getProperty(event, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
