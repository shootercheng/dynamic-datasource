package com.scd.util;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
public class LookUpKeyUtil {

    // 存储路由的 key, 线程安全
    private static ThreadLocal<String> lookupThreadKey = new ThreadLocal<String>();

    public static void setLookupKey(String key){
        lookupThreadKey.set(key);
    }

    public static String getLookupKey(){
        return lookupThreadKey.get();
    }

    public static void removeLookupKey(){
        lookupThreadKey.remove();
    }
}
