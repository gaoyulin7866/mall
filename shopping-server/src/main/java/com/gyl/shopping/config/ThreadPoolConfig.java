package com.gyl.shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: gyl
 * @Date: 2022-11-07-下午8:06
 * @Description: 初始化线程池
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${thread.corePoolSize}")
    private Integer corePoolSize;

    @Value("${thread.maximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${thread.keepAliveTime}")
    private Integer keepAliveTime;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<Runnable>();

    @Bean(name = "executorService")
    public ExecutorService createThread(){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, blockingQueue);
    }
}
