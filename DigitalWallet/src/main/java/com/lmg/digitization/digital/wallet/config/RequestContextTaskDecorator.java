package com.lmg.digitization.digital.wallet.config;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.task.TaskDecorator;

public class RequestContextTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		// Right now:Web Thread context !
		// (Grab the current thread context map data)
		Map<String, String> contextMap = ThreadContext.getImmutableContext();
		return () -> {
			try {
				// Right now: @Async thread context !
				// (Restore the web thread context's data)
				ThreadContext.putAll(contextMap);
				runnable.run();
			} finally {
				ThreadContext.clearAll();
			}
		};

	}

}
