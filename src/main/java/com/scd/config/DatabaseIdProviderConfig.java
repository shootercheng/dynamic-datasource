package com.scd.config;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author James
 */
// MybatisAutoConfiguration
@Component
public class DatabaseIdProviderConfig implements DatabaseIdProvider {

    private Properties properties;

    public DatabaseIdProviderConfig() {
        properties = new Properties();
        // database product name
        properties.put("MySQL", "mysql");
        properties.put("PostgreSQL","pgsql");
    }

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        return getDatabaseName(dataSource);
    }

    private String getDatabaseName(DataSource dataSource) throws SQLException {
        String productName = getDatabaseProductName(dataSource);
        if (properties != null) {
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                if (productName.contains((String) property.getKey())) {
                    return (String) property.getValue();
                }
            }
            // no match, return null
            throw new IllegalArgumentException("unknown database product name " + productName);
        }
        return productName;
    }

    private String getDatabaseProductName(DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            DatabaseMetaData metaData = con.getMetaData();
            return metaData.getDatabaseProductName();
        }

    }
}
