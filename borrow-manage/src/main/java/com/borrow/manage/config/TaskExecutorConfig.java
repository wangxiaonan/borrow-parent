package com.risk.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxn on 2018/7/4
 */
@Configuration
public class TaskExecutorConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Bean("collTaskExecutor")
    public TaskExecutor collTaskExecutor(){
        ThreadPoolTaskExecutor collTaskExecutor = new ThreadPoolTaskExecutor();
        collTaskExecutor.setCorePoolSize(50);
        collTaskExecutor.setMaxPoolSize(100);
        collTaskExecutor.setKeepAliveSeconds(180);
        collTaskExecutor.setQueueCapacity(1000);
        collTaskExecutor.setRejectedExecutionHandler(
                (Runnable r, ThreadPoolExecutor executor) -> {
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().offer(r,60, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            logger.error("采集线程池中断:"+Thread.currentThread().getName(), e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        return collTaskExecutor;
    }
    @Bean("dataTaskExecutor")
    public TaskExecutor dataTaskExecutor(){
        ThreadPoolTaskExecutor dataTaskExecutor = new ThreadPoolTaskExecutor();
        dataTaskExecutor.setCorePoolSize(10);
        dataTaskExecutor.setMaxPoolSize(20);
        dataTaskExecutor.setKeepAliveSeconds(180);
        dataTaskExecutor.setQueueCapacity(1000);
        dataTaskExecutor.setRejectedExecutionHandler(
                (Runnable r, ThreadPoolExecutor executor) -> {
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().offer(r,60, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            logger.error("数据线程池中断:"+Thread.currentThread().getName(), e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        return dataTaskExecutor;
    }
}
