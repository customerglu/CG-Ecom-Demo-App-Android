package com.customerglu.sdk.cgRxBus.event;

public class CGBannerDismissShimmerEvent {

    boolean shouldDismissShimmer;

    public CGBannerDismissShimmerEvent(boolean value) {
        this.shouldDismissShimmer = value;
    }


    public boolean getImageLoadedCallback() {
        return shouldDismissShimmer;
    }

}
