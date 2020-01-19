package com.coder.txpdc.tc.txpdctc.core.transaction;

import com.coder.txpdc.tc.txpdctc.core.caching.ConnectionProxyCache;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.clear.DungBeetle;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.transaction.pdc.PdcConnectionProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: lht
 * @date: 2020-01-18 14:17
 */
@Component
@Slf4j
public class LocalTransactionManager {
    private TxGroupCaching txGroupCaching;
    private DungBeetle dungBeetle;

    public LocalTransactionManager(TxGroupCaching txGroupCaching, DungBeetle dungBeetle) {
        this.txGroupCaching = txGroupCaching;
        this.dungBeetle = dungBeetle;
    }

    /**
     * 提交事务
     * @author: lht
     * @date: 2020/1/18 14:19
     */
    public void submitTxGroup(String groupId,int state)throws Exception{
        TxContextInfo txContextInfo = txGroupCaching.getTxContext(groupId);
        Object lock = txContextInfo.getLock();
        synchronized ( lock ) {
            try{
                Optional<PdcConnectionProxy> pdcConnectionProxy = ConnectionProxyCache.getOptional(groupId);
                if(pdcConnectionProxy.isPresent()){
                    pdcConnectionProxy.get().submitTxGroup(state);
                }
            }catch (Exception e){
                log.error("error",e);
                throw e;
            }finally {
                dungBeetle.clearTxGroup(groupId);
                lock.notifyAll();
            }
        }
    }
}
