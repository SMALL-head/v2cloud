package com.zyc.common.enums;

/**
 * @author zyc
 * @version 1.0
 */
public enum ExceptionEnum {

    HTTP_CALL_EXCEPTION(100001, "http请求异常"),
    RESULT_UNCORRECT(100002, "请求结果异常"),
    REQUEST_UNCORRECT(100003, "请求参数异常")
    ;


    final int code;
    final String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
