package com.scd.service.impl;

import com.scd.config.ParamConfig;
import com.scd.model.vo.ExportResult;
import com.scd.model.vo.RequestApiData;
import com.scd.service.ApiExportBase;
import com.scd.service.ApiExportService;
import com.scd.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Service
public class TxtExportServiceImpl extends ApiExportBase implements ApiExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TxtExportServiceImpl.class);

    @Autowired
    private ParamConfig paramConfig;

    @Override
    public ExportResult exportAllApi(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        String tempPath = paramConfig.getExportTempPath();
        String exportPath = tempPath + "/" + UUID.randomUUID() + "_API.txt";
        ExportResult exportResult = new ExportResult();
        FileWriter fileWriter = null;
        try {
            FileUtil.createNewDirFile(exportPath);
            exportResult.setFilePath(exportPath);
            fileWriter = new FileWriter(exportPath, true);
            List<RequestApiData> apiDataList = super.parseApiData(handlerMappings);
            int i = 1;
            for (RequestApiData requestApiData : apiDataList) {
                RequestMappingInfo requestMappingInfo = requestApiData.getRequestMappingInfo();
                HandlerMethod handlerMethod = requestApiData.getHandlerMethod();
                FileUtil.writeStrToFile(fileWriter, i + "\n");
                FileUtil.writeStrToFile(fileWriter,  requestMappingInfo + "\n");
                FileUtil.writeStrToFile(fileWriter, handlerMethod + "\n");
                i++;
            }
            LOGGER.info("export api to txt end");
            exportResult.setFileStatus(true);
        } catch (IOException e) {
            e.printStackTrace();
            exportResult.setFileStatus(false);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    LOGGER.error("close filewriter error");
                }
            }
        }
        return exportResult;
    }
}
