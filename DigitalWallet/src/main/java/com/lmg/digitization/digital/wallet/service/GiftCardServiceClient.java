package com.lmg.digitization.digital.wallet.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.http.client.utils.URIBuilder;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;

import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.request.ConvertCardRequest;
import com.lmg.digitization.digital.wallet.request.OglobaRedeemRequest;
import com.lmg.digitization.digital.wallet.response.OglobaResponse;
import com.lmg.digitization.digital.wallet.constants.CardConstants;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GiftCardServiceClient implements GiftCardService{
	
	public static final Logger LOGGER = ESAPI.getLogger(GiftCardServiceClient.class);

	@Autowired
	private AppProperties appProperties;
	@Autowired
	private RestTemplate restTemplate;
	
	public String generateStsToken()
	{	
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	    map.add("client_id",appProperties.getStsClientId());
	    map.add("redirect_uri",appProperties.getStsRedirectUri());
	    map.add("grant_type", appProperties.getStsGrantType());
	    map.add("client_secret", appProperties.getStsClientSecret());
	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
	    ResponseEntity<OglobaResponse> response =
	    restTemplate.exchange(appProperties.getStsToeknGenerationUrl(),
	                              HttpMethod.POST,
	                              entity,
	                              OglobaResponse.class); 
	    OglobaResponse res=response.getBody();
	    if(res!=null) {
	    return res.getToken();
	    }
	    else
	    	throw new DigitizationException("Token Error",
					"Token could not be generated");
	}
	
	public BigDecimal consumeCards( ConvertCardRequest convertRequest, BigDecimal amount, String token) throws URISyntaxException
	{
		try {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set(CardConstants.AUTH, CardConstants.BEARERLITERALS.concat(token));
	    headers.set(CardConstants.XCLIENT, appProperties.getxIbmClientId());
	    headers.set("Accept", CardConstants.ACCEPTTYPE);
        Date dateTime = new Date();
		String dateTimeFormat = "yyyyMMddHHmmss";
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		OglobaRedeemRequest request=new OglobaRedeemRequest();
		request.setAmount(amount);
		request.setCashierId("System");
		request.setGenCode("");
		request.setMerchantId("LMGOMNI");
		request.setMobieNumber("");
		request.setNote("");
		request.setReason("");
		request.setTerminalId("System");
		request.setTrack2Data("");
		request.setTransactionNumber(dateFormat.format(dateTime));
		request.setValidityDate("");
		HttpEntity<OglobaRedeemRequest> entity = new HttpEntity<>(request, headers);
        String url=appProperties.getOglobaCardRedemptionUrl().replace(CardConstants.CARDLITERALS,convertRequest.getCardNumber());
        final URI uri = UriComponentsBuilder.newInstance().build(url);
        ResponseEntity<OglobaResponse> response =
	    restTemplate.exchange(uri,
	                              HttpMethod.POST,
	                              entity,
	                              OglobaResponse.class);
        OglobaResponse res=response.getBody();
				if(res!=null) {
				if (res.isAck())
					{
					    confirmTransaction( convertRequest, res.getReferenceNumber(),token);
						return amount;
					}
				}
				else
					throw new DigitizationException(CardConstants.ERROR,"Failed to Consume cards");
		
		}
		catch(IOException ex) {
			throw new DigitizationException(CardConstants.ERROR,
					ex.getMessage());
		}
		
		return new BigDecimal("0");
		
	}
	
	public void confirmTransaction( ConvertCardRequest convertRequest, String referenceNumber, String token) throws IOException, URISyntaxException
	{	
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set(CardConstants.AUTH, CardConstants.BEARERLITERALS.concat(token));
		    headers.set(CardConstants.XCLIENT, appProperties.getxIbmClientId());
		    headers.set("Accept", CardConstants.ACCEPTTYPE);
		    OglobaRedeemRequest request=new OglobaRedeemRequest();
			request.setCashierId("System");
			request.setMerchantId("LMGOMNI");
			request.setNote("");
			request.setTerminalId("System");
			request.setReferenceNumber(referenceNumber);
			HttpEntity<OglobaRedeemRequest> entity = new HttpEntity<>(request, headers);
	        String url=appProperties.getOglobaCardConfirmationUrl().replace(CardConstants.CARDLITERALS,convertRequest.getCardNumber());
		    final URI uri = UriComponentsBuilder.newInstance().build(url);
	        ResponseEntity<OglobaResponse> response =
		    restTemplate.exchange(uri,
		                              HttpMethod.POST,
		                              entity,
		                              OglobaResponse.class);
	        OglobaResponse res=response.getBody();
	        if(res!=null) {
					if (!res.isAck())
						throw new DigitizationException(CardConstants.ERROR, res.getError());
	        }
	        else
	        	throw new DigitizationException(CardConstants.ERROR, "Can not confirm");
		}
			
		
	
	public BigDecimal getBalance(ConvertCardRequest req) throws URISyntaxException {
		String token=generateStsToken();	
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add(CardConstants.AUTH, CardConstants.BEARERLITERALS.concat(token));
	    headers.add(CardConstants.XCLIENT, appProperties.getxIbmClientId());
	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
		final String URI = appProperties.getOglobaCardEnquiryUrl().replace(CardConstants.CARDLITERALS, req.getCardNumber());
	    ResponseEntity<OglobaResponse> response =
	    restTemplate.exchange(URI, HttpMethod.GET, entity, OglobaResponse.class);
	    OglobaResponse res=response.getBody();
	    
		if(res!=null) {	
			if(!res.isAck())
				throw new DigitizationException(CardConstants.ERROR,
					res.getError());
		if(res.getBalance().compareTo(BigDecimal.ZERO)==0)
			throw new DigitizationException(CardConstants.ERROR,
					"THERE IS NO MONEY LEFT IN THE CARD");
		if(!res.getCurrency().equalsIgnoreCase(req.getBaseCurrency()))
			throw new DigitizationException(CardConstants.ERROR,
					"CURRENCY DOES NOT MATCH");
		return consumeCards(req, res.getBalance(), token);
		}
		return new BigDecimal("0.00");
	}

	
	
}
