package com.marx.config.timed;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务
 */

@Configuration
@EnableScheduling
public class TimedTask implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        /* 每24小时执行一次该任务（删除临时文件） */
        taskRegistrar.addCronTask(new myTask(), "0 0 0 1/1 * ?");
    }

}
