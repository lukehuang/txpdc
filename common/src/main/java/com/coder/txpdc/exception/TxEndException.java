package com.coder.txpdc.exception;

import com.coder.txpdc.constant.ErrorInfo;

/**
 * @Description: 事务结束异常
 * @Author: lht
 * @Date: 2020-01-15 15:24
 */
public class TxEndException extends TxGlobalException{
    public TxEndException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
