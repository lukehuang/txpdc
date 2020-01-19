package com.coder.txpdc.tc.txpdctc.core.context;

import com.coder.txpdc.bean.TxGroupInfo;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 11:06
 */
public  class TxGlobalContext {
    private static ThreadLocal<TxGroupInfo> txContextInfoThreadLocal = new InheritableThreadLocal<>();

    public static TxGroupInfo setTxGroupInfo(TxGroupInfo txGroupInfo){
        txContextInfoThreadLocal.set(txGroupInfo);
        return txGroupInfo;
    }

    public static TxGroupInfo getTxGroupInfo(){
        return txContextInfoThreadLocal.get();
    }

    public static void removeTxGroupInfo(){
        txContextInfoThreadLocal.remove();
    }

}
