package com.scd.controller;

import com.scd.config.RequestMethodProvider;
import com.scd.constant.ExportType;
import com.scd.model.vo.ExportResult;
import com.scd.service.ApiExportService;
import com.scd.service.impl.ExcelExportServiceImpl;
import com.scd.service.impl.TxtExportServiceImpl;
import com.scd.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@RestController
public class ApiExportController {
    @Autowired
    private RequestMethodProvider requestMethodProvider;

    @RequestMapping(value = "/export/all", method = RequestMethod.GET)
    public ExportResult exportAllApi(ExportType exportType) {
        ExportResult exportResult = new ExportResult();
        List<String> errorMsg = new ArrayList<>();
        if (exportType == null) {
            exportResult.setFileStatus(false);
            return exportResult;

        }
        List<RequestMappingInfoHandlerMapping> handlerMappings = requestMethodProvider.getHandlerMappings();
        if (!checkParam(handlerMappings, exportResult)) {
            return exportResult;
        }
        ApiExportService apiExportService = null;
        switch (exportType) {
            case TXT:
                apiExportService = BeanUtil.getBean(TxtExportServiceImpl.class);
                break;
            case EXCEL:
                apiExportService = BeanUtil.getBean(ExcelExportServiceImpl.class);
                break;
            default:
                errorMsg.add("unknown type");
                return exportResult;
        }
        exportResult = apiExportService.exportAllApi(handlerMappings);
        return exportResult;
    }

    private boolean checkParam(List<RequestMappingInfoHandlerMapping> handlerMappings, ExportResult exportResult) {
        if (CollectionUtils.isEmpty(handlerMappings)) {
            List<String> errorList = new ArrayList<>();
            errorList.add("api handlermappings is empty");
            exportResult.setErrorMsg(errorList);
            return false;
        }
        return true;
    }
}
