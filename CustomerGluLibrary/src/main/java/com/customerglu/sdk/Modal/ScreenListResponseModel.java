package com.customerglu.sdk.Modal;

import java.util.ArrayList;

public class ScreenListResponseModel {
    Boolean success;
    EntryPointIdList data;

    public ScreenListResponseModel(Boolean success, EntryPointIdList data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public EntryPointIdList getData() {
        return data;
    }

    public void setData(EntryPointIdList data) {
        this.data = data;
    }

    public static class EntryPointIdList {

        PlatformList activityIdList;
        PlatformList embedIds;
        PlatformList bannerIds;

        public EntryPointIdList(PlatformList activityIdList, PlatformList embedIds, PlatformList bannerIds) {
            this.activityIdList = activityIdList;
            this.embedIds = embedIds;
            this.bannerIds = bannerIds;
        }

        public PlatformList getActivityIdList() {
            return activityIdList;
        }

        public void setActivityIdList(PlatformList activityIdList) {
            this.activityIdList = activityIdList;
        }

        public PlatformList getEmbedIds() {
            return embedIds;
        }

        public void setEmbedIds(PlatformList embedIds) {
            this.embedIds = embedIds;
        }

        public PlatformList getBannerIds() {
            return bannerIds;
        }

        public void setBannerIds(PlatformList bannerIds) {
            this.bannerIds = bannerIds;
        }

        public static class PlatformList {
            ArrayList<String> android;
            ArrayList<String> ios;

            public PlatformList(ArrayList<String> android, ArrayList<String> ios) {
                this.android = android;
                this.ios = ios;
            }

            public ArrayList<String> getAndroid() {
                return android;
            }

            public void setAndroid(ArrayList<String> android) {
                this.android = android;
            }

            public ArrayList<String> getIos() {
                return ios;
            }

            public void setIos(ArrayList<String> ios) {
                this.ios = ios;
            }
        }


    }
}
