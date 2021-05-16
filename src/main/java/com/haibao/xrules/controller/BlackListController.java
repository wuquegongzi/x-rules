package com.haibao.xrules.controller;

import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity queryAll() {

        try {
            return ResponseEntity.ok(blackListService.queryAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
    public ResponseEntity add(String dimension, String type, String value, String detail) {

        try {
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

            return ResponseEntity.ok(blackListService.pub(blackList));
        } catch (Exception e) {
            logger.error("add balcklist fail", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
