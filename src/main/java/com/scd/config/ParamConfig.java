package com.scd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chengdu
 * @date 2019/12/18
 */
@Component
@Data
public class ParamConfig {

    @Value("${file.export-temp:export-temp}")
    private String exportTempPath;
}
