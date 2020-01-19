package com.coder.txpdc.tc.txpdctc;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 15:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {AutoTcConfiguration.class})
public @interface EnableGlobalransaction {
}
