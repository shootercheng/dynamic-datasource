package com.scd.sql;

import com.scd.constant.TypeEnum;
import com.scd.mapper.UserRoleMapper;
import com.scd.model.po.TaskParam;
import com.scd.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

/**
 * @author chengdu
 * @date 2019/9/1.
 */
public class ReflectData {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 测试数据
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T reflectTestData(Class<T> clazz) {
        T object = null;
        if (clazz == null){
            return object;
        }
        try {
            object = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                Class<?>[] clazzs = method.getParameterTypes();
                if (methodName.startsWith("set") && clazzs.length > 0) {
                    String clazzName = clazzs[0].getName();
                    Object value = null;
                    if (TypeEnum.Integer.type().equals(clazzName)){
                        value = 1;
                    } else if (TypeEnum.Long.type().equals(clazzName)){
                        value = 1L;
                    } else if (TypeEnum.String.type().equals(clazzName)){
                        value = "1";
                    } else if (TypeEnum.Double.type().equals(clazzName)){
                        value = 1D;
                    } else if (TypeEnum.Date.type().equals(clazzName)){
                        value = new Date();
                    } else {
                        LOGGER.error("unknown type {}", clazzName);
                    }
                    method.invoke(object, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void main(String[] args) throws Exception {
        VoTest voTest = reflectTestData(VoTest.class);
        System.out.println(voTest);
        Method[] methods = UserRoleMapper.class.getDeclaredMethods();
        for (Method method : methods){
            System.out.println(method.getName());
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters){
                Class<?> clazz = parameter.getType();
                System.out.println(clazz);
//                clazz.newInstance();
            }
            Class<?>[] classes = method.getParameterTypes();
            for (Class clazz : classes){
                System.out.println(clazz);
            }
        }
    }
}
