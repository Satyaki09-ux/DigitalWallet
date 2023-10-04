package com.lmg.digitization.digital.wallet.filter;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.APPLICATION_NAME;
import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.CHANNEL;
import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.CONCEPT;
import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.STORE;
import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.UNIQUE_REFERENCE_CODE;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LogEnhancerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		MDC.put(UNIQUE_REFERENCE_CODE, httpRequest.getHeader(UNIQUE_REFERENCE_CODE));
		MDC.put(CONCEPT, httpRequest.getHeader(CONCEPT));
		MDC.put(STORE, httpRequest.getHeader(STORE));
		MDC.put(CHANNEL, httpRequest.getHeader(CHANNEL));
		MDC.put("application", APPLICATION_NAME);
		chain.doFilter(request, response);
	}

}
