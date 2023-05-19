package com.customerglu.sdk.Modal;

import java.util.Objects;

public class Campaigns {

    public Campaigns(String campaignId) {
        this.campaignId = campaignId;
    }

    public Campaigns(String campaignId, String url, String type, String status, RewardModel.Banner banner) {
        this.campaignId = campaignId;
        this.url = url;
        this.type = type;
        this.status = status;
        this.banner = banner;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RewardModel.Banner getBanner() {
        return banner;
    }

    public void setBanner(RewardModel.Banner banner) {
        this.banner = banner;
    }

    String campaignId;
    String url;
    String type;
    String status;
    RewardModel.Banner banner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaigns)) return false;
        Campaigns campaigns = (Campaigns) o;
        return Objects.equals(getCampaignId(), campaigns.getCampaignId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCampaignId(), getUrl(), getType(), getStatus(), getBanner());
    }
}
