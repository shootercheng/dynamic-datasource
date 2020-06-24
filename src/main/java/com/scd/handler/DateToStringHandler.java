package com.scd.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author James
 */
public class DateToStringHandler extends BaseTypeHandler<Date> {

    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        String dateStr = formatDate(date);
        preparedStatement.setNString(i, dateStr);
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
