package com.coder.txpdc.tc.txpdctc.core.caching.impl;

import com.coder.txpdc.constant.ErrorInfo;
import com.coder.txpdc.exception.TxEndException;
import com.coder.txpdc.exception.TxGlobalException;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.context.TxUnitInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 15:24
 */
public class MapTxGroupCaching implements TxGroupCaching {
    //缓存事务单元
    private ConcurrentHashMap<String, List<TxUnitInfo>> caching = new ConcurrentHashMap<>();
    //缓存事务组上下文
    private ConcurrentHashMap<String,TxContextInfo> groupCache = new ConcurrentHashMap<>();

    @Override
    public void setTxContext(TxContextInfo txContextInfo){
        groupCache.put(txContextInfo.getGroupId(),txContextInfo);
    }

    @Override
    public TxContextInfo getTxContext(String groupId) {
        Optional<TxContextInfo> txContextInfo = Optional.ofNullable(groupCache.get(groupId));
        if(txContextInfo.isPresent()){
            return txContextInfo.get();
        }
        throw new TxEndException(ErrorInfo.E_10003);
    }

    @Override
    public void clear(String groupId) {
        caching.remove(groupId);
        groupCache.remove(groupId);
    }

    @Override
    public Boolean isNotEnd(String groupId){
        return groupCache.containsKey(groupId);
    }



    @Override
    public void setTxUnit(TxUnitInfo txUnit){
        List<TxUnitInfo> txUnitInfos = caching.get(txUnit.getGroupId());
        if(txUnitInfos==null){
            txUnitInfos = new ArrayList<>();
        }
        txUnitInfos.add(txUnit);
    }

    @Override
    public List<TxUnitInfo> getTxUnit(String groupId){
        return caching.get(groupId);
    }

    @Override
    public TxUnitInfo getLastTxUnit(String groupId){
        List<TxUnitInfo> txUnitInfos = caching.get(groupId);
        if(txUnitInfos!=null){
            return txUnitInfos.get(txUnitInfos.size()-1);
        }
        return null;
    }

    @Override
    public Boolean isTxGroupTimer(String groupId) {
        Optional<TxContextInfo> txContextInfo = Optional.ofNullable(groupCache.get(groupId));
        if(txContextInfo.isPresent()){
            return txContextInfo.get().getIsTxGroupTimer();
        }
        throw new TxEndException(ErrorInfo.E_10003);
    }

    @Override
    public void setTxGroupTimer(String groupId){
        Optional<TxContextInfo> txContextInfo = Optional.of(groupCache.get(groupId));
        txContextInfo.get().setIsTxGroupTimer(true);
    }
}
