package com.scd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
@Component
@ConfigurationProperties(prefix = "spring")
public class DsConfig {

    private Map<String, DsInfo> datasource;

    public Map<String, DsInfo> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DsInfo> datasource) {
        this.datasource = datasource;
    }
}
