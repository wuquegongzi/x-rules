package com.haibao.xrules.service.impl;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.model.QueryParam;
import com.haibao.xrules.model.RuleResult;
import com.haibao.xrules.service.BlackListService;
import com.haibao.xrules.service.DimensionService;
import com.haibao.xrules.service.RuleEngineService;
import com.haibao.xrules.utils.GsonUtils;
import javax.annotation.PostConstruct;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KieSession kieSession;

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private DimensionService dimensionService;

    /**
     * drools全局服务变量
     */
    @PostConstruct
    private void setGlobal() {
        kieSession.setGlobal("blackListService", blackListService);
        kieSession.setGlobal("dimensionService", dimensionService);
    }

    @Override
    public boolean removeRule(String rule) {

        //todo

        return false;
    }

    @Override
    public boolean addRule(String rule) {

        //todo

        return false;
    }

    @Override
    public void execute(BaseEvent event) {

        LOGGER.info("开始执行：{}", GsonUtils.gsonString(event));

        kieSession.insert(event) ;
        kieSession.fireAllRules() ;
    }
}