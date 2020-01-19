package com.coder.txpdc.tc.txpdctc.core.context;

import lombok.Data;


/**
 * @Description: 主要用于日志记录
 * @Author: lht
 * @Date: 2020-01-13 13:45
 */
@Data
public class TxUnitInfo {
    //事务组id
    private String groupId;
    //事务单元id
    private String unitId;
    //方法
    private String methodInfo;
    //参数
    private String paramsInfo;

}
