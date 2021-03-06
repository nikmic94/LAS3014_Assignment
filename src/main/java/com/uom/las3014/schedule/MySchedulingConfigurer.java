package com.uom.las3014.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Handles the {@link ThreadPoolTaskScheduler} of the {@link Scheduled} tasks
 */
@Configuration
public class MySchedulingConfigurer implements SchedulingConfigurer {
    @Value("${com.uom.las3014.scheduling.thread.pool.size}")
    private int threadPoolSize;

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolSize);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}