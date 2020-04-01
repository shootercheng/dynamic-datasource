package com.scd.jdbc.mysql;

import com.scd.jdbc.JdbcBaseTest;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author James
 */
public class MysqlJdbc extends JdbcBaseTest {

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static final String url = "jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";

    private static final String userName = "chengdu";

    private static final String password = "chengdu";

    static {
        // 加载驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {
        Connection connection = createConnection(url, userName, password);
        try {
            Properties properties = connection.getClientInfo();
            System.out.println(properties);
            System.out.println(connection.getSchema());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSql() {
        Connection connection = createConnection(url, userName, password);
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("drop table if exists t_user");
            statement.execute("create table t_user (\n" +
                    "\tid int,\n" +
                    "\tname varchar(30),\n" +
                    "\taddress varchar(100)\n" +
                    ");");
            statement.execute("insert into t_user(id, name) values (1, \'cd\')," +
                    "(2, \'shooter\')");
            ResultSet resultSet = statement.executeQuery("select count(1) as num from t_user");
            while (resultSet.next()) {
                Object object = resultSet.getObject("num");
                System.out.println(object);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
