package com.scd;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;

/**
 * @author James
 */
public class MyBatisLogger {

    private static Class<?>[] LOGGER_CLASS = {StdOutImpl.class, Slf4jImpl.class,
            NoLoggingImpl.class, Log4j2Impl.class, Log4jImpl.class, Jdk14LoggingImpl.class};

    public static void main(String[] args) {
        for (Class clazz : LOGGER_CLASS) {
            try {
                LogFactory.useCustomLogging(clazz);
            } catch (Exception e) {
                System.out.println("---init---logger error" + clazz.getName());
                e.printStackTrace();
            }
        }
    }
}
