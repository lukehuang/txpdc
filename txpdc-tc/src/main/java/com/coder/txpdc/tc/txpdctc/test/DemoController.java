package com.coder.txpdc.tc.txpdctc.test;

import com.coder.txpdc.bean.MsgTxGroup;
import com.coder.txpdc.tc.txpdctc.core.listener.impl.TxGroupCloseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-13 16:38
 */
@RestController
public class DemoController {
    @Autowired
    private TxGroupCloseListener txGroupCloseListener;


    @GetMapping("/submit")
    public String submit(String groupId,Integer state)throws Exception{
        MsgTxGroup msgTxGroup = new MsgTxGroup();
        msgTxGroup.setGroupId(groupId);
        msgTxGroup.setState(state);
        txGroupCloseListener.working(msgTxGroup);
        return "sumbited";
    }

}
