package com.coder.txpdc.bean;

import lombok.Data;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 15:43
 */
@Data
public class TxGroupInfo {
    //事务组id
    private String groupId;

    //是否为发起者
    private boolean isSponsor;

    public TxGroupInfo(String groupId, boolean isSponsor) {
        this.groupId = groupId;
        this.isSponsor = isSponsor;
    }
}
