package com.scd.config;

import lombok.Data;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
@Data
public class DsInfo {
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
    private String template;
    private String dburl;
}
