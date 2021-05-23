package com.haibao.xrules.controller;

import cn.hutool.core.util.StrUtil;
import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.common.base.Result;
import com.haibao.xrules.common.enums.ResultStatusEnum;
import com.haibao.xrules.common.exception.ServiceException;
import com.haibao.xrules.common.factory.EventFactory;
import com.haibao.xrules.model.SysConfig;
import com.haibao.xrules.service.RuleEngineService;
import com.haibao.xrules.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 风控 规则引擎 统一入口
 *
 * @author ml.c
 * @date 11:00 AM 5/6/21
 **/
@RestController
@RequestMapping("/xrules")
public class XRulesController {

    private static Logger logger = LoggerFactory.getLogger(XRulesController.class);

    @Autowired
    private SysConfigService configService;

    @Autowired
    private RuleEngineService ruleEngineService;

    @Autowired
    EventFactory eventFactory;


    /**
     * 业务风控分析
     *
     * @param eventJson
     * @return
     */
    @GetMapping("/req")
    public Mono req(@RequestParam("json") String eventJson) {

        logger.info("业务风控分析:{}",eventJson);

        if (StrUtil.isEmpty(eventJson)) {
            throw new ServiceException(ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR);
        }
        BaseEvent event = eventFactory.build(eventJson);

        SysConfig sysConfig = configService.get("X_RULES_SWITCH");
        if (null != sysConfig && "ON".equals(sysConfig.getValue())) {
            ruleEngineService.execute(event);
        }

        return Result.ok(Mono.just(event));
    }

}
