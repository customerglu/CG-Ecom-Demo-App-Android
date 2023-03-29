package com.customerglu.sdk.Modal;

import java.util.List;

public class ScreenListModal {

    List<String> activityIdList;
    List<String> embedIds;
    List<String> bannerIds;

    public ScreenListModal() {
    }

    public ScreenListModal(List<String> activityIdList, List<String> embedIds, List<String> bannerIds) {
        this.activityIdList = activityIdList;
        this.embedIds = embedIds;
        this.bannerIds = bannerIds;
    }

    public List<String> getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(List<String> activityIdList) {
        this.activityIdList = activityIdList;
    }

    public List<String> getEmbedIds() {
        return embedIds;
    }

    public void setEmbedIds(List<String> embedIds) {
        this.embedIds = embedIds;
    }

    public List<String> getBannerIds() {
        return bannerIds;
    }

    public void setBannerIds(List<String> bannerIds) {
        this.bannerIds = bannerIds;
    }
}
