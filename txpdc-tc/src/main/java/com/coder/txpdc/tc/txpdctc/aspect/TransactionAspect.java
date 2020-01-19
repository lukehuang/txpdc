package com.coder.txpdc.tc.txpdctc.aspect;

import com.alibaba.fastjson.JSON;
import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import com.coder.txpdc.tc.txpdctc.core.context.TxUnitInfo;
import com.coder.txpdc.tc.txpdctc.core.transaction.TxExecutor;
import com.coder.txpdc.util.Guid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-14 17:22
 */
@Aspect
@Component
public class TransactionAspect implements Ordered {

    private TxGroupCaching txGroupCaching;
    private TxExecutor txExecutor;

    public TransactionAspect(TxGroupCaching txGroupCaching, TxExecutor txExecutor) {
        this.txGroupCaching = txGroupCaching;
        this.txExecutor = txExecutor;
    }

    /**
     * @Description: 生成事务单元
     * @Author: lht
     * @Date: 2020/1/15 15:17
     */
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        TxGroupInfo txGroupInfo = TxGlobalContext.getTxGroupInfo();
        //本线程中存在事务组
        if(txGroupInfo != null&&txGroupCaching.isNotEnd(txGroupInfo.getGroupId())){
            //生成事务单位
            TxUnitInfo txUnitInfo = new TxUnitInfo();
            txUnitInfo.setGroupId(txGroupInfo.getGroupId());
            txUnitInfo.setUnitId(Guid.generateId());
            txUnitInfo.setMethodInfo("方法信息");
            txUnitInfo.setParamsInfo("参数信息");
            //保存到缓存中
            txGroupCaching.setTxUnit(txUnitInfo);
            //发送到远端
            System.out.println(JSON.toJSONString(txUnitInfo));
            return txExecutor.call(txGroupInfo,point::proceed);
        }
        return point.proceed();
    }

    @Override
    public int getOrder() {
        //该拦截器要在GlobalTransactionAspect后面
        return 2;
    }
}
