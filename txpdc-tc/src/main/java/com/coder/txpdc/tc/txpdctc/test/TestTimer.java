package com.coder.txpdc.tc.txpdctc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: lht
 * @date: 2020-01-18 10:26
 */
public class TestTimer   {
    static ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);
    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            final int j = i;
            scheduledExecutorService.schedule(()->{
                executorService.submit(()->{
                    try{
                        Thread.sleep(1000L);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    System.out.println("i%4:"+(j%10));
                });
            },i%10, TimeUnit.SECONDS);
        }
        System.out.println("执行结束");

    }
}
