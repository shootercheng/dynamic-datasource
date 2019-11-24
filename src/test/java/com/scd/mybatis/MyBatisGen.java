package com.scd.mybatis;

import com.scd.util.FileUtil;
import com.scd.api.ShellRunner;
import org.apache.ibatis.io.Resources;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
public class MyBatisGen {

    private static final String TABLE_NAME_TEMPLATE = "@@@###TABLE###@@@";

    private static final String MODEL_NAME_TEMPLATE = "@@@###MODEL###@@@";

    private static final String TABLES_TEMPLATE = "@@@###TABLES###@@@";

    private static final String TABLE_TEMPLATE_STR = "<table tableName=\"@@@###TABLE###@@@\" domainObjectName=\"@@@###MODEL###@@@\" enableCountByExample=\"false\" enableUpdateByExample=\"false\" enableDeleteByExample=\"false\" " +
            "enableSelectByExample=\"false\" selectByExampleQueryId=\"false\"></table>";

    private static String[] tables = {
            "t_task_param",
            "t_test",
            "t_article"
    };

    private static String[] models = {
            "TaskParam",
            "Test",
            "Article"
    };


    public static void main(String[] args) throws Exception {
        String resource = "templates/generatorConfigTemplate.xml";
        String template = "";
        // 读取之后会关闭文件流
        try(InputStream inputStream = Resources.getResourceAsStream(MyBatisGen.class.getClassLoader(), resource)){
            template = FileUtil.readFileAll(inputStream, StandardCharsets.UTF_8.name());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (template.length() == 0){
            return ;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < tables.length; i++){
            String tablestr = TABLE_TEMPLATE_STR;
            tablestr = tablestr.replace(TABLE_NAME_TEMPLATE, tables[i]);
            tablestr = tablestr.replace(MODEL_NAME_TEMPLATE, models[i]);
            stringBuilder.append(tablestr).append("\n\t\t");
        }
        template = template.replace(TABLES_TEMPLATE, stringBuilder.toString());
        String target = "temp";
        FileUtil.makedir(target);
        String filepath = target + File.separator + "mbgeneratorConfig.xml";
//        FileUtil.writeStrtoFile(filepath, template, false);
        System.out.println("create config success ");
        String[] command = {ShellRunner.CONFIG_FILE, filepath,ShellRunner.OVERWRITE};
        ShellRunner.genCode(command);
    }
}
