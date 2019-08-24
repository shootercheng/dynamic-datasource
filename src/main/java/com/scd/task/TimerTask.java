package com.scd.task;

import com.scd.service.DataService;
import com.scd.service.TaskParamService;
import com.scd.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
public class TimerTask extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimerTask.class);

    public TimerTask(String name){
        super.setName(name);
    }

    @Override
    public void run(){
        TaskParamService taskParamService = BeanUtil.getBean(TaskParamService.class);
        DataService dataService = BeanUtil.getBean(DataService.class);
        LOGGER.info("time task execute time {}", LocalDateTime.now().toLocalTime());
        dataService.doHandler();
    }
}
