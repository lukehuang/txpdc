package com.coder.txpdc.tc.txpdctc.core.caching;

import com.coder.txpdc.exception.TxEndException;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.context.TxUnitInfo;

import java.util.List;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 15:26
 */
public interface TxGroupCaching {
    /**
     * 设置事务上下文
     * @Author: lht
     * @param txContextInfo : 事务上下文
     * @Date: 2020/1/16 16:01
     */
    void setTxContext(TxContextInfo txContextInfo);

    /**
     * 获得事务上下文
     * @author: lht
     * @param groupId :
     * @date: 2020/1/16 16:10
     * @return:
     */
    TxContextInfo getTxContext(String groupId);

    /**
     * @Description: 清除事务缓存信息
     * @Author: lht
     * @Date: 2020/1/15 16:01
     */
    void clear(String groupId);

   /**
    * 事务是否结束，如果结束则报错
    * @Author: lht
    * @Date: 2020/1/16 16:27
    */
    Boolean isNotEnd(String groupId);

    /**
     * 增加事务单元
     * @author: lht
     * @param txUnit :
     * @date: 2020/1/16 16:33
     */
    void setTxUnit(TxUnitInfo txUnit);

    /**
     * @Description: 获得所有事务单元
     * @Author: lht
     * @Date: 2020/1/15 16:03
     */
    List<TxUnitInfo> getTxUnit(String groupId);

    /**
     * @Description: 获得最后一个事务单元
     * @Author: lht
     * @Date: 2020/1/15 16:03
     */
    TxUnitInfo getLastTxUnit(String groupId);

    /**
     * 是否设置了事务组的定时任务
     * @author: lht
     * @param groupId :
     * @date: 2020/1/18 15:03
     */
    Boolean isTxGroupTimer(String groupId);

    /**
     * 设置事务组定时任务已经开启
     * @author: lht
     * @param groupId :
     * @date: 2020/1/18 15:12
     */
    void setTxGroupTimer(String groupId);
}
