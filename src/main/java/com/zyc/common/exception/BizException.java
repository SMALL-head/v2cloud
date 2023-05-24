package com.zyc.common.exception;

import com.zyc.common.enums.ExceptionEnum;

/**
 * @author zyc
 * @version 1.0
 */
public class BizException extends RuntimeException {
    ExceptionEnum exceptionEnum;

    public BizException(String message, ExceptionEnum exceptionEnum) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }


}
