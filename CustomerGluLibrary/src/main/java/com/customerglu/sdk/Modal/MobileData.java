package com.customerglu.sdk.Modal;

import java.util.List;
import java.util.Objects;

public class MobileData {
    Container container;
    Conditions conditions;
    List<Content> content;
    List<String> displayScreen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileData)) return false;
        MobileData that = (MobileData) o;
        return Objects.equals(getContainer(), that.getContainer()) && Objects.equals(getConditions(), that.getConditions()) && Objects.equals(getContent(), that.getContent()) && Objects.equals(getDisplayScreen(), that.getDisplayScreen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContainer(), getConditions(), getContent(), getDisplayScreen());
    }

    public MobileData(Container container, Conditions conditions, List<Content> content, List<String> displayScreen) {
        this.container = container;
        this.conditions = conditions;
        this.content = content;
        this.displayScreen = displayScreen;
    }

    public List<String> getDisplayScreen() {
        return displayScreen;
    }

    public void setDisplayScreen(List<String> displayScreen) {
        this.displayScreen = displayScreen;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public class Container {
        String type;
        String bannerId;
        String height;
        String width;
        String position;
        String borderRadius;
        AndroidScreenData android;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Container)) return false;
            Container container = (Container) o;
            return Objects.equals(getType(), container.getType()) && Objects.equals(getBannerId(), container.getBannerId()) && Objects.equals(getHeight(), container.getHeight()) && Objects.equals(getWidth(), container.getWidth()) && Objects.equals(getPosition(), container.getPosition()) && Objects.equals(getBorderRadius(), container.getBorderRadius()) && Objects.equals(getAndroid(), container.getAndroid());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getType(), getBannerId(), getHeight(), getWidth(), getPosition(), getBorderRadius(), getAndroid());
        }

        public Container(String type, String bannerId, String height, String width, String position, String borderRadius, AndroidScreenData android) {
            this.type = type;
            this.bannerId = bannerId;
            this.height = height;
            this.width = width;
            this.position = position;
            this.borderRadius = borderRadius;
            this.android = android;
        }

        public AndroidScreenData getAndroid() {
            return android;
        }

        public void setAndroid(AndroidScreenData android) {
            this.android = android;
        }

        public String getBorderRadius() {
            return borderRadius;
        }

        public void setBorderRadius(String borderRadius) {
            this.borderRadius = borderRadius;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String elementId) {
            this.bannerId = elementId;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public class AndroidScreenData {
            List<String> allowedActitivityList;
            List<String> disallowedActitivityList;

            public AndroidScreenData(List<String> allowedActitivityList, List<String> disallowedActitivityList) {
                this.allowedActitivityList = allowedActitivityList;
                this.disallowedActitivityList = disallowedActitivityList;
            }

            public List<String> getAllowedActitivityList() {
                return allowedActitivityList;
            }

            public void setAllowedActitivityList(List<String> allowedActitivityList) {
                this.allowedActitivityList = allowedActitivityList;
            }

            public List<String> getDisallowedActitivityList() {
                return disallowedActitivityList;
            }

            public void setDisallowedActitivityList(List<String> disallowedActitivityList) {
                this.disallowedActitivityList = disallowedActitivityList;
            }

        }
    }

    public class Conditions {
        int delay;
        boolean autoScroll;
        int autoScrollSpeed;
        boolean draggable;
        String backgroundOpacity;
        int priority;
        String backendValidations;
        ShowCount showCount;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Conditions)) return false;
            Conditions that = (Conditions) o;
            return getDelay() == that.getDelay() && isAutoScroll() == that.isAutoScroll() && getAutoScrollSpeed() == that.getAutoScrollSpeed() && isDraggable() == that.isDraggable() && getPriority() == that.getPriority() && Objects.equals(getBackgroundOpacity(), that.getBackgroundOpacity()) && Objects.equals(getShowCount(), that.getShowCount());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getDelay(), isAutoScroll(), getAutoScrollSpeed(), isDraggable(), getBackgroundOpacity(), getPriority(), getShowCount());
        }

        public Conditions(int delay, boolean autoScroll, String backendValidations, int autoScrollSpeed, boolean draggable, String backgroundOpacity, int priority, ShowCount showCount) {
            this.delay = delay;
            this.autoScroll = autoScroll;
            this.autoScrollSpeed = autoScrollSpeed;
            this.draggable = draggable;
            this.backendValidations = backendValidations;
            this.backgroundOpacity = backgroundOpacity;
            this.priority = priority;
            this.showCount = showCount;
        }

        public String getBackendValidations() {
            return backendValidations;
        }

        public void setBackendValidations(String backendValidations) {
            this.backendValidations = backendValidations;
        }

        public ShowCount getShowCount() {
            return showCount;
        }

        public void setShowCount(ShowCount showCount) {
            this.showCount = showCount;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public boolean getAutoScroll() {
            return autoScroll;
        }

        public int getAutoScrollSpeed() {
            return autoScrollSpeed;
        }

        public void setAutoScrollSpeed(int autoScrollSpeed) {
            this.autoScrollSpeed = autoScrollSpeed;
        }

        public boolean getDraggable() {
            return draggable;
        }

        public String getBackgroundOpacity() {
            return backgroundOpacity;
        }

        public void setBackgroundOpacity(String backgroundOpacity) {
            this.backgroundOpacity = backgroundOpacity;
        }

        public boolean isAutoScroll() {
            return autoScroll;
        }

        public void setAutoScroll(boolean autoScroll) {
            this.autoScroll = autoScroll;
        }

        public boolean isDraggable() {
            return draggable;
        }

        public void setDraggable(boolean draggable) {
            this.draggable = draggable;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public class ShowCount {
            int count;
            boolean dailyRefresh;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ShowCount)) return false;
                ShowCount showCount = (ShowCount) o;
                return getCount() == showCount.getCount() && isDailyRefresh() == showCount.isDailyRefresh();
            }

            @Override
            public int hashCode() {
                return Objects.hash(getCount(), isDailyRefresh());
            }

            public ShowCount(int count, boolean dailyRefresh) {
                this.count = count;
                this.dailyRefresh = dailyRefresh;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public boolean isDailyRefresh() {
                return dailyRefresh;
            }

            public void setDailyRefresh(boolean dailyRefresh) {
                this.dailyRefresh = dailyRefresh;
            }

        }
    }


    public class Content {
        String type;
        String openLayout;
        String campaignId;
        String url;
        String lightUrl;
        String darkUrl;
        Double absoluteHeight;
        Double relativeHeight;
        Boolean closeOnDeepLink;
        ActionData action;


        public Content(String type, String openLayout, String campaignId, String url, String lightUrl, String darkUrl, Double absoluteHeight, Double relativeHeight, boolean closeOnDeepLink, String _id, ActionData action) {
            this.type = type;
            this.openLayout = openLayout;
            this.campaignId = campaignId;
            this.darkUrl = darkUrl;
            this.url = url;
            this.lightUrl = lightUrl;
            this.absoluteHeight = absoluteHeight;
            this.relativeHeight = relativeHeight;
            this.closeOnDeepLink = closeOnDeepLink;
            this._id = _id;
            this.action = action;
        }

        public ActionData getAction() {
            return action;
        }

        public void setAction(ActionData action) {
            this.action = action;
        }

        public String getLightUrl() {
            return lightUrl;
        }

        public void setLightUrl(String lightUrl) {
            this.lightUrl = lightUrl;
        }

        public String getDarkUrl() {
            return darkUrl;
        }

        public void setDarkUrl(String darkUrl) {
            this.darkUrl = darkUrl;
        }

        public void setAbsoluteHeight(Double absoluteHeight) {
            this.absoluteHeight = absoluteHeight;
        }

        public void setRelativeHeight(Double relativeHeight) {
            this.relativeHeight = relativeHeight;
        }

        public Boolean getCloseOnDeepLink() {
            return closeOnDeepLink;
        }

        public void setCloseOnDeepLink(Boolean closeOnDeepLink) {
            this.closeOnDeepLink = closeOnDeepLink;
        }

        public Boolean isCloseOnDeepLink() {
            return closeOnDeepLink;
        }

        public void setCloseOnDeepLink(boolean closeOnDeepLink) {
            this.closeOnDeepLink = closeOnDeepLink;
        }

        public Double getAbsoluteHeight() {
            return absoluteHeight;
        }

        public void setAbsoluteHeight(double absoluteHeight) {
            this.absoluteHeight = absoluteHeight;
        }

        public Double getRelativeHeight() {
            return relativeHeight;
        }

        public void setRelativeHeight(double relativeHeight) {
            this.relativeHeight = relativeHeight;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        String _id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOpenLayout() {
            return openLayout;
        }

        public void setOpenLayout(String openLayout) {
            this.openLayout = openLayout;
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
    }

    public static class ActionData {
        String type;
        String url;
        Boolean isHandledBySDK;

        public ActionData(String type, String url, Boolean isHandledBySDK) {
            this.type = type;
            this.url = url;
            this.isHandledBySDK = isHandledBySDK;
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

        public Boolean isHandledBySDK() {
            return isHandledBySDK;
        }

        public void setHandledBySDK(Boolean handledBySDK) {
            isHandledBySDK = handledBySDK;
        }
    }

}


