package com.customerglu.sdk.Modal;

public class PIPConfig {

    Boolean muteOnDefaultPIP;
    Boolean muteOnDefaultExpanded;
    Boolean loopVideoPIP;
    Boolean loopVideoExpanded;
    Boolean darkPlayer;
    Boolean removeOnDismissExpanded;
    Boolean removeOnDismissPIP;


    public PIPConfig(Boolean muteOnDefaultPIP, Boolean muteOnDefaultExpanded, Boolean loopVideoPIP, Boolean loopVideoExpanded, Boolean darkPlayer, Boolean removeOnDismissExpanded, Boolean removeOnDismissPIP) {
        this.muteOnDefaultPIP = muteOnDefaultPIP;
        this.muteOnDefaultExpanded = muteOnDefaultExpanded;
        this.loopVideoPIP = loopVideoPIP;
        this.loopVideoExpanded = loopVideoExpanded;
        this.darkPlayer = darkPlayer;
        this.removeOnDismissExpanded = removeOnDismissExpanded;
        this.removeOnDismissPIP = removeOnDismissPIP;
    }

    public Boolean getMuteOnDefaultPIP() {
        return muteOnDefaultPIP;
    }

    public void setMuteOnDefaultPIP(Boolean muteOnDefaultPIP) {
        this.muteOnDefaultPIP = muteOnDefaultPIP;
    }

    public Boolean getMuteOnDefaultExpanded() {
        return muteOnDefaultExpanded;
    }

    public void setMuteOnDefaultExpanded(Boolean muteOnDefaultExpanded) {
        this.muteOnDefaultExpanded = muteOnDefaultExpanded;
    }

    public Boolean getLoopVideoPIP() {
        return loopVideoPIP;
    }

    public void setLoopVideoPIP(Boolean loopVideoPIP) {
        this.loopVideoPIP = loopVideoPIP;
    }

    public Boolean getLoopVideoExpanded() {
        return loopVideoExpanded;
    }

    public void setLoopVideoExpanded(Boolean loopVideoExpanded) {
        this.loopVideoExpanded = loopVideoExpanded;
    }

    public Boolean getDarkPlayer() {
        return darkPlayer;
    }

    public void setDarkPlayer(Boolean darkPlayer) {
        this.darkPlayer = darkPlayer;
    }

    public Boolean getRemoveOnDismissExpanded() {
        return removeOnDismissExpanded;
    }

    public void setRemoveOnDismissExpanded(Boolean removeOnDismissExpanded) {
        this.removeOnDismissExpanded = removeOnDismissExpanded;
    }

    public Boolean getRemoveOnDismissPIP() {
        return removeOnDismissPIP;
    }

    public void setRemoveOnDismissPIP(Boolean removeOnDismissPIP) {
        this.removeOnDismissPIP = removeOnDismissPIP;
    }
}
