package com.haibao.xrules.service.impl;

import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.model.QueryParam;
import com.haibao.xrules.model.RuleResult;
import com.haibao.xrules.service.BlackListService;
import com.haibao.xrules.service.DimensionService;
import com.haibao.xrules.service.RuleEngineService;
import com.haibao.xrules.utils.GsonUtils;
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
    private void setGlobal() {
//        kieSession.setGlobal("blackListService", blackListService);
        kieSession.setGlobal("dimensionService", dimensionService);
    }

    @Override
    public void executeAddRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());

    }

    @Override
    public void executeRemoveRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());

    }

    @Override
    public boolean removeRule(String rule) {
        return false;
    }

    @Override
    public boolean addRule(String rule) {
        return false;
    }

    @Override
    public void execute(BaseEvent event) {

        setGlobal();

        LOGGER.info("开始执行：{}", GsonUtils.gsonString(event));

        QueryParam queryParam1 = new QueryParam() ;
        queryParam1.setParamId("1");
        queryParam1.setParamSign("+");
        QueryParam queryParam2 = new QueryParam() ;
        queryParam2.setParamId("2");
        queryParam2.setParamSign("-");
        // 入参
        kieSession.insert(queryParam1) ;
        kieSession.insert(queryParam2) ;
        kieSession.insert(this) ;
        // 返参
        RuleResult resultParam = new RuleResult() ;
        kieSession.insert(resultParam) ;

        kieSession.insert(event) ;

        kieSession.fireAllRules() ;

        System.out.println("resultParam:"+GsonUtils.gsonString(resultParam));
    }
}