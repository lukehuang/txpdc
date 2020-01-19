package com.coder.txpdc.exception;

import com.coder.txpdc.constant.ErrorInfo;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-15 14:29
 */
public class TxGlobalException extends RuntimeException {
    private final ErrorInfo errorInfo;

    public TxGlobalException(ErrorInfo errorInfo) {
        super(errorInfo.name());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
