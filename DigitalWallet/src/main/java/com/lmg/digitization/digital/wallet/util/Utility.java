package com.lmg.digitization.digital.wallet.util;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.OTPModel;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;

import com.lmg.digitization.digital.wallet.repository.OTPRepository;
import com.lmg.digitization.digital.wallet.request.CreateDigitalWalletRequest;


@Repository
public class Utility {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private OTPRepository otpRepository;
	public static final Logger LOGGER = ESAPI.getLogger(Utility.class);
	public String generateWalletId(CreateDigitalWalletRequest req) {
		String channel = req.getSourceChannel().name().substring(0, 1);
		String requesttype = req.getReferenceType();
		String territoryid = req.getTerritoryCode();
		String storecode = req.getStoreCode();
		LocalDate businessdate = req.getTransactionDate();
		String conceptcode = req.getConceptCode();
		String costcentre = req.getCostCentre();
		String walletid = null;
		String channelid = ((channel.equals("H")) ? "1" : "0");
		if (channel.equals("P")) {
			walletid = this.walletIdGenerator(channelid, requesttype, territoryid, storecode, conceptcode,
					this.getDDMMYY(businessdate), 9);
		} else {
			walletid = this.walletIdGenerator(channelid, requesttype, territoryid, conceptcode, costcentre,
					this.getDDMMYY(businessdate), 9);
		}
		return walletid;
	}

	public String walletIdGenerator(String channel, String requesttype, String territoryid, String storecode,
									String registerid, String businessdate, int noofcharacters) {
		String walletId = channel + requesttype + territoryid + storecode + registerid + businessdate;
		String uuid = ((UUID.randomUUID().toString()).replace("-", "").substring(0, noofcharacters)).toUpperCase();
		return walletId + uuid;
	}

	public String getDateAndTime() {
		Date dateTime = new Date();
		String dateTimeFormat = DigitalWalletConstants.DATE_FORMAT;
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		return dateFormat.format(dateTime);

	}

	public String getDate() {
		Date dateTime = new Date();
		String dateTimeFormat = "yyyy-MM-dd";
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		return dateFormat.format(dateTime);

	}

	public LocalDateTime getLocalDateTime(String timeZone) {
		Instant nowUtc = Instant.now();
		ZoneId localZone = ZoneId.of(timeZone != null && !timeZone.isEmpty() ? timeZone : "Asia/Dubai");
		ZonedDateTime localZoneDT = ZonedDateTime.ofInstant(nowUtc, localZone);
		return localZoneDT.toLocalDateTime();
	}

	public LocalDateTime getLocalDateTimeFromString(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DigitalWalletConstants.DATE_FORMAT);
		return LocalDateTime.parse(date, formatter);

	}

	public LocalDate getLocalDateFromString(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DigitalWalletConstants.DATE_FORMAT);
		return LocalDate.parse(date, formatter);

	}
	public String getDDMMYY(LocalDate businessdate) {
		return businessdate.format(DateTimeFormatter.ofPattern("ddMMyy"));

	}

	public static String getRandomNumberString() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
		return String.format("%06d",secureRandomGenerator.nextInt(999999));
	}

	private DateTimeFormatter dcnBusinessDateFormat = DateTimeFormatter.ofPattern("yyMMdd");

	private DateTimeFormatter timeUniqueFormat = DateTimeFormatter.ofPattern("HHmmss");

	public String generateCreditNoteNumber(String mode, String requestType, String territory, String concept,
										   String storeId, LocalDate businessDay) {
		StringBuilder creditNoteNumber = new StringBuilder();
		SecureRandom secureRandomGenerator;
		try {
			secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
			creditNoteNumber.append(mode).append(requestType).append(territory).append(concept).append(storeId)
					.append(dcnBusinessDateFormat.format(businessDay)).append(timeUniqueFormat.format(LocalDateTime.now()))
					.append(secureRandomGenerator.nextInt(89) + 10);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			LOGGER.error(Logger.EVENT_FAILURE,"Error occured {}"+ e.getMessage());
		}
		return creditNoteNumber.toString();
	}

	public String getStingFromLocalDateTime(LocalDateTime ldt) {
		LocalDateTime currentDateTime = ldt;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DigitalWalletConstants.DATE_FORMAT);
		return currentDateTime.format(formatter);

	}

	public String getEndDate(String startDate, int enddays) {
		LocalDate ldate = LocalDate.parse(startDate);

		return ldate.plusDays(enddays).toString();
	}

	public LocalDate getExpiryDay(int expiryPeriod, LocalDate datetime, int expirydate) {
		LocalDate ldt = datetime;
		if(expiryPeriod>0) {
			return ldt.plusDays(expiryPeriod);
		}
		else{
			return ldt.plusDays(expirydate);
		}

	}

	public Date stringToDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-mm-dd").parse(date);

		} catch (Exception ex) {
			throw new DigitizationException("Faliure", "Failed to parse date");
		}
	}

	public boolean compareDates(String d1, String d2) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(d1);
			Date date2 = sdf.parse(d2);

			if (date1.before(date2)) {
				return true;
			}

		} catch (Exception ex) {
			throw new DigitizationException("Faliure", "Failed to parse date");
		}
		return false;
	}

	public boolean compareTimes(String time1, String time2) {
		LocalTime t1 = LocalTime.parse(time1);
		LocalTime t2 = LocalTime.parse(time2);

		if (t1.isBefore(t2)) {
			return true;
		}
		return false;
	}

	public boolean validateOTP(String shukranId, String otpType, String otp, String transactonId) {
		OTPModel model = otpRepository.findByShukranIdAndOtpTypeAndOtpAndTransactionIdAndIsExpired(shukranId, otpType,
				otp, transactonId, false);
		if (null == model) {
			throw new DigitizationException("Invalid OTP", "ERR_INVALID_OTP");

		} else {
			if (model.getOtpExpiration().isBefore(LocalDateTime.now())) {
				throw new DigitizationException("OTP expired", "ERR_INVALID_OTP");

			} else {
				model.setIsValidated(true);
				model.setValidationDate(LocalDateTime.now());
				model.setIsExpired(true);
				model.setModifiedDate(LocalDateTime.now());
				otpRepository.save(model);
			}
		}
		return true;
	}

}
