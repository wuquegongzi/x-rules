package com.haibao.xrules.service;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.model.QueryParam;

/**
 *
 *
 * @author ml.c
 * @date 10:26 PM 5/9/21
 **/
public interface RuleEngineService {

    boolean removeRule(String rule);

    boolean addRule(String rule);

    void execute(BaseEvent event);
}
