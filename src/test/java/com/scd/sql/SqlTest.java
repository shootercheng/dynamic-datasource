package com.scd.sql;

import com.scd.mapper.TaskParamMapper;
import com.scd.model.po.Article;
import com.scd.model.po.TaskParam;
import com.scd.model.po.UserRole;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public void selectTest() {
        SqlSession session = sqlSessionFactory.openSession();
        TaskParamMapper taskParamMapper = session.getMapper(TaskParamMapper.class);
        List<TaskParam> taskParamList = taskParamMapper.selectAllTask();
        System.out.println(taskParamList);
    }

    @Test
    public void selectArrayResult() {
        SqlSession session = sqlSessionFactory.openSession();
        TaskParamMapper taskParamMapper = session.getMapper(TaskParamMapper.class);
        List<String[]> arrayList = taskParamMapper.selectArrRes();
        System.out.println(arrayList);
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
        session.commit();
        if (session != null){
            session.close();
        }
    }

    @Test
    public void testInsertBatch() {
        SqlSession session = sqlSessionFactory.openSession();
        TaskParamMapper taskParamMapper = session.getMapper(TaskParamMapper.class);
        List<TaskParam> taskParams = new ArrayList<>();
        TaskParam taskParam = new TaskParam(null, "" + 8,"");
        for (int i = 0;i < 10; i++) {
            taskParam.setParamValue("" + i);
            taskParams.add(taskParam);
        }
        taskParamMapper.insertBatch(taskParams);
        session.commit();
        session.close();
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
    public void testStrToken(){
        String input = "1        33      4656    \n ewwwe";
        StringTokenizer tokenizer = new StringTokenizer(input);
        while (tokenizer.hasMoreTokens()){
            System.out.println(tokenizer.nextToken());
        }
    }

    @Test
    public void testExOne(){
        String str1 = "bigdata";
        String str2 = "big" + new String("data");
        String str3 = "bigdata";
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
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
        String namespace = "com.scd.mapper.UserRoleMapper";
        for (Map.Entry<String, List<MappedStatement>> entry : entries){
            LOGGER.info("mapper xml resource {}", entry.getKey());
            List<MappedStatement> mappedStatementList = entry.getValue();
            mappedStatementList.forEach(mappedStatement -> {
                SqlSource sqlSource = mappedStatement.getSqlSource();
                Object param = null;
                if (sqlSource instanceof DynamicSqlSource) {
                    param = ReflectData.reflectTestData(mappedStatement.getParameterMap().getType());
                    System.out.println("dynamicsqlsource");
                } else if (sqlSource instanceof RawSqlSource){
                    System.out.println("rawsqlsource");
                } else {
                    System.out.println("unknown");
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
            String regex = "(\\{)(\\S*?)(\\})";
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            for (SqlResult sqlResult : sqlResultList){
                XNode xNode = sqlResult.getXNode();
                String sql = xNode.toString();
                Matcher matcher = pattern.matcher(sql);
                while (matcher.find()){
                    System.out.println(matcher.group(2));
                }
//                DynamicContext context = new DynamicContext(configuration, null);
                MixedSqlNode mixedSqlNode = sqlResult.getMixedSqlNode();
            }
        }
    }

    @Test
    public void parseSqlTest(){
        Collection<? extends Object> mappedStatements = sqlSessionFactory.getConfiguration().getMappedStatements();
        sqlSessionFactory.getConfiguration().getParameterMaps();
        // 去除statementId 重复数据
        Set<MappedStatement> mappedStatementSet = new HashSet<>(mappedStatements.size());
        for (Object object : mappedStatements){
            if (object instanceof MappedStatement){
                mappedStatementSet.add((MappedStatement) object);
            }
        }
        Map<String, List<MappedStatement>> statementMap = mappedStatementSet.stream().collect(Collectors.groupingBy(MappedStatement::getResource));
        Set<Map.Entry<String, List<MappedStatement>>> entries = statementMap.entrySet();
        String id1 = "com.scd.mapper.UserRoleMapper.batchSave";
        for (Map.Entry<String, List<MappedStatement>> entry : entries){
            LOGGER.info("mapper xml resource {}", entry.getKey());
            List<MappedStatement> mappedStatementList = entry.getValue();
            mappedStatementList.forEach(mappedStatement -> {
                SqlSource sqlSource = mappedStatement.getSqlSource();
                Object param = null;
                if (id1.equals(mappedStatement.getId())) {
                    if (sqlSource instanceof DynamicSqlSource) {
//                        List<Map<String, Object>> mapList = new ArrayList<>();
//                        Map<String, Object> map1 = new HashMap<>();
//                        map1.put("userId","1");
//                        map1.put("roleId", "2");
//                        Map<String, Object> map2 = new HashMap<>();
//                        map2.put("userId","1");
//                        map2.put("roleId", "2");
//                        mapList.add(map1);
//                        mapList.add(map2);
//                        param = mapList;
                        List<UserRole> userRoleList = new ArrayList<>();
                        UserRole userRole1 = new UserRole(1L, 1L, 1L);
                        UserRole userRole2 = new UserRole(2L, 2L, 2L);
                        userRoleList.add(userRole1);
                        userRoleList.add(userRole2);
                        param = userRoleList;
                        mappedStatement.getBoundSql(param).getSql();
                        System.out.println("dynamicsqlsource");
                    } else if (sqlSource instanceof RawSqlSource) {
                        System.out.println("rawsqlsource");
                    } else {
                        System.out.println("unknown");
                    }
                    String sql = mappedStatement.getId() + ":" + mappedStatement.getBoundSql(param).getSql();
                    sql = sql.replace("\n", "");
                    sql = removeBreakingWhitespace(sql);
                    System.out.println(sql);
                }
            });
        }
    }
}
