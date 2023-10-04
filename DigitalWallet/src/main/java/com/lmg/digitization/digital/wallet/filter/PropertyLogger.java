package com.lmg.digitization.digital.wallet.filter;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class PropertyLogger {

	private String propValues;

	private static final Logger LOGGER = ESAPI.getLogger(PropertyLogger.class);

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		StringBuilder sb = new StringBuilder();
		final Environment env = event.getApplicationContext().getEnvironment();
		LOGGER.info(Logger.EVENT_SUCCESS,"====== Environment and configuration ======");
		LOGGER.info(Logger.EVENT_SUCCESS,"Active profiles: {}"+ Arrays.toString(env.getActiveProfiles()));
		final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
		StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
				.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
				.filter(prop -> !(prop.contains("credentials") || prop.contains("password"))).sorted().forEach(prop -> {
					sb.append("\n");
					sb.append(prop + " => " + env.getProperty(prop));
					LOGGER.info(Logger.EVENT_SUCCESS,"{}: {}"+ prop+" "+ env.getProperty(prop));
				});
		propValues = sb.toString();

		LOGGER.info(Logger.EVENT_SUCCESS,"===========================================");
	}

	public String getPropValues() {
		return propValues;
	}

	public void setPropValues(String propValues) {
		this.propValues = propValues;
	}

}