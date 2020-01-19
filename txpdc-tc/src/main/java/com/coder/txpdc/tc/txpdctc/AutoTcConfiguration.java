package com.coder.txpdc.tc.txpdctc;

import com.coder.txpdc.tc.txpdctc.core.caching.TxGroupCaching;
import com.coder.txpdc.tc.txpdctc.core.caching.impl.MapTxGroupCaching;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 15:30
 */
@Configuration
@ComponentScan
public class AutoTcConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TxGroupCaching txGroupCaching(){
        return new MapTxGroupCaching();
    }


}
