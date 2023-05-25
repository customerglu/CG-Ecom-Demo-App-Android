package com.customerglu.sdk.clienttesting;

import com.customerglu.sdk.Utils.CGConstants;

public class ClientTestingModel {

    String testName;
    CGConstants.TEST_STATE testState;
    int viewType;
    String url;

    public ClientTestingModel(String testName, CGConstants.TEST_STATE testState, int viewType, String url) {
        this.testName = testName;
        this.testState = testState;
        this.viewType = viewType;
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeadingType() {
        return viewType;
    }

    public void setHeadingType(int viewType) {
        this.viewType = viewType;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public CGConstants.TEST_STATE getTestState() {
        return testState;
    }

    public void setTestState(CGConstants.TEST_STATE testState) {
        this.testState = testState;
    }
}