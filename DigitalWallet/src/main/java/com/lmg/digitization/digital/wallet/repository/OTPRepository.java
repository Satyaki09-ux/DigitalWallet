package com.lmg.digitization.digital.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.digital.wallet.entity.OTPModel;

public interface OTPRepository extends JpaRepository<OTPModel,String>{
	OTPModel findByShukranIdAndOtpTypeAndOtpAndTransactionIdAndIsExpired(String shukranId, String otpType, String otp, String transactionId, boolean expired);
	OTPModel findByShukranIdAndOtpTypeAndTransactionIdAndIsExpired(String shukranId, String otpType, String transactionId, boolean expired);
}
