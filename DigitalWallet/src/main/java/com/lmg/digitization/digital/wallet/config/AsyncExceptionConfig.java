package com.lmg.digitization.digital.wallet.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lmg.digitization.digital.wallet.exception.CustomAsyncExceptionHandler;

@Configuration
@EnableAsync
public class AsyncExceptionConfig extends AsyncConfigurerSupport {

	@Autowired
	private CustomAsyncExceptionHandler customeAsyncExceptionHandler;

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(200);
		executor.setQueueCapacity(5000);
		executor.setMaxPoolSize(30000);
		executor.setThreadNamePrefix("asyn-dcn-task-thread-");
		executor.setTaskDecorator(new RequestContextTaskDecorator());
		executor.initialize();
		return executor;
	}

	@Override
	public CustomAsyncExceptionHandler getAsyncUncaughtExceptionHandler() {
		return customeAsyncExceptionHandler;
	}
}
