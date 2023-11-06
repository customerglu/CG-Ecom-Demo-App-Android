package com.customerglu.sdk.ApiServices;


import com.customerglu.sdk.Modal.CGConfigurationModel;
import com.customerglu.sdk.Modal.CGLoggingEventModel;
import com.customerglu.sdk.Modal.ClientTestNotificationModel;
import com.customerglu.sdk.Modal.ClientTestPostDataModel;
import com.customerglu.sdk.Modal.ConfigurationData;
import com.customerglu.sdk.Modal.DeepLinkWormholeModel;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.EventData;
import com.customerglu.sdk.Modal.ProgramFilterModel;
import com.customerglu.sdk.Modal.RegisterModal;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.Modal.ScreenListModal;
import com.customerglu.sdk.Modal.ScreenListResponseModel;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {


    @POST("user/v1/user/sdk?token=true")
    Call<RegisterModal> doRegister(
            @Header("sandbox") boolean sanbox,
            @Body Map<String, Object> registerObject
    );

    @GET("reward/v1.1/user")
    Call<RewardModel> customerRetrieveData(@Header("Authorization") String token);


    @GET("reward/v1.1/user")
    Call<RewardModel> retrieveDataByFilter(
            @Header("Authorization") String token,
            @QueryMap Map<String, Object> paramsMap
    );

    @POST("https://events.customerglu.com/server/v4")
    Call<RegisterModal> sendEvents(@Header("x-api-key") String key,
                                   @Body EventData eventData);

    @GET("https://api.customerglu.com/reward/v1.1/client/assets")
    Call<ConfigurationData> getConfiguration(@Header("x-api-key") String key);

    @PUT("api/v1/report")
    Call<RegisterModal> sendCrashAnalytics(
            @Header("x-api-key") String key,
            @Body Map<String, Object> registerObject
    );

    @GET("/entrypoints/v1/list?consumer=MOBILE")
    Call<EntryPointsModel> getEntryPointsData(@Header("Authorization") String token);

    @GET("/entrypoints/v1/list?consumer=MOBILE")
    Call<EntryPointsModel> getEntryPointsDataById(
            @Header("Authorization") String token,
            @Query("id") String entrypointId);

    @POST("/entrypoints/v1/config")
    Call<ScreenListResponseModel> sendActivities(@Header("x-api-key") String key,
                                                 @Header("platform") String platform,
                                                 @Body ScreenListModal activityIdList);

//    @POST("https://stream.customerglu.com/v4/nudge")
//    Call<RegisterModal> sendNudgeAnalytics(@Header("X-API-KEY") String key,
//                                           @Header("X-GLU-AUTH") String token,
//                                           @Body Map<String, Object> nudgeData);

    @POST("https://stream.customerglu.com/v4/sdk")
    Call<RegisterModal> sendAnalyticsEvent(@Header("X-API-KEY") String key,
                                           @Header("X-GLU-AUTH") String token,
                                           @Body Map<String, Object> nudgeData);

    @GET("/client/v1/sdk/config")
    Call<CGConfigurationModel> getSDKConfiguration(@Header("x-api-key") String key);


    @GET()
    Call<JsonObject> readJson(
            @Url String url);

    @GET("/api/v1/wormhole/sdk/url")
    Call<DeepLinkWormholeModel> getWormholeData(
            @Header("X-API-KEY") String apiKey,
            @Query("id") String key);

    @POST("https://diagnostics.customerglu.com/sdk/v4")
    Call<RegisterModal> sendDiagnosticsEvent(@Header("X-API-KEY") String key,
                                             @Body CGLoggingEventModel diagnosticsData);


    @POST("/integrations/v1/nudge/sdk/test")
    Call<RewardModel> clientNudgeTest(
            @Header("Authorization") String token,
            @Body Map<String, Object> nudgeUserBody);

    @POST("/integrations/v1/onboarding/sdk/notification-config")
    Call<ClientTestNotificationModel> checkFirebaseConfiguration(
            @Header("x-api-key") String writeKey,
            @Body Map<String, Object> nudgeUserBody);

    @POST("/integrations/v1/onboarding/sdk/test-steps")
    Call<ClientTestNotificationModel> postClientTestingData(
            @Header("x-api-key") String writeKey,
            @Body ClientTestPostDataModel clientTestPostDataModel);

    @GET
    Call<String> readSSLCertificate(
            @Url String url);


    //FE API

    @POST("/reward/v2/user/program")
    Call<JsonObject> getPrograms(
            @Header("Authorization") String token,
            @Body ProgramFilterModel data
    );

    @POST("/reward/v2/user/reward")
    Call<JsonObject> getRewards(
            @Header("Authorization") String token,
            @Body ProgramFilterModel data
    );

    @GET
    Call<ResponseBody> downloadVideo(@Url String fileUrl);


}
