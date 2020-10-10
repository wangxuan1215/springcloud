package com.wangx.user002.common;

/**
 * @author xrzj
 * @description 异常
 * @date 2018/3/22
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -7803697661254409759L;

    public BizException(String message) {
        super(message);
    }
}
