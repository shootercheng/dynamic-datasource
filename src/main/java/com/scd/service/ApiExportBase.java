package com.scd.service;

import com.scd.model.vo.RequestApiData;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengdu
 * @date 2019/12/18
 */
public abstract class ApiExportBase {
    private static List<RequestApiData> requestDataList = new ArrayList<>(500);

    public List<RequestApiData> parseApiData(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        if (CollectionUtils.isEmpty(requestDataList)) {
            for (RequestMappingInfoHandlerMapping handlerMapping : handlerMappings) {
                Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
                Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entrySet = handlerMethodMap.entrySet();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entrySet) {
                    RequestMappingInfo requestMappingInfo = entry.getKey();
                    HandlerMethod handlerMethod = entry.getValue();
                    RequestApiData requestApiData = new RequestApiData(requestMappingInfo, handlerMethod);
                    requestDataList.add(requestApiData);
                }
            }
        }
        return requestDataList;
    }
}
