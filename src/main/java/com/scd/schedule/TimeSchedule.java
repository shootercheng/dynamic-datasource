package com.scd.schedule;

import com.scd.model.po.TaskParam;
import com.scd.service.TaskParamService;
import com.scd.task.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
@Component
@Configuration
@EnableScheduling
public class TimeSchedule implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeSchedule.class);

    @Autowired
    private TaskParamService taskParamService;

    // 每隔 5秒 执行一次
    private static final String defaultTime = "0/5 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        scheduledTaskRegistrar.addTriggerTask(new TimerTask("timetask"),
                triggerContext -> {
                    String cronExpression = taskParamService.queryScheduleEx(1);
                    if (StringUtils.isEmpty(cronExpression)){
                        cronExpression = defaultTime;
                    }
                    return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
        });
    }
}
