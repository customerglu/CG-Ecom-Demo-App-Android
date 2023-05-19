package com.customerglu.sdk.Modal;

import com.customerglu.sdk.Utils.CGConstants;

import java.util.ArrayList;

public class ClientTestPostDataModel {

    String user_id;
    String platform;
    String cg_sdk_platform;
    String cg_sdk_version;
    String app_version;
    String manufacturer;
    String model;
    String os_version;
    ArrayList<TestData> data;

    public ClientTestPostDataModel() {

    }

    public ClientTestPostDataModel(String user_id, String platform, String cg_sdk_platform, String cg_sdk_version, String app_version, String manufacturer, String model, String os_version, ArrayList<TestData> data) {
        this.user_id = user_id;
        this.platform = platform;
        this.cg_sdk_platform = cg_sdk_platform;
        this.cg_sdk_version = cg_sdk_version;
        this.app_version = app_version;
        this.manufacturer = manufacturer;
        this.model = model;
        this.os_version = os_version;
        this.data = data;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCg_sdk_platform() {
        return cg_sdk_platform;
    }

    public void setCg_sdk_platform(String cg_sdk_platform) {
        this.cg_sdk_platform = cg_sdk_platform;
    }

    public String getCg_sdk_version() {
        return cg_sdk_version;
    }

    public void setCg_sdk_version(String cg_sdk_version) {
        this.cg_sdk_version = cg_sdk_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public ArrayList<TestData> getData() {
        return data;
    }

    public void setData(ArrayList<TestData> data) {
        this.data = data;
    }

    public static class TestData {

        CGConstants.TEST_NAME_ENUM name;
        String status;
        String timestamp;

        public TestData(CGConstants.TEST_NAME_ENUM name, String status, String timestamp) {
            this.name = name;
            this.status = status;
            this.timestamp = timestamp;
        }

        public CGConstants.TEST_NAME_ENUM getName() {
            return name;
        }

        public void setName(CGConstants.TEST_NAME_ENUM name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
