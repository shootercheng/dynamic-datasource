package com.scd.sql;

import lombok.Data;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author chengdu
 * @date 2019/9/1.
 */
@Data
public class SqlResult {

    private String id;

    private SqlSource sqlSource;

    public SqlResult(String id, SqlSource sqlSource) {
        this.id = id;
        this.sqlSource = sqlSource;
    }
}
