package com.scd.util;

import com.scd.constant.TypeEnum;
import com.scd.model.po.TaskParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengdu
 * @date 2019/8/30.
 */
public class ReflectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    public static <T> T reflectByMap(Class<T> clazz, Map<String, Object> map) {
        T object = null;
        try {
            object = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (!methodName.startsWith("set")) {
                    continue;
                }
                String fieldName = method.getName().substring("set".length()).toLowerCase();
                if (map.containsKey(fieldName)) {
                    Parameter parameter = method.getParameters()[0];
                    Object value = parameter.getType().cast(map.get(fieldName));
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
}
