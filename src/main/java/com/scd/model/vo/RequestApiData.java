package com.scd.model.vo;

import lombok.Data;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Data
public class RequestApiData {
    private RequestMappingInfo requestMappingInfo;
    private HandlerMethod handlerMethod;

    public RequestApiData() {

    }

    public RequestApiData(RequestMappingInfo requestMappingInfo, HandlerMethod handlerMethod) {
        this.requestMappingInfo = requestMappingInfo;
        this.handlerMethod = handlerMethod;
    }
}
