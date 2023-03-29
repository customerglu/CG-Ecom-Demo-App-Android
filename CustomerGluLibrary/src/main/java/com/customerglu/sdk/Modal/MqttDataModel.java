package com.customerglu.sdk.Modal;

public class MqttDataModel {

    String type;
    String id;

    public MqttDataModel() {
        
    }

    public MqttDataModel(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
