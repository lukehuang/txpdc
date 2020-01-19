package com.coder.txpdc.tc.txpdctc.transfer.feign;

import com.coder.txpdc.bean.TxGroupInfo;
import com.coder.txpdc.tc.txpdctc.core.clear.DungBeetle;
import com.coder.txpdc.tc.txpdctc.core.context.TxGlobalContext;
import com.coder.txpdc.tc.txpdctc.transfer.Tracings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 14:58
 */
@ConditionalOnClass(HandlerInterceptor.class)
@Component
public class SpringTracingApplier implements HandlerInterceptor, WebMvcConfigurer {
    @Autowired
    private DungBeetle dungBeetle;
    @Autowired
    private Tracings tracings;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        tracings.apply(request::getHeader);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Optional<TxGroupInfo> txGroupInfo = Optional.ofNullable(TxGlobalContext.getTxGroupInfo());
        //存在事务组
        if(txGroupInfo.isPresent()){
            //存在错误
            if(ex!=null){
                System.out.println(ex);
            }
            dungBeetle.clearThreadTxGroup();
        }

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
