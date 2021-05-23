package com.haibao.xrules.service.impl;

import com.haibao.xrules.model.QueryParam;
import com.haibao.xrules.service.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author ml.c
 * @date 10:27 PM 5/9/21
 **/
@Service
public class RuleEngineServiceImpl implements RuleEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineServiceImpl.class);

    @Override
    public void executeAddRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());

    }

    @Override
    public void executeRemoveRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());

    }
}