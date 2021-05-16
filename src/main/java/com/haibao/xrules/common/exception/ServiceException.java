package com.haibao.xrules.common.exception;

import com.haibao.xrules.common.enums.ResultStatusEnum;

/**
 * 服务异常
 *
 * @author ml.c
 * @date 6:38 PM 5/16/21
 **/
public final class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    public ServiceException(ResultStatusEnum resultStatusEnum) {
        // 使用父类的 message 字段
        super(resultStatusEnum.getMessage());
        // 设置错误码
        this.code = resultStatusEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
