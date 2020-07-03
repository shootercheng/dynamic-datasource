package com.scd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author chengdu
 * @date 2019/6/28.
 */
@Configuration
public class ThreadPoolConfig {


    @Value("${pool.async.corePoolSize}")
    private int corePoolSize;

    @Value("${pool.async.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${pool.async.keepAliveTime}")
    private long keepAliveTime;

    @Value("${pool.async.timeUnit}")
    private TimeUnit timeUnit;

    @Value("${pool.async.blockingQueue}")
    private int blockingQueue;



    @Bean(name = "asyncPool")
    public ExecutorService createThreadPool(){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, timeUnit, new ArrayBlockingQueue(blockingQueue),
                new ThreadPoolExecutor.DiscardPolicy());
    }
}
