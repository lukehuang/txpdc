package com.coder.txpdc.tc.txpdctc.core.listener.impl;

import com.coder.txpdc.bean.MsgTxGroup;
import com.coder.txpdc.exception.TxEndException;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.listener.MsgListener;
import com.coder.txpdc.tc.txpdctc.core.timer.TxGroupTimer;
import com.coder.txpdc.tc.txpdctc.core.transaction.LocalTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author: lht
 * @date: 2020-01-19 10:51
 */
@Service("txGroupCloseListener")
@Slf4j
public class TxGroupCloseListener implements MsgListener {
    @Autowired
    private LocalTransactionManager transactionManager;
    @Autowired
    private TxGroupTimer txGroupTimer;

    @Override
    public void working(MsgTxGroup msgTxGroup) {
        try {
            transactionManager.submitTxGroup(msgTxGroup.getGroupId(),msgTxGroup.getState());
            txGroupTimer.cancelTxGroupTimer(msgTxGroup.getGroupId());
        }catch (TxEndException e){
            log.info(e.getMessage());
        }catch (Exception e) {
            //发送失败记录 1
            //本地记录错误日志 2
            log.error("error",e);
        }
    }
}
