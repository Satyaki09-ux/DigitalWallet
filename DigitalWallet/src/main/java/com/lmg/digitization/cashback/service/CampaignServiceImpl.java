package com.lmg.digitization.cashback.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmg.digitization.cashback.constants.PromocashConstants;
import com.lmg.digitization.cashback.entity.CashbackCampaignDetails;
import com.lmg.digitization.cashback.repository.CashbackCampaignDetailsRepository;
import com.lmg.digitization.cashback.request.CreateCampaignRequest;
import com.lmg.digitization.cashback.response.CreateCampaignResponse;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.cashback.exception.CampaignException;
import com.lmg.digitization.digital.wallet.util.Utility;

@Service
public class CampaignServiceImpl implements CampaignService {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private Utility utility;

	@Autowired
	private CashbackCampaignDetailsRepository lmgCampaignDetailsRepository;

	@Override
	public CreateCampaignResponse saveCampaign(String channel, CreateCampaignRequest ccr) {

		if (ccr.getDateTo() == null || ccr.getDateTo().isEmpty()) {
			ccr.setDateTo(utility.getEndDate(ccr.getDateFrom(), appProperties.getEndDate()));
		}

		if (!utility.compareDates(ccr.getDateFrom(), ccr.getDateTo())) {
			throw new CampaignException(PromocashConstants.INVALID_END_DATE,
					PromocashConstants.ENDDATE_LESSTHAN_STARTDATE);
		}

		CreateCampaignResponse response = new CreateCampaignResponse();
		CashbackCampaignDetails entity = new CashbackCampaignDetails();
		try {
			entity.setChannel(channel);
			entity.setCampaignDesc(ccr.getCampaignDesc());
			entity.setIdCon(ccr.getIdCon());
			entity.setIdCdGeo(ccr.getIdCdGeo());
			entity.setIdStrRT(ccr.getIdStrRT());
			entity.setFlHrsHappy(ccr.getFlHrsHappy());
			entity.setLvlAplPrm(ccr.getLvlAplPrm());
			entity.setLvlElPrm(ccr.getLvlElPrm());
			entity.setFcValueTh(ccr.getFcValueTh());
			entity.setDptSrc(ccr.getDptSrc());
			entity.setDptTrgt(ccr.getDptTrgt());
			entity.setIdConEl(ccr.getIdConEl());
			entity.setIdConSrc(ccr.getIdConSrc());
			entity.setIsPerc(ccr.getIsPerc());
			entity.setDscVal(ccr.getDscVal());
			entity.setCtType(ccr.getCtType());
			entity.setIdPrgp(ccr.getIdPrgp());
			entity.setFlThresItmDisc(ccr.getFlThresItmDisc());
			entity.setFlPromItmDisc(ccr.getFlPromItmDisc());
			entity.setFlAmtMulAlw(ccr.getFlAmtMulAlw());
			entity.setDateFrom(utility.stringToDate(ccr.getDateFrom()));
			entity.setDateTo(utility.stringToDate(ccr.getDateTo()));
			entity.setTsCrtRcrd(LocalDateTime.now());
			entity.setTsStart(LocalTime.parse(ccr.getTsStart()));
			entity.setTsEnd(LocalTime.parse(ccr.getTsEnd()));
			entity.setIdEmployee(ccr.getIdEmployee());
			entity.setFlOnlyDiscount(ccr.getFlOnlyDiscount());
			lmgCampaignDetailsRepository.save(entity);
			response.setMessage(PromocashConstants.SUCCESS);
			response.setCampaignId(entity.getIdProm());
			response.setDateFrom(entity.getDateFrom().toString());
			response.setDateTo(entity.getDateTo().toString());
			response.setTsStart(entity.getTsStart().toString());
			response.setTsEnd(entity.getTsEnd().toString());
			response.setTsCrtRcrd(utility.getStingFromLocalDateTime(entity.getTsCrtRcrd()));
			BeanUtils.copyProperties(entity, response);
			return response;
		} catch (Exception ex) {
			throw new CampaignException(PromocashConstants.FAILURE, PromocashConstants.FAILED_TO_CREATE_CAMPAIGN);
		}
	}

	@Override
	public CreateCampaignResponse updateCampaign(long promoId, CreateCampaignRequest ccr) {
		CreateCampaignResponse response = new CreateCampaignResponse();
		CashbackCampaignDetails entity = lmgCampaignDetailsRepository.getById(promoId);
		try {
			entity.setChannel(ccr.getChannel());
			entity.setCampaignDesc(ccr.getCampaignDesc());
			entity.setIdCon(ccr.getIdCon());
			entity.setIdCdGeo(ccr.getIdCdGeo());
			entity.setIdStrRT(ccr.getIdStrRT());
			entity.setFlHrsHappy(ccr.getFlHrsHappy());
			entity.setLvlAplPrm(ccr.getLvlAplPrm());
			entity.setLvlElPrm(ccr.getLvlElPrm());
			entity.setFcValueTh(ccr.getFcValueTh());
			entity.setDptSrc(ccr.getDptSrc());
			entity.setDptTrgt(ccr.getDptTrgt());
			entity.setIdConEl(ccr.getIdConEl());
			entity.setIdConSrc(ccr.getIdConSrc());
			entity.setIsPerc(ccr.getIsPerc());
			entity.setDscVal(ccr.getDscVal());
			entity.setCtType(ccr.getCtType());
			entity.setIdPrgp(ccr.getIdPrgp());
			entity.setFlThresItmDisc(ccr.getFlThresItmDisc());
			entity.setFlPromItmDisc(ccr.getFlPromItmDisc());
			entity.setFlAmtMulAlw(ccr.getFlAmtMulAlw());
			entity.setDateFrom(utility.stringToDate(ccr.getDateFrom()));
			entity.setDateTo(utility.stringToDate(ccr.getDateTo()));
			entity.setTsCrtRcrd(LocalDateTime.now());
			entity.setTsStart(LocalTime.parse(ccr.getTsStart()));
			entity.setTsEnd(LocalTime.parse(ccr.getTsEnd()));
			entity.setIdEmployee(ccr.getIdEmployee());
			entity.setFlOnlyDiscount(ccr.getFlOnlyDiscount());
			lmgCampaignDetailsRepository.save(entity);
			response.setMessage(PromocashConstants.SUCCESS);
			response.setCampaignId(entity.getIdProm());
			response.setDateFrom(entity.getDateFrom().toString());
			response.setDateTo(entity.getDateTo().toString());
			response.setTsStart(entity.getTsStart().toString());
			response.setTsEnd(entity.getTsEnd().toString());
			response.setTsCrtRcrd(utility.getStingFromLocalDateTime(entity.getTsCrtRcrd()));
			BeanUtils.copyProperties(entity, response);
			return response;
		} catch (Exception ex) {
			throw new CampaignException(PromocashConstants.FAILURE, PromocashConstants.FAILED_TO_UPDATE_CAMPAIGN);
		}
	}

}
