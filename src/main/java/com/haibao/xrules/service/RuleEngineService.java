package com.haibao.xrules.service;

import com.haibao.xrules.model.QueryParam;

/**
 *
 *
 * @author ml.c
 * @date 10:26 PM 5/9/21
 **/
public interface RuleEngineService {

    void executeAddRule(QueryParam param);

    void executeRemoveRule(QueryParam param);
}
