package com.coder.txpdc.tc.txpdctc.core.listener;

import com.coder.txpdc.bean.MsgTxGroup;

/**
 * 消息监听处理
 * @author: lht
 * @date: 2020-01-19 10:51
 */
public interface MsgListener {
    /**
     *
     * @author: lht
     * @date: 2020/1/19 10:54
     */
    void working(MsgTxGroup msgTxGroup);
}
