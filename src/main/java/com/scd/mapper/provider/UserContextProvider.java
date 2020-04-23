package com.scd.mapper.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author James
 */
public class UserContextProvider {

    public String selectUserProvider(ProviderContext providerContext) {
        String sql = "";
        if ("mysql".equals(providerContext.getDatabaseId())) {
            sql = "select count(1) from t_user where id = " + "#{id}";
        } else if ("pgsql".equals(providerContext.getDatabaseId())) {
            sql = "select count(1) from t_user_1 where id = " + "#{id}";
        }
        return sql;
    }
}
