package com.lmg.digitization.digital.wallet.exception;

import java.lang.reflect.Method;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	
	public static final Logger LOGGER = ESAPI.getLogger(CustomAsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		LOGGER.info(Logger.EVENT_SUCCESS,"Exception Message: {}"+ throwable.getMessage());
	}

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}
}
