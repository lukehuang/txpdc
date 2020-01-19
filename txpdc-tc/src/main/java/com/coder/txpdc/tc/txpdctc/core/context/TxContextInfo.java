package com.coder.txpdc.tc.txpdctc.core.context;

import lombok.Data;

/**
 * @Description: 本事务组主要信息
 * @Author: lht
 * @Date: 2020-01-13 11:18
 */
@Data
public class TxContextInfo {

    //事务组id
    private String groupId;
    //是否为发起者
    private Boolean isSponsor;
    private Boolean isTxGroupTimer;
    //是否为发起者
    private Object lock = new Object();


    public TxContextInfo(String groupId, boolean isSponsor) {
        this.groupId = groupId;
        this.isSponsor = isSponsor;
        this.isTxGroupTimer = false;
    }
}
