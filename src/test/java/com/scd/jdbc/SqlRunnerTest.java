package com.scd.jdbc;

import com.scd.jdbc.task.SqlRunnerResult;
import com.scd.jdbc.task.SqlRunnerTask;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.regex.Pattern;

/**
 * @author James
 */
public class SqlRunnerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlRunnerTest.class);

    private static final String DB_NAME = "@#db_name#@";

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private static final Pattern MYSQL_PATTERN = Pattern.compile("^delimiter\\s*(.*)", Pattern.CASE_INSENSITIVE);

    public static UnpooledDataSource createUnpooledDataSource(String resource) throws IOException {
        Properties props = Resources.getResourceAsProperties(resource);
        UnpooledDataSource ds = new UnpooledDataSource();
        ds.setDriver(props.getProperty("driver"));
        ds.setUrl(props.getProperty("url"));
        ds.setUsername(props.getProperty("username"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }

    public static PooledDataSource createPooledDataSource(String resource) throws IOException {
        Properties props = Resources.getResourceAsProperties(resource);
        PooledDataSource ds = new PooledDataSource();
        ds.setDriver(props.getProperty("driver"));
        ds.setUrl(props.getProperty("url"));
        ds.setUsername(props.getProperty("username"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }

    public static void runScript(DataSource ds, String resource, PrintWriter printWriter) throws IOException, SQLException {
        try (Connection connection = ds.getConnection()) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(true);
            runner.setStopOnError(true);
            runner.setLogWriter(null);
            runner.setErrorLogWriter(printWriter);
            runScript(runner, resource);
        }
    }

    public static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            runner.runScript(reader);
        }
    }


    public PooledDataSource createDataSource(String resource) throws IOException {
        Properties props = Resources.getResourceAsProperties(resource);
        String driver = props.getProperty("driver");
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        return new PooledDataSource(driver, url, username, password);
    }

    public SqlRunner createSqlRunner(Connection connection, PrintWriter printWriter) throws SQLException {
        SqlRunner runner = new SqlRunner(connection);
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.setLogWriter(null);
        runner.setErrorLogWriter(printWriter);
        runner.setDefinedelimiterPattern(MYSQL_PATTERN);
        return runner;
    }

    @Test
    public void testScriptRunner() {
        PooledDataSource pooledDataSource = null;
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createPooledDataSource(dbProPath);
            PrintWriter printWriter = new PrintWriter("sql.log");
            String sqlTempPath = "sql/dbtest.sql";
            runScript(pooledDataSource, sqlTempPath, printWriter);
        } catch (IOException e) {

        } catch (SQLException e) {

        } finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }

    @Test
    public void testSqlRunner() {
        PooledDataSource pooledDataSource = null;
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createDataSource(dbProPath);
            PrintWriter printWriter = new PrintWriter("sql.log");
            SqlRunner runner = createSqlRunner(pooledDataSource.getConnection(), printWriter);
            String sqlTempPath = "sql/dump-test.sql";
            runner.executeLineByLine(Resources.getResourceAsReader(sqlTempPath), DB_NAME, "test4");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }

    @Test
    public void testSqlRunnerWithComment() {
        PooledDataSource pooledDataSource = null;
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createDataSource(dbProPath);
            PrintWriter printWriter = new PrintWriter("sql.log");
            SqlRunner runner = createSqlRunner(pooledDataSource.getConnection(), printWriter);
            String sqlTempPath = "sql/dump-test-comment.sql";
            runner.executeLineByLine(Resources.getResourceAsReader(sqlTempPath), DB_NAME, "test4");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }

    @Test
    public void testSqlErrorRollBack() {
        PooledDataSource pooledDataSource = null;
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createDataSource(dbProPath);
            PrintWriter printWriter = new PrintWriter("sql.log");
            SqlRunner runner = createSqlRunner(pooledDataSource.getConnection(), printWriter);
            String sqlTempPath = "sql/dbtest_error.sql";
            runner.executeLineByLine(Resources.getResourceAsReader(sqlTempPath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeSqlException e) {
            e.printStackTrace();
            dropDataBase(pooledDataSource, "error_sql");
        }
        finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }

    private void dropDataBase(PooledDataSource pooledDataSource, String dbName) {
        LOGGER.info("drop database {}", dbName);
        Connection connection = null;
        try {
            connection = pooledDataSource.getConnection();
            int resultSet = connection.createStatement().executeUpdate("DROP DATABASE IF EXISTS " + dbName);
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSqlRunnerMany() {
        PooledDataSource pooledDataSource = null;
        List<String> dbNames = Arrays.asList("test4", "test5", "test6");
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createDataSource(dbProPath);
            PrintWriter printWriter = new PrintWriter("sql.log");
            for (String dbName : dbNames) {
                SqlRunner runner = createSqlRunner(pooledDataSource.getConnection(), printWriter);
                String sqlTempPath = "sql/dbtest_template.sql";
                runner.executeLineByLine(Resources.getResourceAsReader(sqlTempPath), DB_NAME, dbName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeSqlException e) {
            e.printStackTrace();
        } finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }

    @Test
    public void testThreadRun() {
        PooledDataSource pooledDataSource = null;
        List<String> dbNames = Arrays.asList("test4", "test5", "test6");
        try {
            String dbProPath = "sql/db.properties";
            pooledDataSource = createDataSource(dbProPath);
            List<Future<SqlRunnerResult>> futureList = new ArrayList<>(dbNames.size());
            for (String dbName : dbNames) {
                PrintWriter printWriter = new PrintWriter(dbName + "_sql.log");
                SqlRunner runner = createSqlRunner(pooledDataSource.getConnection(), printWriter);
                String sqlTempPath = "sql/dbtest_template.sql";
                Future<SqlRunnerResult> sqlRunnerResultFuture = threadPool.submit(new SqlRunnerTask(runner, DB_NAME,
                        dbName, sqlTempPath));
                futureList.add(sqlRunnerResultFuture);
            }
            for (Future<SqlRunnerResult> resultFuture : futureList) {
                try {
                    SqlRunnerResult sqlRunnerResult = resultFuture.get();
                    LOGGER.info("sql runner result database {} result {}", sqlRunnerResult.getDbName(),
                            sqlRunnerResult.isSuccess());
                    if (!sqlRunnerResult.isSuccess()) {
                        dropDataBase(pooledDataSource, sqlRunnerResult.getDbName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pooledDataSource != null) {
                pooledDataSource.forceCloseAll();
            }
        }
    }
}
