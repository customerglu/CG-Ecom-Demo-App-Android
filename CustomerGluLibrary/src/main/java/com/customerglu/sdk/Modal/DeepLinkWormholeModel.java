package com.customerglu.sdk.Modal;

public class DeepLinkWormholeModel {

    String message;
    Boolean success;
    DeepLinkData data;


    public DeepLinkWormholeModel(String message, Boolean success, DeepLinkData deepLinkData) {
        this.message = message;
        this.success = success;
        this.data = deepLinkData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DeepLinkData getDeepLinkData() {
        return data;
    }

    public void setDeepLinkData(DeepLinkData deepLinkData) {
        this.data = deepLinkData;
    }

    public static class DeepLinkData {
        DeepLinkContent content;
        DeepLinkContainer container;
        Boolean anonymous;

        public DeepLinkData(DeepLinkContent content, DeepLinkContainer container, Boolean anonymous) {
            this.content = content;
            this.container = container;
            this.anonymous = anonymous;
        }

        public DeepLinkContent getContent() {
            return content;
        }

        public void setContent(DeepLinkContent content) {
            this.content = content;
        }

        public DeepLinkContainer getContainer() {
            return container;
        }

        public void setContainer(DeepLinkContainer container) {
            this.container = container;
        }

        public Boolean getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(Boolean anonymous) {
            this.anonymous = anonymous;
        }

        public static class DeepLinkContainer {
            String type;
            Integer absoluteHeight;
            Integer relativeHeight;

            public DeepLinkContainer(String type, Integer absoluteHeight, Integer relativeHeight) {
                this.type = type;
                this.absoluteHeight = absoluteHeight;
                this.relativeHeight = relativeHeight;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getAbsoluteHeight() {
                return absoluteHeight;
            }

            public void setAbsoluteHeight(Integer absoluteHeight) {
                this.absoluteHeight = absoluteHeight;
            }

            public Integer getRelativeHeight() {
                return relativeHeight;
            }

            public void setRelativeHeight(Integer relativeHeight) {
                this.relativeHeight = relativeHeight;
            }
        }

        public static class DeepLinkContent {
            Boolean closeOnDeepLink;
            String type;
            String url;
            String campaignId;

            public DeepLinkContent(Boolean closeOnDeepLink, String type, String url, String campaignId) {
                this.closeOnDeepLink = closeOnDeepLink;
                this.type = type;
                this.url = url;
                this.campaignId = campaignId;
            }

            public Boolean getCloseOnDeepLink() {
                return closeOnDeepLink;
            }

            public void setCloseOnDeepLink(Boolean closeOnDeepLink) {
                this.closeOnDeepLink = closeOnDeepLink;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCampaignId() {
                return campaignId;
            }

            public void setCampaignId(String campaignId) {
                this.campaignId = campaignId;
            }
        }

    }


}
