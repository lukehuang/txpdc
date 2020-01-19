package com.coder.txpdc.tc.txpdctc.transfer.feign;

import com.coder.txpdc.tc.txpdctc.transfer.Tracings;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 14:45
 */
@ConditionalOnClass(Feign.class)
@Component
@Order
public class FeignTransfer  implements RequestInterceptor {
    @Autowired
    private Tracings tracings;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //应该要记录方法信息的

        tracings.transfer(requestTemplate::header);
    }
}
