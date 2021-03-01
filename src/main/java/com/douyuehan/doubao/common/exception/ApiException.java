package com.douyuehan.doubao.common.exception;

import com.douyuehan.doubao.common.api.IErrorCode;

/**
 * @author bing  @create 2021/3/1-3:03 下午
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}

