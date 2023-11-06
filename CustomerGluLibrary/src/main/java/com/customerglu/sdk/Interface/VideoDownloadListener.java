package com.customerglu.sdk.Interface;

public interface VideoDownloadListener {
    public void onVideoDownloaded();

    public void onVideoDownloadError(Exception e);
}