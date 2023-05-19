package com.customerglu.sdk.cgRxBus.event;

public class ClientTestPageTestCaseEvent {

    boolean retry;
    String testName;

    public ClientTestPageTestCaseEvent(boolean retry, String testName) {
        this.retry = retry;
        this.testName = testName;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
