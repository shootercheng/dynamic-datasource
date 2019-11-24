package com.scd.mybatis;

import com.github.autoTest.AutoTestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;

/**
 * @author chengdu
 * @date 2019/10/20.
 */
public class MyBatisSqlTest {

    @Test
    public void testMyBatisSql() throws Exception {
        Reader resourceAsReader = Resources.getResourceAsReader("templates/mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsReader);
        resourceAsReader.close();
        AutoTestMapper autoTestMapper = new AutoTestMapper("E:\\Github\\dynamicdatasource\\src\\main\\java\\com\\scd\\mapper");
        autoTestMapper.openSqlSession(sqlSessionFactory);
    }
}
