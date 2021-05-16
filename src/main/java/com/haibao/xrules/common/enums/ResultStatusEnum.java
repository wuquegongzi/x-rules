package com.haibao.xrules.common.enums;

/**
 * 状态码枚举
 *
 * 一共 10 位，分成四段
 * 第一段，1 位，类型
 *     1 - 系统级别异常
 *     2 - 业务级别异常
 * 第二段，3 位，系统类型
 *     001 - 规则引擎
 * 第三段，3 位，模块
 *     不限制规则。一般建议，每个系统里面，可能有多个模块，可以再去做分段。
 *     001 -黑名单
 * 第四段，3 位，错误码
 *     不限制规则。
 *     一般建议，每个模块自增。
 */
public enum ResultStatusEnum {

    SUCCESS(0, "OK"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    MISSING_REQUEST_PARAM_ERROR(200100101, "参数缺失"),;

    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ResultStatusEnum( Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
