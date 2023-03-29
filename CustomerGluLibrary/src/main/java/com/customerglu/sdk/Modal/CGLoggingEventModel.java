package com.customerglu.sdk.Modal;

import java.util.ArrayList;

public class CGLoggingEventModel {
    public String timestamp;
    public String type;
    public String log_type;
    public String event_name;
    public String event_id;
    public String session_id;
    public String session_time;
    public String sdk_version;
    public String sdk_type;
    public String user_id;
    public String analytics_version;
    public PlatformDetails platform_details;
    public OptionalPayload optional_payload;

    public CGLoggingEventModel() {

    }

    public CGLoggingEventModel(String timestamp, String type, String log_type, String event_name, String event_id, String session_id, String session_time, String sdk_version, String sdk_type, String user_id, String analytics_version, PlatformDetails platform_details, OptionalPayload optional_payload) {
        this.timestamp = timestamp;
        this.type = type;
        this.log_type = log_type;
        this.event_name = event_name;
        this.event_id = event_id;
        this.session_id = session_id;
        this.session_time = session_time;
        this.sdk_version = sdk_version;
        this.sdk_type = sdk_type;
        this.user_id = user_id;
        this.analytics_version = analytics_version;
        this.platform_details = platform_details;
        this.optional_payload = optional_payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_time() {
        return session_time;
    }

    public void setSession_time(String session_time) {
        this.session_time = session_time;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getSdk_type() {
        return sdk_type;
    }

    public void setSdk_type(String sdk_type) {
        this.sdk_type = sdk_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAnalytics_version() {
        return analytics_version;
    }

    public void setAnalytics_version(String analytics_version) {
        this.analytics_version = analytics_version;
    }

    public PlatformDetails getPlatform_details() {
        return platform_details;
    }

    public void setPlatform_details(PlatformDetails platform_details) {
        this.platform_details = platform_details;
    }

    public OptionalPayload getOptional_payload() {
        return optional_payload;
    }

    public void setOptional_payload(OptionalPayload optional_payload) {
        this.optional_payload = optional_payload;
    }

    public static class PlatformDetails {
        public String os;
        public String os_version;
        public String manufacturer;
        public String model;

        public PlatformDetails() {
        }

        public PlatformDetails(String os, String os_version, String manufacturer, String model) {
            this.os = os;
            this.os_version = os_version;
            this.manufacturer = manufacturer;
            this.model = model;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
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
    }

    public static class OptionalPayload {
        public ArrayList<MetaData> metadata;

        public OptionalPayload(ArrayList<MetaData> metadata) {
            this.metadata = metadata;
        }

        public OptionalPayload() {
        }

        public ArrayList<MetaData> getMetadata() {
            return metadata;
        }

        public void setMetadata(ArrayList<MetaData> metadata) {
            this.metadata = metadata;
        }
    }
}
