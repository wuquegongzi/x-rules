package com.haibao.xrules.service;

import com.haibao.xrules.model.BlackList;
import java.util.List;
import org.springframework.data.redis.connection.MessageListener;

/**
 *
 *
 * @author ml.c
 * @date 9:53 PM 5/15/21
 **/
public interface BlackListService{

    /**
     * 获取所有黑名单
     * @return
     */
    List<BlackList> queryAll();

    /**
     * 添加并发布最新 黑名单
     * @param blackList
     * @return
     */
    boolean pub(BlackList blackList);

    /**
     * 根据黑名单的value获取单个详情
     * @param key
     * @return
     */
    BlackList get(String key);

    /**
     *
     * @return
     */
    String getTopic();

    /**
     * 全量更新缓存
     */
    void updateCache();
}
