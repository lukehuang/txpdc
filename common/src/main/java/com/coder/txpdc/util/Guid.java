package com.coder.txpdc.util;

import cn.hutool.core.util.IdUtil;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 14:41
 */
public class Guid {
    /**
     * @Description: 分布式id，现在先临时
     * @Author: lht
     * @Date: 2020/1/13 13:59
     */
    public static String generateId(){
        return IdUtil.simpleUUID();
    }
}
