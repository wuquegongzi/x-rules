package com.haibao.xrules.common.base;

import com.haibao.xrules.common.enums.ResultStatusEnum;
import java.io.Serializable;
import reactor.core.publisher.Mono;

/**
 * 返回体
 *
 * @author ml.c
 * @date 6:26 PM 5/16/21
 **/
public class Result<T> implements Serializable {

    /** 业务错误码 */
    private Integer code;
    /** 信息描述 */
    private String message;
    /** 返回参数 */
    private T data;


    public static <T> Mono<Result<T>> ok (Mono<T> monoBody) {
        return responseBodyCreate(monoBody,0,null);
    }

    public static <T> Mono<Result<T>> ok (Mono<T> monoBody, String msg) {
        return responseBodyCreate(monoBody,0,msg);
    }

    public static <T> Mono<Result<T>> ok (Mono<T> monoBody, int code, String msg) {
        return responseBodyCreate(monoBody,code,msg);
    }

    public static <T> Mono<Result<T>> fail (Mono<T> monoBody) {
        return responseBodyCreate(monoBody,0,null);
    }

    public static <T> Mono<Result<T>> fail (Mono<T> monoBody, String msg) {
        return responseBodyCreate(monoBody,0,msg);
    }

    public static <T> Mono<Result<T>> fail (Mono<T> monoBody, int code, String msg) {
        return responseBodyCreate(monoBody,code,msg);
    }

    private static <T> Mono<Result<T>> responseBodyCreate(Mono<T> monoData, int code, String msg) {
        return monoData.map(data-> {
            final Result<T> result = new Result<>();
            result.setCode(code);
            result.setData(data);
            result.setMessage(msg);
            return result;
        });
    }

    public Result() {
    }

    private Result(ResultStatusEnum resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
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
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
