package com.scd.jdbc.task;

import com.scd.util.SqlRunner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.RuntimeSqlException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

/**
 * @author James
 */
public class SqlRunnerTask implements Callable<SqlRunnerResult> {

    private SqlRunner runner;

    private String templateName;

    private String dbName;

    private PrintWriter printWriter;

    private String sqlTempPath;

    public SqlRunnerTask(SqlRunner runner, String templateName, String dbName,
                         PrintWriter printWriter, String sqlTempPath) {
        this.runner = runner;
        this.templateName = templateName;
        this.dbName = dbName;
        this.printWriter = printWriter;
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
        }
        return sqlRunnerResult;
    }
}
