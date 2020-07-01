package com.scd.service.impl;

import com.alibaba.ttl.TtlRunnable;
import com.scd.controller.ThreadLocalTestController;
import com.scd.service.ThreadLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 由于使用线程池 复用线程，每次获取的可能是相同的值
 * @author James
 */
@Service
public class ThreadLocalServiceImpl implements ThreadLocalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalServiceImpl.class);

    private static final Executor THREAD_POOL = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10));

    @Override
    public void asyncTest() {
        THREAD_POOL.execute(() -> {
            String dbCode = ThreadLocalTestController.THREAD_LOCAL.get();
            LOGGER.info("child thred db code is {}", dbCode);
        });
    }

    @Override
    public void transmittTest() {
        THREAD_POOL.execute(TtlRunnable.get(() -> {
            String dbCode = ThreadLocalTestController.TRANAMITT_THREAD_LOCAL.get();
            LOGGER.info("child thred db code is {}", dbCode);
        }));
    }
}
