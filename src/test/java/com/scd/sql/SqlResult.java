package com.scd.sql;

import lombok.Data;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;

/**
 * @author chengdu
 * @date 2019/9/1.
 */
@Data
public class SqlResult {

    private String id;

    private String databaseId;

    private SqlSource sqlSource;

    private MixedSqlNode mixedSqlNode;

    public SqlResult(String id, String databaseId, SqlSource sqlSource, MixedSqlNode mixedSqlNode) {
        this.id = id;
        this.databaseId = databaseId;
        this.sqlSource = sqlSource;
        this.mixedSqlNode = mixedSqlNode;
    }
}
