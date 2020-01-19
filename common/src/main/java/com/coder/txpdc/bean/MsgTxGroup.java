package com.coder.txpdc.bean;

import lombok.Data;

/**
 * 消息传递bean
 * @author: lht
 * @date: 2020-01-19 10:53
 */
@Data
public class MsgTxGroup {
    //事务组id
    private String groupId;
    //事务状态
    private Integer state;

}
