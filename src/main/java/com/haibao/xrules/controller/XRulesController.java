package com.haibao.xrules.controller;

import cn.hutool.core.util.StrUtil;
import com.haibao.xrules.common.enums.ResultStatusEnum;
import com.haibao.xrules.common.exception.ServiceException;
import com.haibao.xrules.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * 风控 规则引擎 统一入口
 * @author ml.c
 * @date 11:00 AM 5/6/21
 **/
@RestController
@RequestMapping("/xrules")
public class XRulesController {

    private static Logger logger = LoggerFactory.getLogger(XRulesController.class);

    @Autowired
    private SysConfigService configService;

    /**
     * 响应式编程的返回值必须是 Flux 或者 Mono ，两者之间可以相互转换
     * Mono：实现发布者，并返回 0 或 1 个元素，即单对象
     * Flux：实现发布者，并返回 N 个元素，即 List 列表对象
     * @return
     */
    @GetMapping("/")
    public Mono<String> getUser() {

        //just() 方法可以指定序列中包含的全部元素。
        return Mono.just("welcome!");
    }

    /**
     * 业务风控分析
     * @param json
     * @return
     */
    @GetMapping("/req")
    public Mono<String> req(String json) {
        if (StrUtil.isEmpty(json)) {
            throw new ServiceException(ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR);
        }


        return Mono.just("");
    }

}
