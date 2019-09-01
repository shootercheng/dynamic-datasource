package com.scd.sql;

import com.scd.mapper.TaskParamMapper;
import com.scd.model.po.TaskParam;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengdu
 * @date 2019/8/31.
 */
public class SqlTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTest.class);

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

    protected String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        while (whitespaceStripper.hasMoreTokens()) {
            builder.append(whitespaceStripper.nextToken());
            builder.append(" ");
        }
        return builder.toString();
    }

    @Test
    public void parseAllSql(){
        Collection<? extends Object> mappedStatements = sqlSessionFactory.getConfiguration().getMappedStatements();
        sqlSessionFactory.getConfiguration().getParameterMaps();
        // 去除statementId 重复数据
        Set<MappedStatement> mappedStatementSet = new HashSet<>(mappedStatements.size());
        for (Object object : mappedStatements){
            if (object instanceof MappedStatement){
                mappedStatementSet.add((MappedStatement) object);
            }
        }
//        Map<String, List<MappedStatement>> statementMap = new HashMap<>(mappedStatements.size());
//        for (MappedStatement mappedStatement : mappedStatementSet){
//            String resource = mappedStatement.getResource();
//            if (!statementMap.containsKey(resource)){
//                List<MappedStatement> list = new ArrayList<>();
//                statementMap.put(resource, list);
//            }
//            statementMap.get(resource).add(mappedStatement);
//        }
        Map<String, List<MappedStatement>> statementMap = mappedStatementSet.stream().collect(Collectors.groupingBy(MappedStatement::getResource));
        Set<Map.Entry<String, List<MappedStatement>>> entries = statementMap.entrySet();
        for (Map.Entry<String, List<MappedStatement>> entry : entries){
            LOGGER.info("mapper xml resource {}", entry.getKey());
            List<MappedStatement> mappedStatementList = entry.getValue();
            mappedStatementList.forEach(mappedStatement -> {
                SqlSource sqlSource = mappedStatement.getSqlSource();
                Object param = null;
                if (sqlSource instanceof DynamicSqlSource) {
                    param = ReflectData.reflectTestData(mappedStatement.getParameterMap().getType());
                }
                String sql = mappedStatement.getId() + ":" + mappedStatement.getBoundSql(param).getSql();
                sql = sql.replace("\n", "");
                sql = removeBreakingWhitespace(sql);
                System.out.println(sql);
            });
        }
    }

    @Test
    public void parseXMLMapperFile() throws Exception {
        Configuration configuration = new Configuration();
        String resource = "mapper/UserRoleMapper.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            XMLMapperBuilderSql builder = new XMLMapperBuilderSql(inputStream, configuration, resource,
                    configuration.getSqlFragments());
            builder.parse();
            List<SqlResult> sqlResultList = builder.getSqlResultList();
            for (SqlResult sqlResult : sqlResultList){
                System.out.println(sqlResult);
            }
        }
    }
}
