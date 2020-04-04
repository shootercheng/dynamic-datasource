package com.scd.jdbc.task;

/**
 * @author James
 */
public class SqlRunnerResult {
    private boolean isSuccess;

    private String dbName;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
