package com.haibao.xrules.service;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.common.enums.TimePeriodEnums;
import com.haibao.xrules.model.event.LoginEvent;

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

    int distinctCount(BaseEvent event, String[] condDimensions, TimePeriodEnums lasthour, String mobile);

    int count(BaseEvent event, String[] condDimensions, TimePeriodEnums lastmin);
}
