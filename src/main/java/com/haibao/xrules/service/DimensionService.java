package com.haibao.xrules.service;

public interface DimensionService<T> {

    /**
     * 事件入库
     * @param event
     */
    void insertEvent(String collectionName ,T event);

    /**
     * 可疑事件入库
     * @param event
     * @param rule
     */
    void insertRiskEvent(T event, String rule);
}
