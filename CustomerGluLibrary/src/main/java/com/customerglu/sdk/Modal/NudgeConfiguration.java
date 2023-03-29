package com.customerglu.sdk.Modal;


public class NudgeConfiguration {

    boolean closeOnDeepLink = false;
    double opacity = 0.5;
    String layout = "";
    double absoluteHeight = 0;
    double relativeHeight = 0;


    public boolean isCloseOnDeepLink() {
        return closeOnDeepLink;
    }

    public void setCloseOnDeepLink(boolean closeOnDeepLink) {
        this.closeOnDeepLink = closeOnDeepLink;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public double getAbsoluteHeight() {
        return absoluteHeight;
    }

    public void setAbsoluteHeight(double absoluteHeight) {
        this.absoluteHeight = absoluteHeight;
    }

    public double getRelativeHeight() {
        return relativeHeight;
    }

    public void setRelativeHeight(double relativeHeight) {
        this.relativeHeight = relativeHeight;
    }


}