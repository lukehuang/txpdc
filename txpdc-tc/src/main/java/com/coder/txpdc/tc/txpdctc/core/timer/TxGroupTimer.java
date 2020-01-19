package com.coder.txpdc.tc.txpdctc.core.timer;

import com.coder.txpdc.exception.TxEndException;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.transaction.LocalTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author: lht
 * @date: 2020-01-18 14:46
 */
@Component
@Slf4j
public class TxGroupTimer {
    private LocalTransactionManager transactionManager;

    private TxGroupCaching txGroupCaching;

    public TxGroupTimer(LocalTransactionManager transactionManager, TxGroupCaching txGroupCaching) {
        this.transactionManager = transactionManager;
        this.txGroupCaching = txGroupCaching;
    }

    private  final ScheduledExecutorService scheduledExecutorService =
            new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

    private  final ExecutorService executorService =
            new ThreadPoolExecutor(10,10,0,TimeUnit.SECONDS,new LinkedBlockingDeque<>());

    private final Map<String,ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();


    /**
     * 增加事务工作定时任务
     * @author: lht
     * @date: 2020/1/18 14:54
     */
    public void addWorker(String groupId){
        if(txGroupCaching.isTxGroupTimer(groupId)){
            return ;
        }
        txGroupCaching.setTxGroupTimer(groupId);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(()->{
            executorService.submit(()->{
                try {
                    if(txGroupCaching.isNotEnd(groupId)){
                        transactionManager.submitTxGroup(groupId,2);
                    }
                }catch (TxEndException e){
                    log.info(e.getMessage());
                }catch (Exception e) {
                    //发送失败记录 1
                    //本地记录错误日志 2
                    log.error("error",e);
                }finally {
                    System.out.println("定时任务清除事务组："+groupId);
                }
            });
        },10,TimeUnit.SECONDS);
        //添加到
        scheduledFutureMap.put(groupId,scheduledFuture);
    }

    /**
     * 清除定时任务
     * @author: lht
     * @param groupId :
     * @date: 2020/1/19 10:59
     */
    public void cancelTxGroupTimer(String groupId){
        Optional<ScheduledFuture> scheduledFuture = Optional.ofNullable(scheduledFutureMap.get(groupId));
        scheduledFuture.ifPresent(s -> s.cancel(true));
    }
}
