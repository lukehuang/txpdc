package com.coder.txpdc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 17:07
 */

public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> type) {
        try {
            return Optional.ofNullable(applicationContext).orElseThrow(() -> new IllegalStateException("non in spring application context.")).getBean(type);
        } catch (Exception e) {
            return null;
        }
    }
}
