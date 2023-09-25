package com.customerglu.sdk.Modal;

import java.util.Map;

public class ProgramFilter {

    Map<String, Object> campaignId;

    public ProgramFilter() {

    }

    public ProgramFilter(Map<String, Object> campaignId) {
        this.campaignId = campaignId;
    }


    public Map<String, Object> getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Map<String, Object> campaignId) {
        this.campaignId = campaignId;
    }
}


