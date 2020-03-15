package com.scd.service;

import com.scd.model.vo.ExportResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/12/18
 */
public interface ApiExportService {
    ExportResult exportAllApi(List<RequestMappingInfoHandlerMapping> handlerMappings);
}
