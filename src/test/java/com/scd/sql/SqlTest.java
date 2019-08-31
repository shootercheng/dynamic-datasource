package com.scd.sql;

import com.scd.mapper.TaskParamMapper;
import com.scd.model.po.TaskParam;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.*;

import static org.mockito.Mockito.mock;

/**
 * @author chengdu
 * @date 2019/8/31.
 */
public class SqlTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "templates/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void testAllStatementIds(){
        Collection<? extends Object> mappedStatements = sqlSessionFactory.getConfiguration().getMappedStatements();
        Set<String> statementIds = new HashSet<>();
        for (Object object : mappedStatements){
            if (object instanceof MappedStatement) {
                MappedStatement mappedStatement = (MappedStatement) object;
                statementIds.add(mappedStatement.getId());
            }
        }
        for (String statementId : statementIds){
            System.out.println(statementId);
        }
    }

    @Test
    public void testMapper(){
        SqlSession session = sqlSessionFactory.openSession();
        TaskParamMapper taskParamMapper = session.getMapper(TaskParamMapper.class);
        TaskParam taskParam = new TaskParam(null, "1","1");
        taskParamMapper.insert(taskParam);
        Integer id = taskParam.getId();
        TaskParam taskParamDb = taskParamMapper.selectByPrimaryKey(id);
        Assert.assertNotNull(taskParamDb);
        if (session != null){
            session.close();
        }
    }

    @Test
    public void parseAllSql(){
        Collection<? extends Object> mappedStatements = sqlSessionFactory.getConfiguration().getMappedStatements();
        List<String> stateMentIds = new ArrayList<>();
        for (Object object : mappedStatements){
            if (object instanceof MappedStatement) {
                MappedStatement mappedStatement = (MappedStatement) object;
                String statementId = mappedStatement.getId();
                if (!stateMentIds.contains(statementId)) {
                    String sql = statementId + ":" + mappedStatement.getBoundSql(null).getSql();
                    sql = sql.replace("\n", "");
                    System.out.println(sql);
                    stateMentIds.add(statementId);
                }
            }
        }
    }
}
