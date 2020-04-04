package com.scd.util;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Properties;

/**
 * @author James
 */
public class PooledDataSourceUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        String resource = "";
        Properties props = null;
        try {
            props = Resources.getResourceAsProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = props.getProperty("driver");
        url = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    private static class PooledDataSourceHolder {
        private static final PooledDataSource pooledDataSource = new PooledDataSource(driver, url, username, password);
    }

    public static PooledDataSource getPooledDataSource() {
        return PooledDataSourceHolder.pooledDataSource;
    }
}
