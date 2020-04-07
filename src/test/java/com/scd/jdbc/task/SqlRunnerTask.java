package com.scd.jdbc.task;

import com.scd.jdbc.SqlRunner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.RuntimeSqlException;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author James
 */
public class SqlRunnerTask implements Callable<SqlRunnerResult> {

    private SqlRunner runner;

    private String templateName;

    private String dbName;

    private String sqlTempPath;

    public SqlRunnerTask(SqlRunner runner, String templateName, String dbName, String sqlTempPath) {
        this.runner = runner;
        this.templateName = templateName;
        this.dbName = dbName;
        this.sqlTempPath = sqlTempPath;
    }

    @Override
    public SqlRunnerResult call() {
        SqlRunnerResult sqlRunnerResult = new SqlRunnerResult();
        sqlRunnerResult.setDbName(dbName);
        try {
            runner.executeLineByLine(Resources.getResourceAsReader(sqlTempPath), templateName, dbName);
            sqlRunnerResult.setSuccess(true);
        } catch (IOException e) {
        } catch (RuntimeSqlException e) {
        } finally {
            runner.returnConnToPool();
        }
        return sqlRunnerResult;
    }
}
