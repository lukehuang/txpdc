package com.coder.txpdc.tc.txpdctc.transfer;

import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.context.TxContextInfo;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 14:55
 */
@Component
public class Tracings {

    private TxGroupCaching txGroupCaching;

    public Tracings(TxGroupCaching txGroupCaching) {
        this.txGroupCaching = txGroupCaching;
    }

    private  String appGroupId = "appGroupId";

    public  void transfer(Setting setting){
        TxGroupInfo txGroupInfo =  TxGlobalContext.getTxGroupInfo();
        if(txGroupInfo!=null){
            setting.set(appGroupId,txGroupInfo.getGroupId());
        }
    }

    public  void apply(Getting getting){
        String groupId = getting.get(appGroupId);
        if(!StringUtils.isEmpty(groupId)){
            //放入本地线程
            TxGlobalContext.setTxGroupInfo(new TxGroupInfo(groupId,Boolean.FALSE));
            //放入缓存
            txGroupCaching.setTxContext(new TxContextInfo(groupId,Boolean.FALSE));
        }

    }

    public interface Getting {
        String get(String key);
    }

    public interface Setting {
        void set(String key,String value);
    }
}
