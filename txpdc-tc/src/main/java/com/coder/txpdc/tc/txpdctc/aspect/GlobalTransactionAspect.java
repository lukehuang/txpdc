package com.coder.txpdc.tc.txpdctc.aspect;

import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.clear.DungBeetle;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import com.coder.txpdc.tc.txpdctc.core.context.TxUnitInfo;
import com.coder.txpdc.tc.txpdctc.core.transaction.TxExecutor;
import com.coder.txpdc.util.Guid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 13:52
 */
@Aspect
@Component
@Slf4j
public class GlobalTransactionAspect implements Ordered {

    private TxExecutor txExecutor;
    private TxGroupCaching txGroupCaching;
    private DungBeetle dungBeetle;

    public GlobalTransactionAspect(TxExecutor txExecutor, TxGroupCaching txGroupCaching, DungBeetle dungBeetle) {
        this.txExecutor = txExecutor;
        this.txGroupCaching = txGroupCaching;
        this.dungBeetle = dungBeetle;
    }

    /**
     * @Description: 判断是否创建事务组
     * @Author: lht
     * @Date: 2020/1/15 15:16
     */
    @Around("@annotation(com.coder.txpdc.tc.txpdctc.annotation.GlobalTransaction)")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        TxGroupInfo txGroupInfo =  TxGlobalContext.getTxGroupInfo();
        //本地存在事务组
        if(txGroupInfo !=null){
            return point.proceed();
        }
        //生成事务组
        txGroupInfo = new TxGroupInfo(Guid.generateId(),Boolean.TRUE);

        System.out.println("================GroupId:"+txGroupInfo.getGroupId());
        //存在线程中
        TxGlobalContext.setTxGroupInfo(txGroupInfo);
        //存储缓存事务上下文
        TxContextInfo txContextInfo = new TxContextInfo(txGroupInfo.getGroupId(),txGroupInfo.isSponsor());
        txGroupCaching.setTxContext(txContextInfo);
        try{
            return txExecutor.call(txGroupInfo,point::proceed);
        }catch (Exception e){
            log.error("business error ",e);
            throw e;
        }finally {
            //只有发起者才会挂起线程等待
            Object lock = txContextInfo.getLock();
            synchronized(lock){
                lock.wait();
            }
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
