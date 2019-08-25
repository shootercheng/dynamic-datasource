package com.scd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
@Service
public class DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

    private volatile boolean isRunning = false;

    private static final Lock lock = new ReentrantLock();

    public void doHandler(){
        if (!isRunning) {
            lock.lock();
            if (!isRunning) {
                LOGGER.info("start handler---------------");
                isRunning = true;
                lock.unlock();
                try {
                    // do SomeThing
                    Thread.sleep(10000);
                    LOGGER.info("end handler----------------");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isRunning = false;
                }
            } else {
                lock.unlock();
                LOGGER.info("handler is running-----------------");
            }
        } else {
            LOGGER.info("handler is running-----------------");
        }
    }
}
