package com.lmg.digitization.digital.wallet.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.lmg.digitization.digital.wallet.constants.CouponConstants;
import com.lmg.digitization.digital.wallet.entity.Coupon;
import com.lmg.digitization.digital.wallet.entity.RedeemCoupon;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.CouponRepository;
import com.lmg.digitization.digital.wallet.repository.RedeemCouponRepository;
import com.lmg.digitization.digital.wallet.request.CreateCouponRequest;
import com.lmg.digitization.digital.wallet.request.RedeemCouponRequest;
import com.lmg.digitization.digital.wallet.response.CreateCouponResponse;
import com.lmg.digitization.digital.wallet.response.EmployeeResponse;
import com.lmg.digitization.digital.wallet.response.RedeemCouponResponse;


@Service
public class CouponService {
	public static final Logger logger = ESAPI.getLogger(CouponService.class.getName());
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private RedeemCouponRepository redeemCouponRepository;

	@Autowired
	private AppProperties appProperties;
	
	public CreateCouponResponse createCoupon(String storeId, CreateCouponRequest request) {
		CreateCouponResponse response = new CreateCouponResponse();
		Coupon coupon = couponRepository.findByCouponCode(request.getCouponCode());
		if (null != coupon) {
			throw new DigitizationException(CouponConstants.ALREADYEXISTS,
					CouponConstants.ALREADYEXISTS);
		}
		coupon=this.insertCoupon(storeId, request);
		BeanUtils.copyProperties(coupon, response);
		return response;
	}
	
	public Coupon insertCoupon(String storeId, CreateCouponRequest request) {
		Coupon coupon = new Coupon();
		try {
			BeanUtils.copyProperties(request, coupon);
			coupon.setStoreId(storeId);
			coupon.setRedeemedAmount(BigDecimal.ZERO);
			coupon.setCreateDate(LocalDateTime.now());
			couponRepository.save(coupon);
			coupon=couponRepository.findByCouponCode(request.getCouponCode());
		}

		catch (Exception ex) {
			
			throw new DigitizationException(CouponConstants.FAILURE, ex.getMessage(), ex);
		}
		return coupon;
	}
	
	public RedeemCouponResponse redeemCoupon(String couponCode, RedeemCouponRequest request) {
		
		Coupon coupon = couponRepository.findByCouponCode(couponCode);
		this.validate(coupon, request);
		return this.updateCoupon(couponCode, request, coupon);
	}
	public RedeemCouponResponse updateCoupon(String couponCode, RedeemCouponRequest request, Coupon coupon) {
		try {
			coupon.setRedeemedAmount(coupon.getRedeemedAmount().add(request.getRedeemedAmount()));
			couponRepository.save(coupon);
		}
		catch (Exception ex) {
			throw new DigitizationException(CouponConstants.FAILURE, ex.getMessage(), ex);
		}
		
		return this.insertRedeem(couponCode, request, coupon);
	}
	public RedeemCouponResponse insertRedeem(String couponCode, RedeemCouponRequest request, Coupon coupon) {
		RedeemCouponResponse response= new RedeemCouponResponse();
	
		RedeemCoupon redeemCoupon = new RedeemCoupon();
		try {
			BeanUtils.copyProperties(request, redeemCoupon);
			redeemCoupon.setCouponCode(couponCode);
			redeemCoupon.setCreateDate(LocalDateTime.now());
			redeemCouponRepository.save(redeemCoupon);
		}
		catch (Exception ex) {
			throw new DigitizationException(CouponConstants.FAILURE, ex.getMessage(), ex);
		}
		BeanUtils.copyProperties(coupon, response);
		response.setCouponCode(couponCode);
		response.setStatus("successly consumed");
		response.setBalance(coupon.getDiscountAmount().subtract(coupon.getRedeemedAmount()));
		return response;
	}
public RedeemCouponResponse validateCoupon(String couponCode, RedeemCouponRequest request) {
		
	    RedeemCouponResponse response=new RedeemCouponResponse();
		Coupon coupon = couponRepository.findByCouponCode(couponCode);
		this.validate(coupon, request);
		BeanUtils.copyProperties(coupon, response);
		response.setCouponCode(couponCode);
		response.setStatus("successly validated");
		response.setBalance(coupon.getDiscountAmount().subtract(coupon.getRedeemedAmount()));
		return response;
	}
	public boolean validate(Coupon coupon,RedeemCouponRequest request)
		{
		if (null == coupon) {
			throw new DigitizationException(CouponConstants.INVALID,
					CouponConstants.INVALID);
		}
		if(coupon.getExpiryDate().isBefore(LocalDate.now()))
		{
			throw new DigitizationException(CouponConstants.EXPIRED,
					CouponConstants.EXPIRED);
		}
		if(!coupon.getCurrency().equals(request.getCurrency()))
		{
			throw new DigitizationException(CouponConstants.INVALIDCURRENCY,
					CouponConstants.INVALIDCURRENCY);
		}
		if(coupon.getDiscountAmount().subtract(coupon.getRedeemedAmount()).equals(BigDecimal.ZERO))
		{
			throw new DigitizationException(CouponConstants.REDEEMED,
					CouponConstants.REDEEMED);
		}
		if(coupon.getDiscountAmount().subtract(coupon.getRedeemedAmount()).compareTo(request.getRedeemedAmount())<1)
		{
			throw new DigitizationException(CouponConstants.INSUFFICIENTBALANCE,
					CouponConstants.INSUFFICIENTBALANCE);
		}
		return true;
	}
public EmployeeResponse validateEmployee(String employeeCode) {
		
	String url = appProperties.getEmplopyeeValidationUrl().concat(employeeCode);
    String userName = appProperties.getEmployeeValidationUserName();
    String password = appProperties.getEmployeeValidationPassword();  
    HttpGet request = new HttpGet(url);
    String auth = userName + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(
    auth.getBytes(StandardCharsets.ISO_8859_1));
    String authHeader = "Basic " + new String(encodedAuth);
    request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
	 HttpClient client = HttpClientBuilder.create().build();
	 HttpResponse response;
	 EmployeeResponse response1=new EmployeeResponse();
	 try {
		response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==200)
		{
			response1.setEmployeeCode(employeeCode);
			response1.setIsActive("Y");
		}
		else
			throw new DigitizationException(CouponConstants.EMPLOYEENOTFOUND,
					CouponConstants.EMPLOYEENOTFOUND);
		
		} catch (IOException e) {
			throw new DigitizationException(CouponConstants.EMPLOYEENOTFOUND,
				e.getMessage());
		}
			return response1;
		}
}
