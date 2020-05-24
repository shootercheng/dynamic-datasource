package com.scd.sql;

import com.scd.mapper.TestUserMapper;
import com.scd.model.po.TestUser;
import com.scd.sql.ognl.OgnlClassResolver;
import com.scd.sql.ognl.OgnlDefineMap;
import com.scd.sql.ognl.OgnlMemberAccess;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testMapperParser() {
        String resource = "mapper/TestUserMapper.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            XPathParser xPathParser = new XPathParser(inputStream);
            XNode xNode = xPathParser.evalNode("/mapper");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectUser() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TestUserMapper testUserMapper = sqlSession.getMapper(TestUserMapper.class);
            List<Integer> list = new ArrayList<>();
            List<TestUser> userList = testUserMapper.selectUsers(list);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testSelectUserList() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TestUserMapper testUserMapper = sqlSession.getMapper(TestUserMapper.class);
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            List<TestUser> userList = testUserMapper.selectUsers(list);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testOgnlMap() throws OgnlException {
        String expression = "list != null and list.size() > 0";
        Object node = Ognl.parseExpression(expression);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Object parameterObject = wrapCollection(list);
        OgnlMemberAccess MEMBER_ACCESS = new OgnlMemberAccess();
        OgnlClassResolver CLASS_RESOLVER = new OgnlClassResolver();
        Map context = Ognl.createDefaultContext(parameterObject, MEMBER_ACCESS, CLASS_RESOLVER, null);
        Object value = Ognl.getValue(node, context, parameterObject);
        Assert.assertTrue(value instanceof Boolean);
        Boolean bool = (Boolean) value;
        Assert.assertTrue(bool);
    }

    @Test
    public void testOgnlListEmpty() throws OgnlException {
        String expression = "list != null and list.size() > 0";
        Object node = Ognl.parseExpression(expression);
        List<Integer> list = new ArrayList<>();
        Object parameterObject = wrapCollection(list);
        Map<String, Object> bindings = new OgnlDefineMap();
        bindings.put(DynamicContext.PARAMETER_OBJECT_KEY, parameterObject);
        OgnlMemberAccess MEMBER_ACCESS = new OgnlMemberAccess();
        OgnlClassResolver CLASS_RESOLVER = new OgnlClassResolver();
        Map context = Ognl.createDefaultContext(bindings, MEMBER_ACCESS, CLASS_RESOLVER, null);
        Object value = Ognl.getValue(node, context, bindings);
        Assert.assertTrue(value instanceof Boolean);
        Boolean bool = (Boolean) value;
        Assert.assertFalse(bool);
    }

    @Test
    public void testOgnlList() throws OgnlException {
        String expression = "list != null and list.size() > 0";
        Object node = Ognl.parseExpression(expression);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Object parameterObject = wrapCollection(list);
        Map<String, Object> bindings = new OgnlDefineMap();
        bindings.put(DynamicContext.PARAMETER_OBJECT_KEY, parameterObject);
        OgnlMemberAccess MEMBER_ACCESS = new OgnlMemberAccess();
        OgnlClassResolver CLASS_RESOLVER = new OgnlClassResolver();
        Map context = Ognl.createDefaultContext(bindings, MEMBER_ACCESS, CLASS_RESOLVER, null);
        Object value = Ognl.getValue(node, context, bindings);
        Assert.assertTrue(value instanceof Boolean);
        Boolean bool = (Boolean) value;
        Assert.assertTrue(bool);
    }

    private Object wrapCollection(Object object) {
        DefaultSqlSession.StrictMap map;
        if (object instanceof Collection) {
            map = new DefaultSqlSession.StrictMap();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }

            return map;
        } else if (object != null && object.getClass().isArray()) {
            map = new DefaultSqlSession.StrictMap();
            map.put("array", object);
            return map;
        } else {
            return object;
        }
    }

    private ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

    @Test
    public void testIfTestOgnl() {
        Map<String, Object> parameterObject = new HashMap<>();
        String expression = " name != null and name != '' ";
        parameterObject.put("name", "cd");
        Assert.assertTrue(expressionEvaluator.evaluateBoolean(expression, parameterObject));
        parameterObject.put("name", "");
        Assert.assertFalse(expressionEvaluator.evaluateBoolean(expression, parameterObject));
        String expressionOr = " name == 'James' or name == 'Sun' ";
        parameterObject.put("name", "James");
        Assert.assertTrue(expressionEvaluator.evaluateBoolean(expressionOr, parameterObject));
        parameterObject.put("name", "Sun");
        Assert.assertTrue(expressionEvaluator.evaluateBoolean(expressionOr, parameterObject));
        parameterObject.put("name", " ");
        Assert.assertFalse(expressionEvaluator.evaluateBoolean(expressionOr, parameterObject));
        parameterObject.clear();
        Assert.assertFalse(expressionEvaluator.evaluateBoolean(expressionOr, parameterObject));
    }
}
