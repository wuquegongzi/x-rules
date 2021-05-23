package com.haibao.xrules.service;

public interface DimensionService<T> {

    /**
     * 事件入库
     * @param event
     */
    void insertEvent(String collectionName ,T event);

}
