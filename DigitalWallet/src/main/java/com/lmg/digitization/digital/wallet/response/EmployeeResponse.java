package com.lmg.digitization.digital.wallet.response;



import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeResponse {

	@JsonProperty("employee_code")
	private String employeeCode;
	
	@JsonProperty("is_active")
	private String isActive;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}

