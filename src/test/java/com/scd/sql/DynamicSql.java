package com.scd.sql;

import com.scd.mapper.TestUserMapper;
import com.scd.model.po.TestUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author James
 */
public class DynamicSql {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "templates/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void testListGenId() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TestUserMapper testUserMapper = sqlSession.getMapper(TestUserMapper.class);
            List<TestUser> list = createList(10);
            int sum = testUserMapper.insertUserList(list);
            sqlSession.commit();
            Assert.assertEquals(10, sum);
            Assert.assertTrue(list.get(0).getId() != null);
        }
    }

    private List<TestUser> createList(int size) {
        List<TestUser> userList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TestUser testUser = new TestUser("james" + i, "ny");
            userList.add(testUser);
        }
        return userList;
    }

    @Test
    public void testStr() {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add("1");
        stringJoiner.add("2");
        System.out.println(stringJoiner);
    }
}
