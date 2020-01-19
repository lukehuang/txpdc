package com.coder.txpdc.constant;

/**
 * @Description: 错误信息枚举
 * @Author: lht
 * @Date: 2020-01-15 14:31
 */
public enum ErrorInfo {
    /**
     * @Description:
     */
    E_10001(500,"获取连接失败"),
    E_10002(500,"全局事务已经结束"),
    E_10003(500,"连接已经关闭"),
    E_10004(500,"获取事务组失败"),

    ;
    private int state;
    private String msg;

    ErrorInfo(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
