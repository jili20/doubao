package com.douyuehan.doubao.common.exception;

import com.douyuehan.doubao.common.api.IErrorCode;

/**
 * @author bing  @create 2021/3/1-3:03 下午
 */
public class ApiAsserts {
    /**
     * 抛失败异常
     *
     * @param message 说明
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 抛失败异常
     *
     * @param errorCode 状态码
     */
    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
