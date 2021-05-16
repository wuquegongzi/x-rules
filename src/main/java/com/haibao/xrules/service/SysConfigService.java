package com.haibao.xrules.service;

import com.haibao.xrules.model.SysConfig;
import java.util.List;

/**
 * 系统配置 接口
 *
 * @author ml.c
 * @date 8:54 PM 5/16/21
 **/
public interface SysConfigService {

    /**
     * 获取所有系统配置
     * @return
     */
    List<SysConfig> queryAll();

    /**
     * 更新并发布最新 系统配置
     * @param sysConfig
     * @return
     */
    boolean pub(SysConfig sysConfig);

    /**
     * 根据key获取单个详情
     * @param key
     * @return
     */
    SysConfig get(String key);

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
