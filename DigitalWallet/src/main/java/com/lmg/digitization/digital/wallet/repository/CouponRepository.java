package com.lmg.digitization.digital.wallet.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.digital.wallet.entity.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, String>{
	public Coupon findByCouponCode(String couponCode);
}
