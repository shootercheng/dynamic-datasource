package com.scd.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Data
public class ExportResult {
    private String filePath;

    private Boolean fileStatus;

    private List<String> errorMsg;
}
