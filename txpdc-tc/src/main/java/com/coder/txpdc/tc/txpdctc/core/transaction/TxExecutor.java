package com.coder.txpdc.tc.txpdctc.core.transaction;

import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.context.BusinessMethod;
import com.coder.txpdc.tc.txpdctc.core.timer.TxGroupTimer;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 14:09
 */
@Service
public class TxExecutor {
    private TxGroupCaching txGroupCaching;
    private TxGroupTimer txGroupTimer;

    public TxExecutor(TxGroupCaching txGroupCaching, TxGroupTimer txGroupTimer) {
        this.txGroupCaching = txGroupCaching;
        this.txGroupTimer = txGroupTimer;
    }

    /**
     *
     * @author: lht
     * @param txGroupInfo : 
     * @param businessMethod : 
     * @date: 2020/1/18 15:29
     */
    public Object call(TxGroupInfo txGroupInfo, BusinessMethod businessMethod)throws Throwable{
        try{
            return businessMethod.call();
        }finally {
            txGroupTimer.addWorker(txGroupInfo.getGroupId());
        }
    }

}
