package com.customerglu.sdk.Interface;


import com.customerglu.sdk.Modal.MqttDataModel;
import com.customerglu.sdk.Utils.CGConstants;

public interface MqttListener {

    void onDataReceived(CGConstants.CGSTATE state, MqttDataModel data, Throwable throwable);


}
