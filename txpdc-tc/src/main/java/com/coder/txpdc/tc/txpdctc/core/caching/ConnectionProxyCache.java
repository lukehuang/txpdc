package com.coder.txpdc.tc.txpdctc.core.caching;

import com.coder.txpdc.constant.ErrorInfo;
import com.coder.txpdc.exception.TxGlobalException;
import com.coder.txpdc.tc.txpdctc.core.transaction.pdc.PdcConnectionProxy;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: lht
 * @Date: 2020-01-15 14:22
 */
public class ConnectionProxyCache {

    private ConnectionProxyCache() {
    }

    //缓存连接
    private final static ConcurrentHashMap<String, PdcConnectionProxy> CONNECTION_PROXY_CACHE = new ConcurrentHashMap<>();

    /**
     * 保存连接到缓存中
     * @author: lht
     * @param groupId :
     * @param pdcConnectionProxy :
     * @date: 2020/1/18 14:25
     */
    public static void put(String groupId,PdcConnectionProxy pdcConnectionProxy){
        CONNECTION_PROXY_CACHE.put(groupId,pdcConnectionProxy);
    }

    /**
     * 判断事务组是否存在连接
     * @author: lht
     * @param groupId :
     * @date: 2020/1/18 14:26
     */
    public static boolean contains(String groupId){
        return CONNECTION_PROXY_CACHE.contains(groupId);
    }

    /**
     * 获得连接，不存在则报错
     * @author: lht
     * @param groupId :
     * @date: 2020/1/18 14:27
     */
    public static PdcConnectionProxy get(String groupId) throws TxGlobalException{
        Optional<PdcConnectionProxy> pdcConnectionProxy =  Optional.ofNullable(CONNECTION_PROXY_CACHE.get(groupId));
        if(pdcConnectionProxy.isPresent()){
            return pdcConnectionProxy.get();
        }
        throw new TxGlobalException(ErrorInfo.E_10001);
    }

    /**
     * 获得连接可能为空
     * @author: lht
     * @param groupId :
     * @date: 2020/1/18 14:28
     */
    public static Optional<PdcConnectionProxy> getOptional(String groupId){
        return  Optional.ofNullable(CONNECTION_PROXY_CACHE.get(groupId));
    }

    /**
     * 清除连接
     * @author: lht
     * @date: 2020/1/18 14:28
     */
    public static void clear(String groupId){
        CONNECTION_PROXY_CACHE.remove(groupId);
    }
}
