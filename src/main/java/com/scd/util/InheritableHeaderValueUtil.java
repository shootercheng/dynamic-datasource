package com.scd.util;

import java.util.Map;

/**
 * @author chengdu
 * @date 2019/11/24
 */
public class InheritableHeaderValueUtil {
    private static ThreadLocal<Map<String, String>> headerMap = new InheritableThreadLocal<>();

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
