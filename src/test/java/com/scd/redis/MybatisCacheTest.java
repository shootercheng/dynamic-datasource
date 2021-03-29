package com.scd.redis;

import com.scd.mapper.TaskParamMapper;
import com.scd.model.po.TaskParam;
import com.scd.sql.SqlTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.List;

/**
 * @author James
 */
public class MybatisCacheTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTest.class);

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "templates/mybatis-config-redis.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


    @Test
    public void selectTest() {
        SqlSession session = sqlSessionFactory.openSession();
        TaskParamMapper taskParamMapper = session.getMapper(TaskParamMapper.class);
        List<TaskParam> taskParamList = taskParamMapper.selectAllTask();
        // 写入缓存 commit or close  TransactionalCacheManager commit
//        session.commit();
        session.close();
        System.out.println(taskParamList);
    }

}
