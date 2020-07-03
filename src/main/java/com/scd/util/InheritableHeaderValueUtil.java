package com.scd.util;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * @author chengdu
 * @date 2019/11/24
 */
public class InheritableHeaderValueUtil {
    private static ThreadLocal<Map<String, String>> headerMap = new TransmittableThreadLocal<>();

    public static ThreadLocal<Map<String, String>> getHeaderMap() {
        return headerMap;
    }

    public static void setHeaderMap(Map<String, String> map) {
        InheritableHeaderValueUtil.headerMap.set(map);
    }

    public static void removeHeaders() {
        headerMap.remove();
    }
}
