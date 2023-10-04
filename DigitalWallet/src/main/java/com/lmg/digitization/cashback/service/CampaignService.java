package com.lmg.digitization.cashback.service;

import com.lmg.digitization.cashback.request.CreateCampaignRequest;
import com.lmg.digitization.cashback.response.CreateCampaignResponse;

public interface CampaignService {

	CreateCampaignResponse saveCampaign(String channel, CreateCampaignRequest ccr);

	CreateCampaignResponse updateCampaign(long promoId, CreateCampaignRequest ccr);
}
