package com.scd.service.impl;

import com.alibaba.excel.EasyExcel;
import com.scd.config.ParamConfig;
import com.scd.model.vo.ApiVo;
import com.scd.model.vo.ExportResult;
import com.scd.model.vo.RequestApiData;
import com.scd.service.ApiExportBase;
import com.scd.service.ApiExportService;
import com.scd.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Service
public class ExcelExportServiceImpl extends ApiExportBase implements ApiExportService {

    @Autowired
    private ParamConfig paramConfig;

    @Override
    public ExportResult exportAllApi(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        String tempPath = paramConfig.getExportTempPath();
        String exportPath = tempPath + "/" + UUID.randomUUID() + "_API.xlsx";
        ExportResult exportResult = new ExportResult();
        try {
            List<RequestApiData> apiDataList = super.parseApiData(handlerMappings);
            int i = 1;
            List<ApiVo> apiVoList = new ArrayList<>(apiDataList.size());
            for (RequestApiData requestApiData : apiDataList) {
                RequestMappingInfo requestMappingInfo = requestApiData.getRequestMappingInfo();
                HandlerMethod handlerMethod = requestApiData.getHandlerMethod();
                ApiVo apiVo = new ApiVo();
                apiVo.setNum(i);
                apiVo.setUrl(requestMappingInfo.toString());
                apiVo.setMethod(handlerMethod.toString());
                apiVoList.add(apiVo);
                i++;
            }
            EasyExcel.write(exportPath, ApiVo.class).sheet("API").doWrite(apiVoList);
            exportResult.setFileStatus(true);
            exportResult.setFilePath(exportPath);
        } catch (Exception e) {
            exportResult.setFileStatus(false);
        }
        return exportResult;
    }
}
