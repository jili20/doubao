package com.douyuehan.doubao.common.api;

/**
 * @author bing  @create 2021/3/1-2:50 下午
 */
public interface IErrorCode {
    /**
     * 错误编码: -1失败;200成功
     *
     * @return 错误编码
     */
    Integer getCode();

    /**
     * 错误描述
     *
     * @return 错误描述
     */
    String getMessage();
}

