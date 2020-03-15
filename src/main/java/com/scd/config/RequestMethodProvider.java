package com.scd.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Component
public class RequestMethodProvider {

    private List<RequestMappingInfoHandlerMapping> handlerMappings;

    public RequestMethodProvider(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public List<RequestMappingInfoHandlerMapping> getHandlerMappings() {
        return handlerMappings;
    }
}
