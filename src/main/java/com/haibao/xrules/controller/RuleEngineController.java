package com.haibao.xrules.controller;

import cn.hutool.core.util.StrUtil;
import com.haibao.xrules.common.base.Result;
import com.haibao.xrules.common.enums.ResultStatusEnum;
import com.haibao.xrules.common.exception.ServiceException;
import com.haibao.xrules.service.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *  规则引擎管理接口
 *
 * @author ml.c
 * @date 1:29 PM 5/23/21
 **/
@RestController
@RequestMapping("/kie")
public class RuleEngineController {

    private static Logger logger = LoggerFactory.getLogger(RuleEngineController.class);

    @Autowired
    private RuleEngineService ruleEngineService;

    /**
     * 添加规则
     * @param rule
     * @return
     */
    @PutMapping("/addRule")
    public Mono addRule(String rule) {

        if (StrUtil.isEmpty(rule)) {
            throw new ServiceException(ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR);
        }
        return Result.ok(Mono.just(ruleEngineService.addRule(rule)));
    }

    /**
     * 删除规则
     * @param rule
     * @return
     */
    @DeleteMapping("/removeRule")
    public Mono removeRule(String rule) {

        if (StrUtil.isEmpty(rule)) {
            throw new ServiceException(ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR);
        }
        return Result.ok(Mono.just(ruleEngineService.removeRule(rule)));
    }

}
