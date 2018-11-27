package com.borrow.manage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Bean("notifyTaskExecutor")
    public TaskExecutor notifyTaskExecutor(){
        ThreadPoolTaskExecutor notifyTaskExecutor = new ThreadPoolTaskExecutor();
        notifyTaskExecutor.setCorePoolSize(20);
        notifyTaskExecutor.setMaxPoolSize(50);
        notifyTaskExecutor.setKeepAliveSeconds(180);
        notifyTaskExecutor.setQueueCapacity(100);
        notifyTaskExecutor.setRejectedExecutionHandler(
                (Runnable r, ThreadPoolExecutor executor) -> {
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().offer(r,60, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            logger.error("理财通知线程池中断:"+Thread.currentThread().getName(), e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        return notifyTaskExecutor;
    }

}
