package com.coder.txpdc.tc.txpdctc.core.clear.impl;

import com.coder.txpdc.tc.txpdctc.core.caching.ConnectionProxyCache;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.clear.DungBeetle;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-14 15:34
 */
@Service("pdc_dung_beetle")
public class PdcDungBeetle implements DungBeetle {

    private TxGroupCaching txGroupCaching;

    public PdcDungBeetle(TxGroupCaching txGroupCaching) {
        this.txGroupCaching = txGroupCaching;
    }

    @Override
    public void clearTxGroup(String groupId) {
        txGroupCaching.clear(groupId);
        ConnectionProxyCache.clear(groupId);
    }

    @Override
    public void clearThreadTxGroup() {
        TxGlobalContext.removeTxGroupInfo();
    }
}
