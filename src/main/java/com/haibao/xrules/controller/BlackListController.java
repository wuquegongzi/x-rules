package com.haibao.xrules.controller;

import com.haibao.xrules.common.base.Result;
import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
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
 * 名单
 *
 * @author ml.c
 * @date 2:12 PM 5/16/21
 **/
@RestController
@RequestMapping("/blacklist")
public class BlackListController {

    private static Logger logger = LoggerFactory.getLogger(BlackListController.class);

    @Autowired
    private BlackListService blackListService;


    /**
     * 名单列表
     * @return
     */
    @GetMapping(path = "/queryall")
    public Mono queryAll() {
        final Mono<List<BlackList>> data = Mono.just(blackListService.queryAll());
        return Result.ok(data);
    }

    /**
     * 名单添加
     * @param dimension
     * @param type
     * @param value
     * @param detail
     * @return
     */
    @PostMapping(value = "/add")
    public Mono add(String dimension, String type, String value, String detail) {

            BlackList.EnumDimension enumDimension = BlackList.EnumDimension.valueOf(dimension.toUpperCase());
            if (enumDimension == null) {
                throw new IllegalArgumentException();
            }
            BlackList.EnumType enumType = BlackList.EnumType.valueOf(type.toUpperCase());
            if (enumType == null) {
                throw new IllegalArgumentException();
            }

            BlackList blackList = new BlackList();
            blackList.setDimension(dimension);
            blackList.setType(type);
            blackList.setValue(value);
            blackList.setDetail(detail);

            return Result.ok(Mono.just(blackListService.pub(blackList)));
    }
}
