package com.lmg.digitization.digital.wallet.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.lmg.digitization.digital.wallet.request.ConvertCardRequest;

@Service
public interface GiftCardService {
	public String generateStsToken();
	public BigDecimal consumeCards( ConvertCardRequest convertRequest, BigDecimal amount, String token) throws IOException, URISyntaxException;
	public void confirmTransaction( ConvertCardRequest convertRequest, String referenceNumber, String token) throws IOException, URISyntaxException;
	public BigDecimal getBalance(ConvertCardRequest req) throws URISyntaxException;
}
