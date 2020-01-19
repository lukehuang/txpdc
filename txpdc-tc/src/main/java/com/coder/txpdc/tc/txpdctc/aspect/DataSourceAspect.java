package com.coder.txpdc.tc.txpdctc.aspect;

import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.caching.ConnectionProxyCache;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import com.coder.txpdc.tc.txpdctc.core.transaction.pdc.PdcConnectionProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Optional;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 13:50
 */
@Aspect
@Component
@Order(0)
public class DataSourceAspect  {

    private  TxGroupCaching txGroupCaching;

    public DataSourceAspect(TxGroupCaching txGroupCaching) {
        this.txGroupCaching = txGroupCaching;
    }

    /**
     * @Description: 创建代理连接
     * @Author: lht
     * @Date: 2020/1/15 15:17
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        TxGroupInfo txGroupInfo = TxGlobalContext.getTxGroupInfo();
        //判断
        if(txGroupInfo != null&&txGroupCaching.isNotEnd(txGroupInfo.getGroupId())){
            System.out.println("getConnection:"+txGroupInfo.getGroupId());
            //没有连接
            if(!ConnectionProxyCache.contains(txGroupInfo.getGroupId())){
                //创建代理连接
                PdcConnectionProxy pdcConnectionProxy = new PdcConnectionProxy((Connection) point.proceed());
                ConnectionProxyCache.put(txGroupInfo.getGroupId(),pdcConnectionProxy);
                return pdcConnectionProxy;
            }
            return ConnectionProxyCache.get(txGroupInfo.getGroupId());
        }else{
            //本线程中不存在事务组
            return point.proceed();
        }

    }
}
