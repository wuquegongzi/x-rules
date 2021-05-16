package com.haibao.xrules.common.exception;

import com.haibao.xrules.common.base.Result;
import com.haibao.xrules.common.enums.ResultStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

/**
 * Webflux
 * 全局统一返回的处理器
 *
 * @author ml.c
 * @date 6:40 PM 5/16/21
 **/
@ControllerAdvice(basePackages = "com.haibao.xrules.controller")
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 ServiceException 异常
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public Mono serviceExceptionHandler(ServiceException ex) {
        logger.debug("[serviceExceptionHandler]", ex);

        return Result.fail(Mono.just(""),ex.getCode(),ex.getMessage());
    }

    /**
     * 处理 ServerWebInputException 异常
     *
     * WebFlux 参数不正确
     */
    @ResponseBody
    @ExceptionHandler(value = ServerWebInputException.class)
    public Mono serverWebInputExceptionHandler(ServerWebInputException ex) {
        logger.debug("[ServerWebInputExceptionHandler]", ex);
        return Result.fail(Mono.just(""),ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),ResultStatusEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    /**
     * 处理其它 Exception 异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Mono exceptionHandler(Exception e) {
        // 记录异常日志
        logger.error("[exceptionHandler]", e);
        return Result.fail(Mono.just(""),ResultStatusEnum.INTERNAL_SERVER_ERROR.getCode(),ResultStatusEnum.INTERNAL_SERVER_ERROR.getMessage());
    }
}
