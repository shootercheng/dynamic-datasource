package com.scd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
@Service
public class DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

    private volatile boolean isRunning = false;

    public void doHandler(){
        if(!isRunning) {
            try {
                LOGGER.info("start handler---------------");
                isRunning = true;
                // do SomeThing
                Thread.sleep(10000);
                LOGGER.info("end handler----------------");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        } else {
            LOGGER.info("handler is running-----------------");
        }
    }
}
