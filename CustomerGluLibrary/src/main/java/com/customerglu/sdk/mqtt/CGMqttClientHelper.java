package com.customerglu.sdk.mqtt;

import static com.customerglu.sdk.CustomerGlu.isMqttConnected;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_MQTT_CONNECTED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_MQTT_INITIALIZING;
import static com.customerglu.sdk.Utils.CGConstants.MQTT_SERVER_HOST;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.util.Base64;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.MqttListener;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.MqttDataModel;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.CGGsonHelper;
import com.customerglu.sdk.Utils.Comman;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.rx.FlowableWithSingle;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/***
 *  Author - Kausthubh S. A.
 *
 *  Created - 24th February 2023
 *  Updated - 1st March 2023
 *
 *
 *
 *  MQTT Service helper for Server and SDK realtime communication.
 *  Kindly use setupMQTTClient with parameters as follows
 *
 *
 *  username - User name in CG SDK
 *  token - JWT token shared in Registration call
 *  serverHost - MQTT broker server url
 *  topic - Topic to subscribe
 *
 */
public class CGMqttClientHelper {

    private static CGMqttClientHelper Instance;
    private Mqtt3Client client;
    private FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subAckFlowableWithSingle;
    Single<Mqtt3ConnAck> connAckSingle;
    Completable connectScenario, subscribeScenario;
    CGGsonHelper gsonHelper;
    String topic = "";
    String userName = "";
    public MqttListener mqttListener;


    public synchronized static CGMqttClientHelper getMqttInstance() {

        if (Instance == null) {
            Instance = new CGMqttClientHelper();
        }
        return Instance;
    }


    /**
     * MQTT Client can be setup using the following parameters -
     *
     * @param username   - User name in CG SDK
     * @param token      - JWT token shared in Registration call
     * @param clientId   - Topic to subscribe
     * @param identifier - Identifier needs to same for device
     */


    public void setupMQTTClient(String username, String token, String clientId, String identifier) {
        ArrayList<MetaData> responseMetaData = new ArrayList();
        this.userName = username;
        responseMetaData.add(new MetaData("username", username));
        responseMetaData.add(new MetaData("token", token));
        responseMetaData.add(new MetaData("clientId", clientId));
        responseMetaData.add(new MetaData("identifier", identifier));
        CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_MQTT_INITIALIZING, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, responseMetaData);

        client = Mqtt3Client.builder()
                .identifier(identifier)
                .simpleAuth()
                .username(username).password(token.getBytes())
                .applySimpleAuth()
                .serverHost(MQTT_SERVER_HOST)
                .serverPort(443)
                .sslWithDefaultConfig()
                .automaticReconnect()
                .initialDelay(500, TimeUnit.MILLISECONDS)
                .maxDelay(1, TimeUnit.MINUTES)
                .applyAutomaticReconnect()
                .buildRx();

        connAckSingle = client.toRx().connect().doOnError(this::onError);
        try {
            byte[] hashBytes = Comman.getSHA256(username);
            String userHash = Comman.toHexString(hashBytes);
            printDebugLogs("userHash " + userHash);
            topic = "nudges/" + clientId + "/" + userHash;
            printDebugLogs("topic " + topic);
            subscribeToTopic(topic);
        } catch (Exception e) {
            printErrorLogs("Mqtt sha-256 hash failed with exception " + e);
        }

        gsonHelper = CGGsonHelper.getInstance();


    }

    public void disconnectMqtt() {
        client.toRx().disconnect();
    }


    public void setMqttListener(MqttListener cgMqttListener) {
        this.mqttListener = cgMqttListener;
    }


    /**
     * Subscription topic should be shared
     *
     * @param topic
     */
    private void subscribeToTopic(String topic) {
        try {
            subAckFlowableWithSingle = client
                    .toRx()
                    .subscribePublishesWith()
                    .topicFilter(topic)
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .applySubscribe();
            setupCompletables();
        } catch (Exception e) {
            printErrorLogs("Mqtt topic subscription failure " + e);
        }

    }


    /***
     * Setup Completables for RxJava
     *
     */
    private void setupCompletables() {
        connectScenario = connAckSingle
                .doOnSuccess(connAck -> {
                    printDebugLogs("Connected, " + connAck.toString());
                    isMqttConnected = true;
                })
                .doOnError(this::onError)
                .ignoreElement();

        subscribeScenario = subAckFlowableWithSingle
                .doOnSingle(subAck -> printDebugLogs("Subscribed, " + subAck.toString()))
                .doOnNext(this::onDataReceivedFromMQTT)
                .doOnError(this::onError)
                .ignoreElements();

        connectScenario.andThen(subscribeScenario).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation()).subscribe(this::onMQTTSuccessful, this::onError);
    }


    /***
     * onDataReceivedFromMQTT
     *
     * @param publishData - publish data of type Mqtt3Publish
     */
    private void onDataReceivedFromMQTT(Mqtt3Publish publishData) {
        String data = new String(publishData.getPayloadAsBytes());
        byte[] encodeData = Base64.decode(data, Base64.DEFAULT);
        String mqttData = new String(encodeData, StandardCharsets.UTF_8);
        printDebugLogs("Mqtt onDataReceivedFromMQTT " + mqttData);
        MqttDataModel mqttDataModel = gsonHelper.getGsonInstance().fromJson(mqttData, MqttDataModel.class);
        if (mqttListener != null) {
            mqttListener.onDataReceived(CGConstants.CGSTATE.SUCCESS, mqttDataModel, new Throwable());
        }
    }


    /***
     * onMQTT Error
     *
     * @param throwable - Throwable / exception of type Throwable
     */
    private void onError(Throwable throwable) {
        MqttDataModel mqttDataModel = new MqttDataModel();
        if (mqttListener != null) {
            if (throwable.getMessage() != null) {
                mqttListener.onDataReceived(CGConstants.CGSTATE.EXCEPTION, mqttDataModel, throwable);
            }
        }
    }


    /***
     * onMQTTSuccessfully
     *
     */
    private void onMQTTSuccessful() {
        isMqttConnected = true;
        printDebugLogs("MQTT Successfully implemented");
        ArrayList<MetaData> responseMetaData = new ArrayList();
        responseMetaData.add(new MetaData("topic", topic));
        responseMetaData.add(new MetaData("userName", userName));
        CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_MQTT_CONNECTED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, responseMetaData);
    }


}
