package com.scd.sql;

import lombok.Data;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
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

    private XNode xNode;

    private MixedSqlNode mixedSqlNode;

    public SqlResult(String id, String databaseId, SqlSource sqlSource, XNode xNode, MixedSqlNode mixedSqlNode) {
        this.id = id;
        this.databaseId = databaseId;
        this.sqlSource = sqlSource;
        this.xNode = xNode;
        this.mixedSqlNode = mixedSqlNode;
    }
}
