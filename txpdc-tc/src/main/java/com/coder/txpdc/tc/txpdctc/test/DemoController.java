package com.coder.txpdc.tc.txpdctc.test;

import com.coder.txpdc.tc.txpdctc.core.transaction.LocalTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 16:38
 */
@RestController
@Slf4j
public class DemoController {
    private LocalTransactionManager transactionManager;

    public DemoController(LocalTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @GetMapping("/submit")
    public String submit(String groupId,int state)throws Exception{
        transactionManager.submitTxGroup(groupId,state);
        return "sumbited";
    }

}
