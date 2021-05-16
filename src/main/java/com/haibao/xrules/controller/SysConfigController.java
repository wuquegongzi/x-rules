package com.haibao.xrules.controller;

import cn.hutool.core.util.StrUtil;
import com.haibao.xrules.common.base.Result;
import com.haibao.xrules.common.enums.ResultStatusEnum;
import com.haibao.xrules.common.exception.ServiceException;
import com.haibao.xrules.model.SysConfig;
import com.haibao.xrules.service.SysConfigService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 系统配置
 *
 * @author ml.c
 * @date 9:31 PM 5/16/21
 **/
@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {

    private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private SysConfigService sysConfigService;


    @GetMapping(value = "/queryall")
    public Mono query() {
        final Mono<List<SysConfig>> data = Mono.just(sysConfigService.queryAll());
        return Result.ok(data);
    }

    @PostMapping(value = "/update")
    public Mono update(String key, String value) {

        if (StrUtil.isEmpty(key) || StrUtil.isEmpty(value)) {
            throw new ServiceException(ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR);
        }

        SysConfig config = new SysConfig();
        config.setKey(key);
        config.setValue(value);
        sysConfigService.pub(config);

        return Result.ok(Mono.just(sysConfigService.pub(config)));
    }

}
