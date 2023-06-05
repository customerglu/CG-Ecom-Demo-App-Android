package com.customerglu.sdk;


import static android.app.Notification.DEFAULT_ALL;
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.app.NotificationCompat.PRIORITY_MAX;
import static com.customerglu.sdk.Utils.CGConstants.ANDROID;
import static com.customerglu.sdk.Utils.CGConstants.ANONYMOUSID;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_CLEAR_GLU_DATA_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_DISABLE_SDK_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_ENABLE_ANALYTICS_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_GET_ENTRY_POINT_END;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_GET_ENTRY_POINT_START;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_GLU_SDK_DEBUGGING_MODE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_INIT_END;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_INIT_START;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_LISTEN_SYSTEM_DARK_MODE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_LOADER_COLOR_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_LOAD_CAMPAIGN_BY_ID_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_LOAD_CAMPAIGN_END;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_LOAD_CAMPAIGN_START;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_NOTIFICATION_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_OPEN_NUDGE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_OPEN_WALLET_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_SEND_EVENT_END;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_SEND_EVENT_START;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_SET_DARK_MODE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_USER_REGISTRATION_END;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_USER_REGISTRATION_START;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_CONFIG_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_CONFIG_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_CONFIG_RESPONSE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_CONFIG_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_ENTRY_POINTS_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_ENTRY_POINTS_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_ENTRY_POINTS_RESPONSE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_ENTRY_POINTS_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_ENTRY_POINTS_UPDATE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_LOAD_CAMPAIGN_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_LOAD_CAMPAIGN_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_LOAD_CAMPAIGN_RESPONSE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_MQTT_CALLBACK_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_MQTT_CALLBACK_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_REGISTER_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_REGISTER_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_REGISTER_RESPONSE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_REGISTER_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_SERVER_EVENTS_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_SERVER_EVENTS_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_WORMHOLE_CALLED;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_WORMHOLE_FAILURE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_WORMHOLE_RESPONSE;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_WORMHOLE_SUCCESS;
import static com.customerglu.sdk.Utils.CGConstants.CG_OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.CG_USER_DATA;
import static com.customerglu.sdk.Utils.CGConstants.CLIENT_ID;
import static com.customerglu.sdk.Utils.CGConstants.CUSTOMERGLU_TOKEN;
import static com.customerglu.sdk.Utils.CGConstants.CUSTOMERGLU_USER_ID;
import static com.customerglu.sdk.Utils.CGConstants.DARK_LOTTIE_FILE_NAME;
import static com.customerglu.sdk.Utils.CGConstants.EMBED_DARK_LOTTIE_FILE_NAME;
import static com.customerglu.sdk.Utils.CGConstants.EMBED_LIGHT_LOTTIE_FILE_NAME;
import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN;
import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CUSTOMERGLU_USER_ID;
import static com.customerglu.sdk.Utils.CGConstants.ENTRYPOINT;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_CLICK;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_DISMISS;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_LOAD;
import static com.customerglu.sdk.Utils.CGConstants.ERROR_URL;
import static com.customerglu.sdk.Utils.CGConstants.ETAG;
import static com.customerglu.sdk.Utils.CGConstants.FLUTTER;
import static com.customerglu.sdk.Utils.CGConstants.HASH_CODE;
import static com.customerglu.sdk.Utils.CGConstants.IS_LOGIN;
import static com.customerglu.sdk.Utils.CGConstants.LIGHT_LOTTIE_FILE_NAME;
import static com.customerglu.sdk.Utils.CGConstants.LOTTIE_FILE;
import static com.customerglu.sdk.Utils.CGConstants.MQTT_IDENTIFIER;
import static com.customerglu.sdk.Utils.CGConstants.NOTIFICATION_LOAD;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_DEEPLINK;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WEBLINK;
import static com.customerglu.sdk.Utils.CGConstants.POPUP_DATE;
import static com.customerglu.sdk.Utils.CGConstants.PUSH_NOTIFICATION_CLICK;
import static com.customerglu.sdk.Utils.CGConstants.REACT_NATIVE;
import static com.customerglu.sdk.Utils.Comman.isJSONValid;
import static com.customerglu.sdk.Utils.Comman.isValidColor;
import static com.customerglu.sdk.Utils.Comman.isValidURL;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;
import static com.customerglu.sdk.mqtt.CGMqttClientHelper.getMqttInstance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;

import androidx.core.app.NotificationCompat;

import com.customerglu.sdk.ApiServices.ApiInterface;
import com.customerglu.sdk.Interface.CGDeepLinkListener;
import com.customerglu.sdk.Interface.DataListner;
import com.customerglu.sdk.Interface.MqttListener;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.CGConfigData;
import com.customerglu.sdk.Modal.CGConfigurationModel;
import com.customerglu.sdk.Modal.Campaigns;
import com.customerglu.sdk.Modal.ConfigurationData;
import com.customerglu.sdk.Modal.DeepLinkWormholeModel;
import com.customerglu.sdk.Modal.EntryPointsData;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.EventData;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.MqttDataModel;
import com.customerglu.sdk.Modal.NudgeConfiguration;
import com.customerglu.sdk.Modal.RegisterModal;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.Modal.ScreenListModal;
import com.customerglu.sdk.Modal.ScreenListResponseModel;
import com.customerglu.sdk.Screens.RewardScreen;
import com.customerglu.sdk.Utils.CGAPIHelper;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.CryptoreUtils;
import com.customerglu.sdk.Utils.DiagnosticsHelper;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.Utils.SentryHelper;
import com.customerglu.sdk.Web.FilterReward;
import com.customerglu.sdk.cgRxBus.CGRxBus;
import com.customerglu.sdk.clienttesting.ClientTestingPage;
import com.customerglu.sdk.entrypoints.EntryPointManager;
import com.customerglu.sdk.mqtt.CGMqttClientHelper;
import com.customerglu.sdk.notification.BottomDialog;
import com.customerglu.sdk.notification.BottomSheet;
import com.customerglu.sdk.notification.MiddleDialog;
import com.customerglu.sdk.notification.NotificationWeb;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerGlu {

    public static EntryPointsModel entryPointsModel;
    public static RewardModel loadCampaignResponse;
    public static ArrayList<String> campaignIdList;
    public CGDeepLinkListener cgDeepLinkListener;
    public static boolean sdk_disable = false;
    public static boolean isPrecachingEnable = false;
    public static boolean isLoginWithUserId = false;
    public static boolean isAnalyticsEventEnabled = false;
    public static boolean auto_close_webview = false;
    public static String currentScreenName = "";
    public static String configure_loader_color = "#65DCAB";
    public static String configure_status_bar_color = "#FFFFFF";
    public static String configure_loading_screen_color = "#FFFFFF";
    public static String configure_banner_default_url = "";
    public static Boolean allowUserRegistration = true;
    public static Boolean enableSentry = false;
    public static Boolean forceUserRegistration = false;
    public static Boolean isDarkMode = false;
    public static Boolean listenToSystemDarkLightMode = false;
    public static String lightBackground = "#FFFFFF";
    public static String darkBackground = "#000000";
    public static String lastScreenName = "";
    public static String errorMessageForDomain = "Error";
    public static int errorCodeForDomain = 401;
    public static String firebaseMessageId = "";
    public static List<String> whiteListDomain = null;
    public static boolean isBannerEntryPointsEnabled = false;
    public static boolean isBannerEntryPointsHasData = false;
    public static boolean debuggingMode = false;
    public static boolean isInitialized = false;
    public static boolean isLottieDownload = false;
    public static HashMap<String, Boolean> dismissedEntryPoints;
    public static List<Integer> entryPointId;
    public static List<String> displayScreen;
    public static List<String> screenList;
    public static List<String> bannerIdList;
    public static List<String> embedIdList;
    public static List<String> testUserIdList;
    public static JsonObject bannerCountData;
    @SuppressLint("StaticFieldLeak")
    public static Context globalContext;
    public static long myThread = 0;
    public static Activity currentActivity = null;
    public static String appSessionId = "";
    private static CustomerGlu single_instance = null;
    public WebView webView = null;
    public String s;
    String campaignUrl = "";
    HashMap<String, Integer> campaignCountObj = null;
    double opacity = 0.5;
    String popup_open_layout = "FULL_DEFAULT";
    String popupOpacity = "0.5";
    EntryPointManager entryPointManager;
    private String hash_code = "";
    private String hostUrl = "";
    private ApiInterface mService;
    private String token = "", user_id = "";
    private RegisterModal registerModal;
    public static RegisterModal.User cgUserData;
    //    List<String> popupdisallowedList;
//    List<String> popupallowedList;
    public static String dismiss_trigger = "UI_BUTTON";
    public static String cg_app_platform = "ANDROID";
    public static String cg_sdk_version = "2.3.6";
    private static String writeKey = "";
    public static boolean debugEnvironment = false;
    public static String darkStatusBarColor;
    public static String lightStatusBarColor;
    public static String clientId;
    public static DiagnosticsHelper diagnosticsHelper;
    public static boolean isDiagnosticsEnabled = false;
    public static boolean isMetricsEnabled = true;
    public static boolean isCrashLoggingEnabled = true;
    public static boolean allowAnonymousRegistration = false;
    public static boolean allowOpenWallet = true;
    static Disposable mqttBusDisposable;
    public static boolean isMqttEnabled = false;
    public static boolean isMqttConnected = false;
    public static int apiRetryCount = 3;
    public static String clientDeeplinkUrl = "";
    public static String callbackConfigurationUrl = "";
    public static CGRxBus cgrxBus;

    private static String LoadCampaignETAG = "";
    private static String EntryPointETAG = "";
    private static boolean MQTT_STATE_SYNC = false;
    private static boolean MQTT_NUDGES = false;
    private static boolean MQTT_ENTRY_POINTS = false;

    private CustomerGlu() {
        s = "CustomerGlu Singleton class";
        printDebugLogs(s);
    }

    /**
     * Static method to create instance of Singleton class of CustomerGlu Class
     */
    public static CustomerGlu getInstance() {
        if (single_instance == null) {
            single_instance = new CustomerGlu();
            appSessionId = UUID.randomUUID().toString();
            /*Initialize Empty HashMap for Entry Points*/
            dismissedEntryPoints = new HashMap<>();
            displayScreen = new ArrayList<>();
            entryPointId = new ArrayList<>();
            screenList = new ArrayList<>();
            bannerIdList = new ArrayList<>();
            embedIdList = new ArrayList<>();
            testUserIdList = new ArrayList<>();
            entryPointsModel = null;
            cgrxBus = new CGRxBus();
            loadCampaignResponse = null;
            campaignIdList = new ArrayList<>();

        }

        return single_instance;
    }

    public static void setWriteKey(String value) {
        writeKey = value;
    }

    /**
     * all SDK configuration
     */
    public void initializeSdk(Context context) {
        printDebugLogs("Initializing SDK");
        diagnosticsHelper = DiagnosticsHelper.getInstance();
        diagnosticsHelper.initDiagnostics(context, getWriteKey(context));
        boolean writeKeyPresent = false;
        boolean userRegistered = false;
        if (!getWriteKey(context).isEmpty()) {
            writeKeyPresent = true;
        }

        if (!Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
            userRegistered = true;
        }
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("writeKeyPresent", "" + writeKeyPresent));
        metaData.add(new MetaData("userRegistered", "" + userRegistered));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_INIT_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        globalContext = context;
        isInitialized = true;
        String writeKey = getWriteKey(context);

        Maybe.fromCallable((Callable<Void>) () -> {
            settingInitializeConfiguration(context, writeKey);
            return null;
        }).subscribeOn(Schedulers.computation()).subscribe();

    }


    public void handleConfigurationChanges(Activity activity) {
        CustomerGlu.isBannerEntryPointsHasData = false;
        enableEntryPoints(activity, true);
        showEntryPoint(activity);
    }

    private void settingInitializeConfiguration(Context globalContext, String writeKey) {
        if (isOnline(globalContext)) {
            printDebugLogs(" settingInitializeConfiguration SDK");
            ArrayList<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("writeKey", getWriteKey(globalContext)));
            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_CONFIG_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
            CGAPIHelper.enqueueWithRetry(Comman.getApiToken().getSDKConfiguration(writeKey), new Callback<CGConfigurationModel>() {
                @Override
                public void onResponse(Call<CGConfigurationModel> call, Response<CGConfigurationModel> response) {
                    printDebugLogs("Config API " + response.code());
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    printDebugLogs("API config body " + json);
                    ArrayList<MetaData> responseMetaData = new ArrayList<>();
                    responseMetaData.add(new MetaData("API config body", json));
                    responseMetaData.add(new MetaData("API config response code", "" + response.code()));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_CONFIG_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                    if (response.code() == 200 && response.body() != null && response.body().getSuccess() != null) {
                        if (response.body().getSuccess()) {
                            ArrayList<MetaData> successMetaData = new ArrayList<>();
                            successMetaData.add(new MetaData("API config body", json));
                            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_CONFIG_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);

                            if (response.body().getData() != null && response.body().getData().getMobile() != null) {
                                CGConfigData.CGMobileConfig mobile = response.body().getData().getMobile();
                                // Generate salt for encryption

                                if (mobile.getSecretKey() != null && !mobile.getSecretKey().isEmpty()) {
                                    CryptoreUtils.getCryptoreUtils().getSaltForEncryption(globalContext, mobile.getSecretKey());
                                }
                                if (mobile.getEnableSentry() != null && mobile.getEnableSentry()) {
                                    enableSentry = mobile.getEnableSentry();
                                    initializeSentryDsn(globalContext, mobile);
                                }
                                if (mobile.getDisableSdk() != null) {
                                    sdk_disable = mobile.getDisableSdk();
                                }
                                if (mobile.getEnableAnalytics() != null) {
                                    isAnalyticsEventEnabled = mobile.getEnableAnalytics();
                                }
                                if (mobile.getEnableEntryPoints() != null) {
                                    isBannerEntryPointsEnabled = mobile.getEnableEntryPoints();
                                }
                                if (mobile.getAllowedRetryCount() != null) {
                                    apiRetryCount = mobile.getAllowedRetryCount();
                                }
                                if (mobile.getCrashLoggingEnabled() != null) {
                                    isCrashLoggingEnabled = mobile.getCrashLoggingEnabled();
                                }
                                if (mobile.getDiagnosticsEnabled() != null) {
                                    isDiagnosticsEnabled = mobile.getDiagnosticsEnabled();
                                }
                                if (mobile.getMetricsEnabled() != null) {
                                    isMetricsEnabled = mobile.getMetricsEnabled();
                                }
                                if (mobile.getLoaderColor() != null && !mobile.getLoaderColor().isEmpty()) {
                                    configure_loader_color = mobile.getLoaderColor();
                                    if (mobile.getDeeplinkUrl() != null) {
                                        clientDeeplinkUrl = mobile.getDeeplinkUrl();
                                    }
                                    if (mobile.getCallbackConfigurationUrl() != null && !mobile.getCallbackConfigurationUrl().isEmpty()) {
                                        callbackConfigurationUrl = mobile.getCallbackConfigurationUrl();
                                    }
                                    if (mobile.getLoaderConfig() != null && mobile.getLoaderConfig().getLoaderURL() != null && mobile.getLoaderConfig().getLoaderURL().getLight() != null && !mobile.getLoaderConfig().getLoaderURL().getLight().isEmpty()) {
                                        if (Patterns.WEB_URL.matcher(mobile.getLoaderConfig().getLoaderURL().getLight()).matches()) {
                                            String[] lottieFileSplit = mobile.getLoaderConfig().getLoaderURL().getLight().split("/");
                                            String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                                            String cachedLottieFileName = Prefs.getKey(globalContext, LIGHT_LOTTIE_FILE_NAME);
                                            if (!lottieFileName.equalsIgnoreCase(cachedLottieFileName)) {

                                                saveLottieFile(globalContext, mobile.getLoaderConfig().getLoaderURL().getLight(), LOTTIE_FILE.LIGHT, lottieFileName);
                                            }
                                        }
                                    }
                                    if (mobile.getLoaderConfig() != null && mobile.getLoaderConfig().getLoaderURL() != null && mobile.getLoaderConfig().getLoaderURL().getDark() != null && !mobile.getLoaderConfig().getLoaderURL().getDark().isEmpty()) {
                                        if (Patterns.WEB_URL.matcher(mobile.getLoaderConfig().getLoaderURL().getDark()).matches()) {

                                            String[] lottieFileSplit = mobile.getLoaderConfig().getLoaderURL().getDark().split("/");
                                            String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                                            String cachedDarkLottieFileName = Prefs.getKey(globalContext, DARK_LOTTIE_FILE_NAME);
                                            if (!lottieFileName.equalsIgnoreCase(cachedDarkLottieFileName)) {
                                                saveLottieFile(globalContext, mobile.getLoaderConfig().getLoaderURL().getDark(), LOTTIE_FILE.DARK, lottieFileName);
                                            }
                                        }
                                    }
                                    if (mobile.getLoaderConfig() != null && mobile.getLoaderConfig().getEmbedLoaderURL() != null && mobile.getLoaderConfig().getEmbedLoaderURL().getLight() != null && !mobile.getLoaderConfig().getEmbedLoaderURL().getLight().isEmpty()) {
                                        if (Patterns.WEB_URL.matcher(mobile.getLoaderConfig().getEmbedLoaderURL().getLight()).matches()) {
                                            String[] lottieFileSplit = mobile.getLoaderConfig().getEmbedLoaderURL().getLight().split("/");
                                            String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                                            String cachedLottieFileName = Prefs.getKey(globalContext, EMBED_LIGHT_LOTTIE_FILE_NAME);
                                            if (!lottieFileName.equalsIgnoreCase(cachedLottieFileName)) {
                                                saveLottieFile(globalContext, mobile.getLoaderConfig().getEmbedLoaderURL().getLight(), LOTTIE_FILE.EMBED_LIGHT, lottieFileName);
                                            }
                                        }
                                    }
                                    if (mobile.getLoaderConfig() != null && mobile.getLoaderConfig().getEmbedLoaderURL() != null && mobile.getLoaderConfig().getEmbedLoaderURL().getDark() != null && !mobile.getLoaderConfig().getEmbedLoaderURL().getDark().isEmpty()) {
                                        if (Patterns.WEB_URL.matcher(mobile.getLoaderConfig().getEmbedLoaderURL().getDark()).matches()) {

                                            String[] lottieFileSplit = mobile.getLoaderConfig().getEmbedLoaderURL().getDark().split("/");
                                            String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                                            String cachedLottieFileName = Prefs.getKey(globalContext, EMBED_DARK_LOTTIE_FILE_NAME);
                                            if (!lottieFileName.equalsIgnoreCase(cachedLottieFileName)) {
                                                saveLottieFile(globalContext, mobile.getLoaderConfig().getEmbedLoaderURL().getDark(), LOTTIE_FILE.EMBED_DARK, lottieFileName);
                                            }
                                        }
                                    }
                                    if (mobile.getAndroidStatusBarColor() != null) {
                                        if (isValidColor(mobile.getAndroidStatusBarColor())) {
                                            configure_status_bar_color = rgbaToArgb(mobile.getAndroidStatusBarColor());
                                            lightStatusBarColor = rgbaToArgb(mobile.getAndroidStatusBarColor());
                                        } else {
                                            configure_status_bar_color = "#000000";
                                            lightStatusBarColor = "#FFFFFF";
                                        }
                                    }
                                    if (mobile.getLightBackground() != null) {
                                        if (isValidColor(mobile.getLightBackground())) {
                                            lightBackground = rgbaToArgb(mobile.getLightBackground());
                                        } else {
                                            lightBackground = "#FFFFFF";
                                        }
                                    }
                                    if (mobile.getDarkBackground() != null) {
                                        if (isValidColor(mobile.getDarkBackground())) {
                                            darkBackground = rgbaToArgb(mobile.getDarkBackground());
                                        } else {
                                            darkBackground = "#000000";
                                        }
                                    }
                                    if (mobile.getErrorCodeForDomain() != null) {
                                        errorCodeForDomain = mobile.getErrorCodeForDomain();
                                    }
                                    if (mobile.getErrorMessageForDomain() != null) {
                                        errorMessageForDomain = mobile.getErrorMessageForDomain();
                                    }
                                    if (mobile.getAllowUserRegistration() != null) {
                                        allowUserRegistration = mobile.getAllowUserRegistration();
                                    }
                                    if (mobile.getForceUserRegistration() != null) {
                                        forceUserRegistration = mobile.getForceUserRegistration();
                                    }
                                    if (mobile.getWhiteListedDomains() != null) {
                                        whiteListDomain = mobile.getWhiteListedDomains();
                                    }
                                    if (mobile.getListenToSystemDarkLightMode() != null) {
                                        listenToSystemDarkLightMode = mobile.getListenToSystemDarkLightMode();


                                    }
                                    if (mobile.getEnableDarkMode() != null) {
                                        isDarkMode = mobile.getEnableDarkMode();
                                    }
                                    if (isBannerEntryPointsEnabled) {
                                        CustomerGlu.getInstance().enableEntryPoints(globalContext, true);
                                    }
                                    if (mobile.getAllowAnonymousRegistration() != null) {
                                        allowAnonymousRegistration = mobile.getAllowAnonymousRegistration();
                                    }

                                    if (isDarkModeEnabled(globalContext)) {
                                        if (mobile.getAndroidStatusBarDarkColor() != null) {
                                            if (isValidColor(mobile.getAndroidStatusBarDarkColor())) {
                                                configure_status_bar_color = rgbaToArgb(mobile.getAndroidStatusBarDarkColor());
                                                darkStatusBarColor = rgbaToArgb(mobile.getAndroidStatusBarDarkColor());
                                            } else {
                                                configure_status_bar_color = "#000000";
                                                darkStatusBarColor = "#000000";
                                            }
                                        }
                                    } else {
                                        if (mobile.getAndroidStatusBarLightColor() != null) {
                                            if (isValidColor(mobile.getAndroidStatusBarLightColor())) {
                                                configure_status_bar_color = rgbaToArgb(mobile.getAndroidStatusBarLightColor());
                                            } else {
                                                configure_status_bar_color = "#FFFFFF";
                                            }
                                        }
                                    }
                                    if (mobile.getActivityIdList() != null) {
                                        if (mobile.getActivityIdList().getAndroid() != null) {
                                            screenList = mobile.getActivityIdList().getAndroid();
                                        }
                                    }
                                    if (mobile.getBannerIds() != null) {
                                        if (mobile.getBannerIds().getAndroid() != null) {
                                            bannerIdList = mobile.getBannerIds().getAndroid();
                                        }
                                    }
                                    if (mobile.getEmbedIds() != null) {
                                        if (mobile.getEmbedIds().getAndroid() != null) {
                                            embedIdList = mobile.getEmbedIds().getAndroid();
                                        }
                                    }
                                    if (mobile.getTestUserIds() != null) {
                                        testUserIdList = mobile.getTestUserIds();
                                    }


                                    if (mobile.getEnableMqtt() != null) {
                                        isMqttEnabled = mobile.getEnableMqtt();
                                    }


                                    if (isMqttEnabled && android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                        initializeMqtt();
                                    }

                                    String islogIn = Prefs.getEncKey(globalContext, CGConstants.IS_LOGIN);
                                    if (islogIn.equalsIgnoreCase("true")) {
                                        isLoginWithUserId = true;
                                    }

                                    if (mobile.getMqttEnabledComponents() != null) {
                                        if (mobile.getMqttEnabledComponents().contains(CGConstants.STATE_SYNC)) {
                                            MQTT_STATE_SYNC = true;
                                        }
                                        if (mobile.getMqttEnabledComponents().contains(CGConstants.NUDGES)) {
                                            MQTT_NUDGES = true;
                                        }
                                        if (mobile.getMqttEnabledComponents().contains(ENTRYPOINT)) {
                                            MQTT_ENTRY_POINTS = true;
                                        }
                                    }
                                    String myUserData = Prefs.getEncKey(globalContext, CG_USER_DATA);

                                    if (!myUserData.isEmpty()) {
                                        cgUserData = gson.fromJson(myUserData, RegisterModal.User.class);

                                    }
                                    printDebugLogs("settingInitializeConfiguration Done");

                                }
                                printDebugLogs("settingInitializeConfiguration Done");

                            }
                        }
                        boolean writeKeyPresent = false;
                        boolean userRegistered = false;
                        if (!getWriteKey(globalContext).isEmpty()) {
                            writeKeyPresent = true;
                        }

                        if (!Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
                            userRegistered = true;
                        }
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        metaData.add(new MetaData("writeKeyPresent", "" + writeKeyPresent));
                        metaData.add(new MetaData("userRegistered", "" + userRegistered));
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_INIT_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                    }
                }

                @Override
                public void onFailure(Call<CGConfigurationModel> call, Throwable t) {

                    printErrorLogs("Config API failed");
                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                    failureMetaData.add(new MetaData("failure", "" + t));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_CONFIG_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                    boolean writeKeyPresent = false;
                    boolean userRegistered = false;
                    if (!getWriteKey(globalContext).isEmpty()) {
                        writeKeyPresent = true;
                    }

                    if (!Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
                        userRegistered = true;
                    }
                    ArrayList<MetaData> metaData = new ArrayList<>();
                    metaData.add(new MetaData("writeKeyPresent", "" + writeKeyPresent));
                    metaData.add(new MetaData("userRegistered", "" + userRegistered));
                    diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_INIT_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                }
            });
        }
    }


    private void initializeMqtt() {
        if (!sdk_disable) {
            String userId = Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_USER_ID);
            String token = Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN);
            String clientId = Prefs.getEncKey(globalContext, CLIENT_ID);
            String mqtt_identifier = Prefs.getEncKey(globalContext, MQTT_IDENTIFIER);
            if (!userId.isEmpty() && !token.isEmpty()) {
                if (!clientId.isEmpty() && !mqtt_identifier.isEmpty()) {
                    mqttCallback(userId, token, clientId, mqtt_identifier);
                } else {
                    registerUser(userId);
                }
            }
        }
    }

    private void registerUser(String user_id) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", user_id);
        registerDevice(globalContext, userData, new DataListner() {
            @Override
            public void onSuccess(RegisterModal registerModal) {
                String userId = Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_USER_ID);
                String token = Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN);
                String clientId = Prefs.getEncKey(globalContext, CLIENT_ID);
                String mqtt_identifier = Prefs.getEncKey(globalContext, MQTT_IDENTIFIER);
                mqttCallback(userId, token, clientId, mqtt_identifier);

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    /**
     * @param userId     -  userId of registered user
     * @param token      - JWT token shared  registration call
     * @param clientId   - clientId  get from  registration call
     * @param identifier - UUID cretead during registration for user & device
     */
    private static void mqttCallback(String userId, String token, String clientId, String identifier) {
        Gson gson = new Gson();
        CGMqttClientHelper mqttClientHelper = getMqttInstance();
        mqttClientHelper.setMqttListener(new MqttListener() {
            @Override
            public void onDataReceived(CGConstants.CGSTATE state, MqttDataModel data, Throwable throwable) {
                if (data != null && state == CGConstants.CGSTATE.SUCCESS) {
                    String id = "";
                    ArrayList<MetaData> responseMetaData = new ArrayList();
                    if (data.getId() != null) {
                        id = data.getId();
                    }
                    responseMetaData.add(new MetaData("id", id));
                    responseMetaData.add(new MetaData("type", data.getId()));
                    CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_MQTT_CALLBACK_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);

                    //  System.out.println("RxBus data" + data);
                    switch (data.getType()) {
                        case CGConstants.ENTRYPOINT:
                            if (isMqttEnabled && MQTT_STATE_SYNC || MQTT_ENTRY_POINTS) {
                                doLoadCampaignAndEntryPointCall();
                            }
                            break;
                        case CGConstants.USER_SEGMENT_UPDATED:
                        case CGConstants.CAMPAIGN_STATE_UPDATED:
                            if (isMqttEnabled && MQTT_STATE_SYNC) {
                                doLoadCampaignAndEntryPointCall();
                            }
                            break;
                        case CGConstants.SDK_CONFIG_UPDATED:
                                CustomerGlu.getInstance().initializeSdk(globalContext);

                            break;
                        case CGConstants.OPEN_CLIENT_TESTING_PAGE:
                            if (isMqttEnabled) {
                                CustomerGlu.getInstance().testIntegration();
                            }
                            break;

                    }


                } else if (state == CGConstants.CGSTATE.EXCEPTION) {
                    ArrayList<MetaData> responseMetaData = new ArrayList();
                    if (throwable.getMessage() != null) {
                        responseMetaData.add(new MetaData("error", throwable.getMessage()));

                    } else {
                        responseMetaData.add(new MetaData("error", throwable.toString()));
                    }
                    CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_MQTT_CALLBACK_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                } else {
                    if (data != null) {
                        String responseData = gson.toJson(data, MqttDataModel.class);
                        ArrayList<MetaData> responseMetaData = new ArrayList();
                        responseMetaData.add(new MetaData("data", responseData));
                        CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_MQTT_CALLBACK_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                        printDebugLogs("RxBus Exception" + throwable);
                    } else {
                        ArrayList<MetaData> responseMetaData = new ArrayList();
                        responseMetaData.add(new MetaData("data", "" + data));
                        CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_MQTT_CALLBACK_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                        printDebugLogs("RxBus Exception" + data);
                    }
                }


            }

        });
        mqttClientHelper.setupMQTTClient(userId, token, clientId, identifier);
    }

    public static void doLoadCampaignAndEntryPointCall() {
        //    Toast.makeText(globalContext, data.getType(), Toast.LENGTH_SHORT).show();

        CustomerGlu.getInstance().retrieveData(globalContext, new RewardInterface() {
            @Override
            public void onSuccess(RewardModel rewardModel) {

                updateEntryPoints();
            }

            @Override
            public void onFailure(String message) {
                ArrayList<MetaData> responseMetaData = new ArrayList();
                responseMetaData.add(new MetaData("method", "mqttCallBack"));
                responseMetaData.add(new MetaData("Failure in loadCampaign ", message.toString()));
                CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_MQTT_CALLBACK_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
            }
        });

    }


    private static void initializeSentryDsn(Context globalContext, CGConfigData.CGMobileConfig mobile) {
        switch (cg_app_platform) {
            case ANDROID:
                if (mobile.getSentryDsn() != null && mobile.getSentryDsn().getAndroid() != null && !mobile.getSentryDsn().getAndroid().isEmpty()) {
                    String androidDsn = mobile.getSentryDsn().getAndroid();
                    SentryHelper.getInstance().initSentry(globalContext, androidDsn);
                }
                break;
            case FLUTTER:
                if (mobile.getSentryDsn() != null && mobile.getSentryDsn().getFlutter() != null && !mobile.getSentryDsn().getFlutter().isEmpty()) {
                    String flutterDsn = mobile.getSentryDsn().getFlutter();
                    SentryHelper.getInstance().initSentry(globalContext, flutterDsn);
                }
                break;
            case REACT_NATIVE:
                if (mobile.getSentryDsn() != null && mobile.getSentryDsn().getReactNative() != null && !mobile.getSentryDsn().getReactNative().isEmpty()) {
                    String reactNativeDsn = mobile.getSentryDsn().getReactNative();
                    SentryHelper.getInstance().initSentry(globalContext, reactNativeDsn);
                }
                break;
        }
    }

    public boolean isSentryEnabled() {
        return enableSentry;
    }


    private static void saveLottieFile(Context context, String url, LOTTIE_FILE lottieType, String lottieFileName) {
        Comman.getApiToken().readJson(url).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (isJSONValid(String.valueOf(response.body()))) {
                    switch (lottieType) {
                        case LIGHT:
                            Prefs.putKey(context, CGConstants.LIGHT_LOTTIE_FILE, String.valueOf(response.body()));
                            Prefs.putKey(context, LIGHT_LOTTIE_FILE_NAME, lottieFileName);
                            break;
                        case DARK:
                            Prefs.putKey(context, CGConstants.DARK_LOTTIE_FILE, String.valueOf(response.body()));
                            Prefs.putKey(context, DARK_LOTTIE_FILE_NAME, lottieFileName);
                            break;
                        case EMBED_LIGHT:
                            Prefs.putKey(context, CGConstants.EMBED_LIGHT_LOTTIE_FILE, String.valueOf(response.body()));
                            Prefs.putKey(context, EMBED_LIGHT_LOTTIE_FILE_NAME, lottieFileName);
                            break;
                        case EMBED_DARK:
                            Prefs.putKey(context, CGConstants.EMBED_DARK_LOTTIE_FILE, String.valueOf(response.body()));
                            Prefs.putKey(context, EMBED_DARK_LOTTIE_FILE_NAME, lottieFileName);
                            break;

                    }
                    isLottieDownload = true;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (t.getMessage() != null) {
                    CustomerGlu.getInstance().sendCrashAnalytics(context, t.getMessage());
                }
            }
        });

    }


    public static boolean isDarkModeEnabled(Context context) {
         /*if listenToSystemDarkLightMode is true and System is in Dark mode return true
         else if darkMode enable then return true
         else return false*/
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (isDarkMode) {
            configure_loading_screen_color = darkBackground;
            return true;
        } else if (listenToSystemDarkLightMode) {
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    printDebugLogs("onClick: NIGHT YES ");
                    configure_loading_screen_color = darkBackground;
                    return true;
                case Configuration.UI_MODE_NIGHT_NO:
                    printDebugLogs("onClick: LIGHT");
                    configure_loading_screen_color = lightBackground;
                    break;
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    printDebugLogs("onClick: DEFAULT YES ");
                    configure_loading_screen_color = lightBackground;
                    //   doStuff();
                    break;
            }
        } else {
            configure_loading_screen_color = lightBackground;
        }
        return false;
    }


    public void enableMqtt(boolean isEnabled) {
        isMqttEnabled = isEnabled;
    }

    public boolean isMqttEnabled() {
        return isMqttEnabled;
    }


    private static String rgbaToArgb(String color) {
        if (color.length() > 7) {
            char[] a = swap(color, 1, 7);
            color = String.valueOf(a);
            a = swap(color, 2, 8);
            color = String.valueOf(a);
        }
        return color;
    }

    private static char[] swap(String str, int i, int j) {
        char ch[] = str.toCharArray();
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
        return ch;
    }


    /**
     * Used for checking Internet Connection for SDK Functions Only
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void setCgDeepLinkListener(CGDeepLinkListener cgDeepLinkListener) {
        this.cgDeepLinkListener = cgDeepLinkListener;
    }


    public void setupCGDeepLinkIntentData(Activity activity) {
        Uri uri = activity.getIntent().getData();
        Comman.handleDeepLinkUri(uri);
    }

    public void validateDeepLinkKey(String key, String type) {
        if (!sdk_disable) {
            mService = Comman.getApiToken();
            String writeKey = getWriteKey(globalContext);
            ArrayList<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("writeKey", writeKey));
            metaData.add(new MetaData("key", key));
            metaData.add(new MetaData("type", type));
            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_WORMHOLE_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
            CGAPIHelper.enqueueWithRetry(mService.getWormholeData(writeKey, key), new Callback<DeepLinkWormholeModel>() {

                @Override
                public void onResponse(Call<DeepLinkWormholeModel> call, Response<DeepLinkWormholeModel> response) {
                    try {
                        ArrayList<MetaData> responseMetaData = new ArrayList<>();
                        Gson gson = new Gson();
                        String responseBody = gson.toJson(response.body());
                        responseMetaData.add(new MetaData("WormHole Response code", "" + response.code()));
                        responseMetaData.add(new MetaData("WormHole Response body", responseBody));
                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_WORMHOLE_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                String json = gson.toJson(response.body());
                                printDebugLogs("Deeplink body " + json);

                                if (response.body().getSuccess() != null && response.body().getSuccess()) {
                                    ArrayList<MetaData> successMetaData = new ArrayList<>();
                                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_WORMHOLE_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);
                                    DeepLinkWormholeModel.DeepLinkData deepLinkData = response.body().getDeepLinkData();
                                    if (deepLinkData.getAnonymous() != null) {

                                        if (deepLinkData.getAnonymous()) {
                                            allowAnonymousRegistration(true);
                                            if (Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
                                                HashMap<String, Object> userData = new HashMap<>();
                                                CustomerGlu.getInstance().registerDevice(globalContext, userData, new DataListner() {
                                                    @Override
                                                    public void onSuccess(RegisterModal registerModal) {
                                                        executeDeepLink(deepLinkData, type);
                                                    }

                                                    @Override
                                                    public void onFail(String message) {

                                                    }
                                                });
                                            } else {
                                                executeDeepLink(deepLinkData, type);
                                            }
                                        } else if (Comman.checkAnonymousUser(globalContext.getApplicationContext())) {
                                            executeDeepLink(deepLinkData, type);
                                        } else {
                                            if (cgDeepLinkListener != null) {

                                                cgDeepLinkListener.onFailure(CGConstants.CGSTATE.USER_NOT_SIGNED_IN);
                                            }
                                        }

                                    }

                                }

                            }

                        }
                        //  range between 400 -499 - invalid url
                        if (response.code() >= 400 && response.code() <= 499) {
                            if (cgDeepLinkListener != null) {

                                cgDeepLinkListener.onFailure(CGConstants.CGSTATE.INVALID_URL);
                            }
                        }
                    } catch (Exception e) {

                        ArrayList<MetaData> failureMetaData = new ArrayList<>();
                        failureMetaData.add(new MetaData("Exception", e.toString()));
                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_WORMHOLE_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                    }

                }

                @Override
                public void onFailure(Call<DeepLinkWormholeModel> call, Throwable t) {
                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                    String s = "";
                    if (t.getMessage() != null) {
                        s = t.getMessage();
                    }
                    failureMetaData.add(new MetaData("Exception", s));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_WORMHOLE_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                    if (cgDeepLinkListener != null) {

                        cgDeepLinkListener.onFailure(CGConstants.CGSTATE.NETWORK_EXCEPTION);
                    }

                }
            });

        }

    }


    private void executeDeepLink(DeepLinkWormholeModel.DeepLinkData deepLinkData, String type) {
        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
        if (deepLinkData.getContent().getCloseOnDeepLink() != null) {
            nudgeConfiguration.setCloseOnDeepLink(deepLinkData.getContent().getCloseOnDeepLink());
        }
        if (deepLinkData.getContainer().getAbsoluteHeight() != null) {
            nudgeConfiguration.setAbsoluteHeight(deepLinkData.getContainer().getAbsoluteHeight());
        }
        if (deepLinkData.getContainer().getRelativeHeight() != null) {
            nudgeConfiguration.setAbsoluteHeight(deepLinkData.getContainer().getRelativeHeight());
        }
        if (deepLinkData.getContent().getType() != null) {
            nudgeConfiguration.setLayout(deepLinkData.getContent().getType());
        }


        if (type.equalsIgnoreCase("u")) {
            if (cgDeepLinkListener != null) {
                cgDeepLinkListener.onSuccess(CGConstants.CGSTATE.DEEPLINK_URL, deepLinkData);
            }

        }
        if (type.equalsIgnoreCase("c")) {
            if (deepLinkData.getContent().getCampaignId() != null) {
                retrieveData(globalContext, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {
                        boolean campaignFound = false;
                        String campaignUrl = "";

                        if (rewardModel.getCampaigns() != null) {
                            if (rewardModel.getCampaigns().size() > 0) {
                                for (int i = 0; i < rewardModel.getCampaigns().size(); i++) {
                                    if (rewardModel.getCampaigns() != null && rewardModel.getCampaigns().size() > 0) {
                                        if (rewardModel.getCampaigns().get(i).getCampaignId() != null) {
                                            if (rewardModel.getCampaigns().get(i).getCampaignId().equalsIgnoreCase(deepLinkData.getContent().getCampaignId())) {
                                                campaignUrl = rewardModel.getCampaigns().get(i).getUrl();
                                                campaignFound = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (campaignFound) {
                            displayCGNudge(globalContext, campaignUrl, nudgeConfiguration);
                            if (cgDeepLinkListener != null) {
                                cgDeepLinkListener.onSuccess(CGConstants.CGSTATE.SUCCESS, deepLinkData);
                            }
                        } else {
                            if (cgDeepLinkListener != null) {

                                cgDeepLinkListener.onFailure(CGConstants.CGSTATE.CAMPAIGN_UNAVAILABLE);
                            }
                            if (rewardModel.defaultUrl != null) {
                                displayCGNudge(globalContext, rewardModel.defaultUrl, nudgeConfiguration);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        if (cgDeepLinkListener != null) {

                            cgDeepLinkListener.onFailure(CGConstants.CGSTATE.NETWORK_EXCEPTION);
                        }
                    }
                });
            } else {
                if (cgDeepLinkListener != null) {

                    cgDeepLinkListener.onFailure(CGConstants.CGSTATE.INVALID_CAMPAIGN);
                }
            }
        }
        if (type.equalsIgnoreCase("w")) {
            openWallet(globalContext, nudgeConfiguration);
            if (cgDeepLinkListener != null) {

                cgDeepLinkListener.onSuccess(CGConstants.CGSTATE.SUCCESS, deepLinkData);
            }
        }
    }

    /**
     * Used to get Write key from Manifest
     */
    public static String getWriteKey(Context context) {
        String apiKeyFromManifest = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;

            apiKeyFromManifest = bundle.getString("CUSTOMERGLU_WRITE_KEY");

        } catch (PackageManager.NameNotFoundException e) {

            Comman.printErrorLogs("WriteKey Not found");

        } catch (NullPointerException e) {

            Comman.printErrorLogs("WriteKey is null ");

        }
        if (apiKeyFromManifest != null && !apiKeyFromManifest.isEmpty()) {
            return apiKeyFromManifest;
        }
        return writeKey;
    }


    public static ArrayList<ActivityInfo> getAllRunningActivities(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return new ArrayList<>(Arrays.asList(pi.activities));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }


    /**
     * It will enable analytics events triggers from frontend
     */
    public void enableAnalyticsEvent(Boolean event) {
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("isAnalyticsEnabled - ", "" + event));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_ENABLE_ANALYTICS_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        isAnalyticsEventEnabled = event;
    }


    public void configureDomainCodeMsg(int code, String message) {
        errorMessageForDomain = message;
        errorCodeForDomain = code;
    }

    public void configureWhiteListedDomains(ArrayList<String> domains) {
        whiteListDomain = domains;
    }

    public void enableDarkMode(boolean value) {
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("isDarkMode - ", "" + value));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_SET_DARK_MODE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        isDarkMode = value;
    }

    public void listenToSystemDarkMode(boolean value) {
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("listenToSystemDarkMode - ", "" + value));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LISTEN_SYSTEM_DARK_MODE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        listenToSystemDarkLightMode = value;
    }

    public void configureLightBackgroundColor(String value) {

        lightBackground = value;
    }

    public void configureDarkBackgroundColor(String value) {
        darkBackground = value;
    }

    public void configureLightLoaderURL(Context context, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                String[] lottieFileSplit = value.split("/");
                String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                String cachedDarkLottieFileName = Prefs.getKey(context, LIGHT_LOTTIE_FILE_NAME);
                if (!lottieFileName.equalsIgnoreCase(cachedDarkLottieFileName)) {
                    saveLottieFile(context, value, LOTTIE_FILE.LIGHT, lottieFileName);
                }
            }
        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    public void configureDarkLoaderURL(Context context, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                String[] lottieFileSplit = value.split("/");
                String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                String cachedDarkLottieFileName = Prefs.getKey(context, DARK_LOTTIE_FILE_NAME);
                if (!lottieFileName.equalsIgnoreCase(cachedDarkLottieFileName)) {
                    saveLottieFile(context, value, LOTTIE_FILE.DARK, lottieFileName);
                }
            }
        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    public void configureLightEmbedLoaderURL(Context context, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                String[] lottieFileSplit = value.split("/");
                String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                String cachedDarkLottieFileName = Prefs.getKey(context, EMBED_LIGHT_LOTTIE_FILE_NAME);
                if (!lottieFileName.equalsIgnoreCase(cachedDarkLottieFileName)) {
                    saveLottieFile(context, value, LOTTIE_FILE.EMBED_LIGHT, lottieFileName);
                }
            }
        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    public void configureDarkEmbedLoaderURL(Context context, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                String[] lottieFileSplit = value.split("/");
                String lottieFileName = lottieFileSplit[lottieFileSplit.length - 1];
                String cachedDarkLottieFileName = Prefs.getKey(context, EMBED_DARK_LOTTIE_FILE_NAME);
                if (!lottieFileName.equalsIgnoreCase(cachedDarkLottieFileName)) {
                    saveLottieFile(context, value, LOTTIE_FILE.EMBED_DARK, lottieFileName);
                }
            }
        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    public void setScreenName(Context context, String screenName) {
        currentScreenName = screenName;
        lastScreenName = screenName;
        if (currentActivity != null) {
            showEntryPoint(currentActivity, currentScreenName);
        }
        if (!screenList.contains(screenName)) {
            screenList.add(screenName);
            ScreenListModal screenListModal = new ScreenListModal();
            screenListModal.setActivityIdList(screenList);
            sendActivities(context, screenListModal);
        }
    }

    public void showEntryPoint(Activity activity) {
        currentActivity = activity;
        printDebugLogs("Size - " + CustomerGlu.entryPointId.size());
        for (int i = 0; i < 10; i++) {
            @SuppressLint("ResourceType") View myView = ((Activity) activity).findViewById(1000013 + i);
            if (myView != null) {
                //   printDebugLogs("Remove" + "" + CustomerGlu.entryPointId.get(i));
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
            }
        }

        if (!lastScreenName.equalsIgnoreCase("")) {
            entryPointManager = new EntryPointManager(activity, currentScreenName);
        } else {
            entryPointManager = new EntryPointManager(activity);
        }
    }

    public void showEntryPoint(Activity activity, String currentScreenName) {
        currentActivity = activity;
        printDebugLogs("Size - " + CustomerGlu.entryPointId.size());
        if (entryPointManager != null) {
            entryPointManager.popupDismissThread();
        }
        for (int i = 0; i < 10; i++) {
            @SuppressLint("ResourceType") View myView = ((Activity) activity).findViewById(1000013 + i);
            if (myView != null) {
                //   printDebugLogs("Remove" + "" + CustomerGlu.entryPointId.get(i));
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
            }
        }
            EntryPointManager.getInstance(activity, currentScreenName).setScreenName(currentScreenName);

//        if (entryPointManager != null) {
//            entryPointManager.setScreenName(currentScreenName);
//        } else {
//            entryPointManager = new EntryPointManager(activity, currentScreenName);
//        }
    }

    /**
     * It will enable entry points for SDK
     */
    public void enableEntryPoints(Context context, Boolean enable) {
        isBannerEntryPointsEnabled = enable;
        String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
        if (isBannerEntryPointsEnabled && !token.equalsIgnoreCase("")) {
            printDebugLogs("Entry points enabled");
            getEntryPointData(context);
        }
    }

    /**
     * This function is used to set loader color
     */
/*
     if they update it manually we change system flag
*/
    public void setDarkMode(Context context, boolean value) {
        isDarkMode = value;
        if (isDarkMode) {
            isDarkModeEnabled(context);
        }

    }

    public void setListenSystemWideDarkLightMode(Context context, boolean value) {
        listenToSystemDarkLightMode = value;
        if (listenToSystemDarkLightMode) {
            isDarkModeEnabled(context);

        }

    }

    public void configureLoaderColour(Context context, String color) {
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("loaderColor - ", "" + color));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOADER_COLOR_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        configure_loader_color = color;
    }

    public void configureStatusBarColour(String color) {
        configure_status_bar_color = color;

    }

    public void configureLoadingScreenColor(String color) {
        configure_loading_screen_color = color;

    }

    /**
     * This function is used to set Default Banner Image for Campaigns
     */
    @Deprecated
    public void setDefaultBannerImage(Context context, String image_url) {
        configure_banner_default_url = image_url;
        Prefs.putEncKey(context, "default_image", image_url);
    }


    /**
     * Used to close Webview on Deeplink event
     */

    @Deprecated
    public void closeWebviewOnDeeplinkEvent(boolean close) {
        auto_close_webview = close;
    }

    /**
     * Used to disable all  SDK Functions
     */
    public void disableGluSdk(boolean disable) {
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("disableGluSdk - ", "" + disable));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_DISABLE_SDK_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        sdk_disable = disable;
    }

    /**
     * It is used to make SDK in debugging mode
     */

    public void gluSDKDebuggingMode(Context context, Boolean enable) {
        debuggingMode = enable;
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("gluSDKDebuggingMode - ", "" + enable));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_GLU_SDK_DEBUGGING_MODE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        //  pushActivities(context);
    }

//    private void pushActivities(Context context) {
//        try {
//            ArrayList<ActivityInfo> screenName = new ArrayList();
//            ArrayList<String> screenList = new ArrayList<>();
//
//            ScreenListModal screenListModal = new ScreenListModal();
//            screenName = getAllRunningActivities(context);
//            for (int i = 0; i < screenName.size(); i++) {
//                if (!screenName.get(i).name.contains("com.customerglu.sdk")) {
//                    screenList.add(screenName.get(i).name);
//                    printDebugLogs("" + screenName.get(i).name);
//                }
//
//            }
//            screenListModal.setActivityIdList(screenList);
//
//            sendActivities(context, screenListModal);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void sendActivities(Context context, ScreenListModal screenName) {
        mService = Comman.getApiToken();
        String write_Key = getWriteKey(context);
        String userId = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
        if (screenName.getEmbedIds() == null) {
            screenName.setEmbedIds(embedIdList);
        }
        if (screenName.getBannerIds() == null) {
            screenName.setBannerIds(bannerIdList);

        }
        if (screenName.getActivityIdList() == null) {
            screenName.setActivityIdList(screenList);

        }
        if (testUserIdList.contains(userId)) {

            CGAPIHelper.enqueueWithRetry(mService.sendActivities(write_Key, "android", screenName), new Callback<ScreenListResponseModel>() {

                @Override
                public void onResponse(Call<ScreenListResponseModel> call, Response<ScreenListResponseModel> response) {
                    if (response.code() == 200) {
                        printDebugLogs("Activities sent successfully");
                        if (response.body() != null && response.body().getSuccess() != null) {
                            if (response.body().getSuccess() && response.body().getData() != null) {
                                if (response.body().getData().getActivityIdList() != null && response.body().getData().getActivityIdList().getAndroid() != null) {
                                    screenList = response.body().getData().getActivityIdList().getAndroid();
                                }
                                if (response.body().getData().getBannerIds() != null && response.body().getData().getBannerIds().getAndroid() != null) {
                                    bannerIdList = response.body().getData().getBannerIds().getAndroid();
                                }
                                if (response.body().getData().getEmbedIds() != null && response.body().getData().getEmbedIds().getAndroid() != null) {
                                    embedIdList = response.body().getData().getEmbedIds().getAndroid();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ScreenListResponseModel> call, Throwable t) {
                    sendCrashAnalytics(context, t.toString());
                }
            });
        }
    }

    /**
     * Used to show popup banner of  CustomerGlu and this will be used internally by SDK
     */


    public void loadPopUpBanner(Context context, String campaign_id, String open_layout, String
            popupOpacity, double absoluteHeight, double relativeHeight, boolean closeOnDeepLink) {
        boolean isUrlValiadte = false;

        if (campaign_id != null && !campaign_id.isEmpty()) {
            if (campaign_id.startsWith("https://") || campaign_id.startsWith("http://")) {
                isUrlValiadte = true;
            }

        }
        if (isUrlValiadte) {
            printDebugLogs("Validated");
            NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
            nudgeConfiguration.setRelativeHeight(relativeHeight);
            nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
            nudgeConfiguration.setLayout(open_layout);
            nudgeConfiguration.setCloseOnDeepLink(closeOnDeepLink);
            nudgeConfiguration.setOpacity(Double.parseDouble(popupOpacity));
            displayCGNudge(context, campaign_id, nudgeConfiguration);

        } else {
            NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
            nudgeConfiguration.setRelativeHeight(relativeHeight);
            nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
            nudgeConfiguration.setCloseOnDeepLink(closeOnDeepLink);
            nudgeConfiguration.setLayout(open_layout);
            nudgeConfiguration.setOpacity(Double.parseDouble(popupOpacity));
            if (campaign_id != null) {
                loadCGCampaign(context, campaign_id, nudgeConfiguration);
            }
        }

    }

    public void displayCGNudge(Context context, String finalUrl, NudgeConfiguration
            nudgeConfiguration) {
        try {
            String layout = nudgeConfiguration.getLayout();
            boolean closeOnDeeplink = nudgeConfiguration.isCloseOnDeepLink();
            double bg_opacity = nudgeConfiguration.getOpacity();
            double absoluteHeight = nudgeConfiguration.getAbsoluteHeight();
            double relativeHeight = nudgeConfiguration.getRelativeHeight();
            boolean isHyperlink = nudgeConfiguration.isHyperlink();
            if (layout.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomSheet.class);
                intent.putExtra("nudge_url", finalUrl);
                intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                intent.putExtra("opacity", String.valueOf(bg_opacity));
                intent.putExtra("absoluteHeight", String.valueOf(absoluteHeight));
                intent.putExtra("relativeHeight", String.valueOf(relativeHeight));
                intent.putExtra("isHyperLink", "" + isHyperlink);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (layout.equalsIgnoreCase(CGConstants.BOTTOM_POPUP) || layout.equalsIgnoreCase(CGConstants.BOTTOM_DEFAULT_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomDialog.class);
                intent.putExtra("nudge_url", finalUrl);
                intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                intent.putExtra("opacity", String.valueOf(bg_opacity));
                intent.putExtra("absoluteHeight", String.valueOf(absoluteHeight));
                intent.putExtra("relativeHeight", String.valueOf(relativeHeight));
                intent.putExtra("isHyperLink", "" + isHyperlink);

                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (layout.equalsIgnoreCase(CGConstants.MIDDLE_POPUP) || layout.equalsIgnoreCase(CGConstants.MIDDLE_NOTIFICATION)) {
                Intent intent = new Intent(context, MiddleDialog.class);
                intent.putExtra("nudge_url", finalUrl);
                intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                intent.putExtra("opacity", String.valueOf(bg_opacity));
                intent.putExtra("absoluteHeight", String.valueOf(absoluteHeight));
                intent.putExtra("relativeHeight", String.valueOf(relativeHeight));
                intent.putExtra("isHyperLink", "" + isHyperlink);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, NotificationWeb.class);
                intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                intent.putExtra("nudge_url", finalUrl);
                intent.putExtra("isHyperLink", "" + isHyperlink);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            printErrorLogs("" + e);
        }
    }


    private void openBanner(Context context, String url, String open_layout, String
            popupOpacity) {
        try {
            if (open_layout.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomSheet.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(popupOpacity));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (open_layout.equalsIgnoreCase(CGConstants.BOTTOM_DEFAULT_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(popupOpacity));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (open_layout.equalsIgnoreCase(CGConstants.MIDDLE_NOTIFICATION)) {
                Intent intent = new Intent(context, MiddleDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(popupOpacity));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, NotificationWeb.class);
                intent.putExtra("nudge_url", url);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        } catch (Exception e) {
            Comman.printErrorLogs(e.toString());
        }
    }

    public void openNudge(Context context, String nudgeId, String layout, double bg_opacity) {
        if (nudgeId != null && layout != null) {
            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("nudgeId - ", "" + nudgeId));
                metaData.add(new MetaData("layout - ", "" + layout));
                metaData.add(new MetaData("bg_opacity - ", "" + bg_opacity));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_NUDGE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                retrieveData(context, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {

                        printDebugLogs(" campaigns load successfully");
                        try {

                            URL myUrl = new URL(hostUrl);
                            String protocol = myUrl.getProtocol();
                            URI uri = new URI(hostUrl);
                            String domain = protocol + "://" + uri.getHost();
                            String write_key = getWriteKey(context);
                            String my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                            String finalUrl = domain + "/fragment-map/?fragmentMapId=" + nudgeId + "&userId=" + my_user_id + "&writeKey=" + write_key;
                            printDebugLogs("openNudge " + finalUrl);
                            if (layout.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                                Intent intent = new Intent(context, BottomSheet.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(bg_opacity));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else if (layout.equalsIgnoreCase(CGConstants.BOTTOM_POPUP)) {
                                Intent intent = new Intent(context, BottomDialog.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(bg_opacity));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else if (layout.equalsIgnoreCase(CGConstants.MIDDLE_POPUP)) {
                                Intent intent = new Intent(context, MiddleDialog.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(bg_opacity));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, NotificationWeb.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                        } catch (Exception e) {
                            Comman.printErrorLogs(e.toString());
                        }


                    }

                    @Override
                    public void onFailure(String message) {
                        Comman.printErrorLogs(message);
                    }
                });


            } catch (Exception e) {
                Comman.printErrorLogs(e.toString());

            }
        } else {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "NudgeId or layout is Null");
            printErrorLogs("NudgeId or layout is Null");
        }
    }

    public void openNudge(Context context, String nudgeId, String layout) {
        if (nudgeId != null && layout != null) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("nudgeId - ", "" + nudgeId));
                metaData.add(new MetaData("layout - ", "" + layout));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_NUDGE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                retrieveData(context, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {

                        printDebugLogs(" campaigns load successfully");
                        try {

                            URL myUrl = new URL(hostUrl);
                            String protocol = myUrl.getProtocol();
                            URI uri = new URI(hostUrl);
                            String domain = protocol + "://" + uri.getHost();
                            String write_key = getWriteKey(context);
                            String my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                            String finalUrl = domain + "/fragment-map/?fragmentMapId=" + nudgeId + "&userId=" + my_user_id + "&writeKey=" + write_key;
                            printDebugLogs("openNudge " + finalUrl);
                            if (layout.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                                Intent intent = new Intent(context, BottomSheet.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(0.5));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else if (layout.equalsIgnoreCase(CGConstants.BOTTOM_POPUP)) {
                                Intent intent = new Intent(context, BottomDialog.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(0.5));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else if (layout.equalsIgnoreCase(CGConstants.MIDDLE_POPUP)) {
                                Intent intent = new Intent(context, MiddleDialog.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.putExtra("opacity", String.valueOf(0.5));
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, NotificationWeb.class);
                                intent.putExtra("nudge_url", finalUrl);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                        } catch (Exception e) {
                            Comman.printErrorLogs(e.toString());
                        }


                    }

                    @Override
                    public void onFailure(String message) {
                        Comman.printErrorLogs(message);
                    }
                });


            } catch (Exception e) {
                Comman.printErrorLogs(e.toString());

            }
        } else {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "NudgeId or layout is Null");
            printErrorLogs("NudgeId or Layout is Null");
        }
    }

    public void openNudge(Context context, String nudgeId, NudgeConfiguration
            nudgeConfiguration) {
        if (nudgeId != null) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                Gson gson = new Gson();
                String config = gson.toJson(nudgeConfiguration);
                metaData.add(new MetaData("nudgeConfiguration - ", "" + config));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_NUDGE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                retrieveData(context, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {

                        printDebugLogs(" campaigns load successfully");
                        if (hostUrl != null && !hostUrl.isEmpty()) {
                            try {
                                URL myUrl = new URL(hostUrl);
                                String protocol = myUrl.getProtocol();
                                URI uri = new URI(hostUrl);
                                String domain = protocol + "://" + uri.getHost();
                                String write_key = getWriteKey(context);
                                String my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                                String finalUrl = domain + "/fragment-map/?fragmentMapId=" + nudgeId + "&userId=" + my_user_id + "&writeKey=" + write_key;
                                printDebugLogs("openNudge " + finalUrl);
                                displayCGNudge(context, finalUrl, nudgeConfiguration);


                            } catch (Exception e) {
                                Comman.printErrorLogs(e.toString());
                            }
                        }


                    }

                    @Override
                    public void onFailure(String message) {
                        Comman.printErrorLogs(message);
                    }
                });


            } catch (Exception e) {
                Comman.printErrorLogs(e.toString());
            }
        } else {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "NudgeId or layout is Null");
            printErrorLogs("NudgeId or layout is Null");
        }
    }


    public void openNudge(Context context, String nudgeId, String layout, double bg_opacity,
                          boolean closeOnDeeplink) {
        if (nudgeId != null && layout != null) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("nudgeId - ", "" + nudgeId));
                metaData.add(new MetaData("layout - ", "" + layout));
                metaData.add(new MetaData("bg_opacity - ", "" + bg_opacity));
                metaData.add(new MetaData("closeOnDeeplink - ", "" + closeOnDeeplink));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_NUDGE_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

                retrieveData(context, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {

                        printDebugLogs(" campaigns load successfully");
                        if (hostUrl != null && !hostUrl.isEmpty()) {
                            try {
                                URL myUrl = new URL(hostUrl);
                                String protocol = myUrl.getProtocol();
                                URI uri = new URI(hostUrl);
                                String domain = protocol + "://" + uri.getHost();
                                String write_key = getWriteKey(context);
                                String my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                                String finalUrl = domain + "/fragment-map/?fragmentMapId=" + nudgeId + "&userId=" + my_user_id + "&writeKey=" + write_key;
                                printDebugLogs("openNudge " + finalUrl);
                                if (layout.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                                    Intent intent = new Intent(context, BottomSheet.class);
                                    intent.putExtra("nudge_url", finalUrl);
                                    intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                                    intent.putExtra("opacity", String.valueOf(bg_opacity));
                                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else if (layout.equalsIgnoreCase(CGConstants.BOTTOM_POPUP)) {
                                    Intent intent = new Intent(context, BottomDialog.class);
                                    intent.putExtra("nudge_url", finalUrl);
                                    intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                                    intent.putExtra("opacity", String.valueOf(bg_opacity));
                                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else if (layout.equalsIgnoreCase(CGConstants.MIDDLE_POPUP)) {
                                    Intent intent = new Intent(context, MiddleDialog.class);
                                    intent.putExtra("nudge_url", finalUrl);
                                    intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                                    intent.putExtra("opacity", String.valueOf(bg_opacity));
                                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(context, NotificationWeb.class);
                                    intent.putExtra("closeOnDeepLink", "" + closeOnDeeplink);
                                    intent.putExtra("nudge_url", finalUrl);
                                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }

                            } catch (Exception e) {
                                Comman.printErrorLogs(e.toString());
                            }
                        }


                    }

                    @Override
                    public void onFailure(String message) {
                        Comman.printErrorLogs(message);
                    }
                });


            } catch (Exception e) {
                Comman.printErrorLogs(e.toString());
            }
        } else {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "NudgeId or layout is Null");
            printErrorLogs("NudgeId or layout is Null");
        }
    }

    public void cgAnalyticsEventManager(Context context, String
            eventName, Map<String, Object> data) {
        if (CustomerGlu.isAnalyticsEventEnabled) {
            mService = Comman.getApiToken();
            String user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
            SimpleDateFormat sdf = new SimpleDateFormat(CGConstants.DATE_FORMAT_PATTERN, Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            HashMap<String, Object> nudgeData = new HashMap<>();

            HashMap<String, Object> platformDetails = new HashMap<>();
            platformDetails.put("device_type", "MOBILE");
            platformDetails.put("os", "ANDROID");
            platformDetails.put("app_platform", cg_app_platform);
            platformDetails.put("sdk_version", cg_sdk_version);
            if (eventName.equalsIgnoreCase(PUSH_NOTIFICATION_CLICK) || eventName.equalsIgnoreCase(NOTIFICATION_LOAD)) {
                nudgeData.put("nudge", data);
            } else if (eventName.equalsIgnoreCase(ENTRY_POINT_CLICK) || eventName.equalsIgnoreCase(ENTRY_POINT_LOAD) || eventName.equalsIgnoreCase(ENTRY_POINT_DISMISS)) {
                nudgeData.put("entry_point_data", data);
            } else {
                nudgeData.put("webview_content", data);
            }

//        nudgeData.put("appSessionId", appSessionId);
            nudgeData.put("event_id", UUID.randomUUID().toString());
            nudgeData.put("analytics_version", "4.0.0");
            nudgeData.put("event_name", eventName);
            nudgeData.put("type", CGConstants.TRACK);
            nudgeData.put("timestamp", currentDateandTime);
            nudgeData.put("user_id", user_id);
            nudgeData.put("platform_details", platformDetails);
            if (eventName.equalsIgnoreCase(CGConstants.WEBVIEW_DISMISS)) {
                nudgeData.put("dismiss_trigger", dismiss_trigger);
            }
            String writeKey = getWriteKey(context);
            String user_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
            Gson gson = new Gson();
            JsonObject nudgeAnalyticsData = gson.toJsonTree(nudgeData).getAsJsonObject();
            printDebugLogs(nudgeAnalyticsData.toString());
            //  printErrorLogs("My Analytics " + nudgeAnalyticsData.toString());
            Intent intent = new Intent("CUSTOMERGLU_ANALYTICS_EVENT");
            intent.putExtra("data", nudgeAnalyticsData.toString());
            context.sendBroadcast(intent);
            CGAPIHelper.enqueueWithRetry(mService.sendAnalyticsEvent(writeKey, "Bearer " + user_token, nudgeData), new Callback<RegisterModal>() {

                @Override
                public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                    printDebugLogs("Analytics API " + response.code());

                    if (response.code() == 200) {
                        printDebugLogs("Event sent");
                    }
                }

                @Override
                public void onFailure(Call<RegisterModal> call, Throwable t) {
                    printErrorLogs("Event failed");
                }
            });
        }

    }

    /**
     * Used to send nudge_analytics events from SDK
     */
//    public void (Context context, String id, String nudge_type, String
//            screenName, String actionName, String actionType, String openType) {
//        String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
//        mService = Comman.getApiToken();
//        String action_type = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
//        String currentDateandTime = sdf.format(new Date());
//
//        HashMap<String, Object> actionPayload = new HashMap<>();
//        HashMap<String, Object> nudgeData = new HashMap<>();
//
//        if (actionType.isEmpty()) {
//            action_type = "WALLET";
//            nudgeData.put("campaignId", "CAMPAIGNID_NOTPRESENT");
//
//        } else if (actionType.equalsIgnoreCase("CUSTOM_URL")) {
//            action_type = "CUSTOM_URL";
//            nudgeData.put("campaignId", "CAMPAIGNID_NOTPRESENT");
//
//
//        } else {
//            action_type = "CAMPAIGN";
//            nudgeData.put("campaignId", actionType);
//        }
//
//        nudgeData.put("deviceType", "ANDROID");
//        nudgeData.put("appSessionId", appSessionId);
//        nudgeData.put("eventId", UUID.randomUUID().toString());
//        nudgeData.put("eventName", "NUDGE_INTERACTION");
//        nudgeData.put("userAgent", "APP");
//        nudgeData.put("pageName", screenName);
//        nudgeData.put("nudgeType", nudge_type);
//        nudgeData.put("nudgeId", id);
//        nudgeData.put("actionTarget", action_type);
//        nudgeData.put("actionType", actionName);
//        nudgeData.put("timestamp", currentDateandTime);
//        nudgeData.put("pageType", openType);
//        nudgeData.put("version", "4.0.0");
//        nudgeData.put("optionalPayload", actionPayload);
//        String writeKey = getWriteKey(context);
//        String user_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
//        printDebugLogs(nudgeData.toString());
//        mService.sendNudgeAnalytics(writeKey, "Bearer " + user_token, nudgeData).enqueue(new Callback<RegisterModal>() {
//            @Override
//            public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
//                printDebugLogs("Analytics API " + response.code());
//
//                if (response.code() == 200) {
//                    printDebugLogs("Event sent");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterModal> call, Throwable t) {
//                printErrorLogs("Event failed");
//
//
//            }
//        });
//
//    }

    /**
     * Used to register a user with CustomerGlu
     */

    public void registerDevice(Context context, Map<String, Object> registerObject, DataListner callBack) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                try {
                    ArrayList<MetaData> metaData = new ArrayList<>();
                    metaData.add(new MetaData("registerObject", "" + registerObject.toString()));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
                    diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_USER_REGISTRATION_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                    boolean isLoadCampaigns = true;
                    String userId = null;
                    String anonymousId = null;
                    anonymousId = Prefs.getEncKey(context, ANONYMOUSID);

                    if (registerObject.get("userId") != null) {

                        userId = "" + registerObject.get("userId");
                        userId = userId.trim();
                        if (userId.equalsIgnoreCase("")) {
                            registerObject.remove("userId");
                            if (allowAnonymousRegistration && anonymousId.isEmpty()) {
                                anonymousId = UUID.randomUUID().toString();
                                Prefs.putEncKey(context, ANONYMOUSID, anonymousId);
                            }
                        }

                    } else {
                        if (allowAnonymousRegistration) {
                            if (anonymousId.isEmpty()) {
                                anonymousId = UUID.randomUUID().toString();
                                Prefs.putEncKey(context, ANONYMOUSID, anonymousId);
                            }
                        }
                    }
                    if (allowAnonymousRegistration) {
                        if (registerObject.get("anonymousId") != null) {
                            String aId = "" + registerObject.get("anonymousId");
                            if (!aId.trim().isEmpty()) {
                                anonymousId = aId;
                                Prefs.putEncKey(context, ANONYMOUSID, anonymousId);
                            }
                        }
                    }
                    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    String version = pInfo.versionName;
                    String write_key = getWriteKey(context);
                    if (write_key == null || write_key.equalsIgnoreCase("")) {
                        if (registerObject.containsKey("writeKey")) {
                            write_key = "" + registerObject.get("writeKey");
                        }
                    }
                    if (userId != null && !userId.isEmpty() || anonymousId != null && !anonymousId.isEmpty()) {

                        if (write_key != null && !write_key.equalsIgnoreCase("")) {
                            @SuppressLint("HardwareIds") String unique_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                            mService = Comman.getApiToken();
                            registerObject.put("deviceType", "android");
                            registerObject.put("deviceId", unique_id);
                            registerObject.put("deviceName", Build.MODEL);
                            registerObject.put("appVersion", version);
                            registerObject.put("writeKey", write_key.trim());
                            if (!anonymousId.isEmpty()) {
                                registerObject.put("anonymousId", anonymousId.trim());
                            }
                            Gson gson = new Gson();
                            String json = gson.toJson(registerObject);
                            printDebugLogs("Registration body " + json);
                            registerModal = new RegisterModal();

                            mService.doRegister(debuggingMode, registerObject)
                                    .enqueue(new Callback<RegisterModal>() {
                                        @Override
                                        public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                                            printDebugLogs("Registration API " + response.code());
                                            ArrayList<MetaData> responseMetaData = new ArrayList<>();
                                            String responseBody = gson.toJson(response.body());
                                            responseMetaData.add(new MetaData("Registration API code", "" + response.code()));
                                            responseMetaData.add(new MetaData("Registration API response body", "" + responseBody));
                                            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);

                                            //      progressDialog.dismiss();
                                            if (response.code() == 200) {
                                                if (response.body() != null) {
                                                    if (response.body().success != null && response.body().success.equalsIgnoreCase("true")) {
                                                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                                        ArrayList<MetaData> successMetaData = new ArrayList<>();
                                                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);
                                                        registerModal.setSuccess(response.body().success);
                                                        registerModal.setData(response.body().getData());
                                                        cgUserData = registerModal.data.getUser();
                                                        SentryHelper.getInstance().setupUser(registerModal.data.user.getUserId(), registerModal.data.getUser().getClient());
                                                        Gson gson = new Gson();
                                                        String json = gson.toJson(cgUserData);
                                                        if (response.body().getData() != null && response.body().getData().getUser() != null) {
                                                            if (response.body().getData().getUser().getClient() != null) {
                                                                Prefs.putEncKey(context, CGConstants.CLIENT_ID, response.body().getData().getUser().getClient());
                                                            }

                                                        }
                                                        // Generate Mqtt Device Identifier
                                                        String mqtt_identifier = Prefs.getEncKey(context, MQTT_IDENTIFIER);
                                                        if (mqtt_identifier.isEmpty()) {
                                                            mqtt_identifier = UUID.randomUUID().toString();
                                                            Prefs.putEncKey(context, MQTT_IDENTIFIER, mqtt_identifier);
                                                        }

                                                        String aId = Prefs.getEncKey(context, ANONYMOUSID);


                                                        if (response.body().getData().getUser().getAnonymousId() != null && !response.body().getData().getUser().getAnonymousId().equalsIgnoreCase(aId)) {
                                                            Prefs.putEncKey(context, CGConstants.FLOATING_DATE, "");
                                                            Prefs.putEncKey(context, POPUP_DATE, "");
                                                            Prefs.putCampaignIdObject(context, null);
                                                            Prefs.putEncCampaignIdObject(context, null);
                                                            Prefs.putDismissedEntryPoints(context, null);
                                                            Prefs.putEncDismissedEntryPoints(context, null);
                                                            dismissedEntryPoints = new HashMap<>();
                                                            displayScreen = new ArrayList<>();
                                                            Prefs.putEncKey(context, ANONYMOUSID, response.body().getData().getUser().getAnonymousId());
                                                        }
                                                        if (response.body().getData().getUser().getUserId() != null) {
                                                            user_id = response.body().getData().getUser().getUserId();
                                                            Prefs.putEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID, user_id);
                                                            String userId = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                                                            printDebugLogs("UserId " + userId);
                                                            isLoginWithUserId = true;
                                                            Prefs.putEncKey(context, IS_LOGIN, "true");
                                                        }
                                                        if (response.body().getData().getToken() != null) {
                                                            token = response.body().getData().getToken();
                                                            Prefs.putEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN, token);
                                                        }

                                                        if (isMqttConnected) {
                                                            CGMqttClientHelper mqttClientHelper = getMqttInstance();
                                                            mqttClientHelper.disconnectMqtt();
                                                        }

                                                        if (isMqttEnabled && android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                                            initializeMqtt();
                                                        }


                                                        retrieveData(context, new RewardInterface() {
                                                            @Override
                                                            public void onSuccess(RewardModel rewardModel) {

                                                                printDebugLogs(" campaigns load successfully");
                                                                callBack.onSuccess(registerModal);
                                                                if (isBannerEntryPointsEnabled && !isBannerEntryPointsHasData) {
                                                                    getEntryPointData(context);
                                                                }

                                                            }

                                                            @Override
                                                            public void onFailure(String message) {
                                                                Comman.printErrorLogs(message);
                                                                callBack.onSuccess(registerModal);
                                                            }
                                                        });
//


                                                    } else {
                                                        String s = String.valueOf(response.code());

                                                        CustomerGlu.getInstance().sendCrashAnalytics(context, "Response Body Null+" + s);
                                                        callBack.onFail("Please Try Again");
                                                    }

                                                } else {
                                                    String s = String.valueOf(response.code());
                                                    CustomerGlu.getInstance().sendCrashAnalytics(context, "Response Body Null+" + s);
                                                    printErrorLogs("Response Body Null" + response.code());
                                                    callBack.onFail("Please Try Again");
                                                }
                                            } else {

                                                String s = String.valueOf(response.code());
                                                CustomerGlu.getInstance().sendCrashAnalytics(context, "Registration Api" + s);
                                                printErrorLogs("400" + response.code());
                                                callBack.onFail("Please Try Again");

                                            }
                                            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_USER_REGISTRATION_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);


                                        }


                                        @Override
                                        public void onFailure(Call<RegisterModal> call, Throwable t) {
                                            String s = "" + t;
                                            printErrorLogs(s);
                                            ArrayList<MetaData> failureMetaData = new ArrayList<>();
                                            failureMetaData.add(new MetaData("failure", "" + s));
                                            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                                            CustomerGlu.getInstance().sendCrashAnalytics(context, "Registration Api Fails" + s);
                                            callBack.onFail(s);

                                        }
                                    });
                        } else {
                            printErrorLogs("Please add CustomerGlu Write Key in Manifest File");
                        }
                    } else {
                        printErrorLogs("UserId is Either null or Empty");
                    }


                } catch (Exception e) {
                    printErrorLogs(e.toString());
                    String s = e.toString();
                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                    failureMetaData.add(new MetaData("Exception", "" + s));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                    CustomerGlu.getInstance().sendCrashAnalytics(context, "Registration Api:" + s);
                    callBack.onFail(e.toString());

                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }
    }

    private static void updateEntryPoints() {

        String token = Prefs.getEncKey(globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN);
        if (isOnline(globalContext) && !sdk_disable && isBannerEntryPointsEnabled && !token.equalsIgnoreCase("")) {
            ArrayList<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("token", token));
            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_UPDATE_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_GET_ENTRY_POINT_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
            String reqToken = "Bearer " + token;
            CGAPIHelper.enqueueWithRetry(Comman.getApiToken().getEntryPointsData(reqToken), new Callback<EntryPointsModel>() {

                @Override
                public void onResponse(Call<EntryPointsModel> call, Response<EntryPointsModel> response) {
                    ArrayList<MetaData> responseMetaData = new ArrayList<>();
                    Gson gson = new Gson();
                    String responseBody = gson.toJson(response.body());
                    responseMetaData.add(new MetaData("EntryPoint API response code", "" + response.code()));
                    responseMetaData.add(new MetaData("EntryPoint API response body - ", responseBody));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getSuccess() != null && response.body().getSuccess()) {

                            if (response.body().getEntryPointsData() != null) {
                                boolean updateUI = true;
                                if (response.headers().get(ETAG) != null) {
                                    String eTag = response.headers().get(ETAG);
                                    if (eTag.equalsIgnoreCase(EntryPointETAG)) {
                                        updateUI = false;
                                    } else {
                                        EntryPointETAG = response.headers().get(ETAG);
                                    }
                                }
                                if (updateUI) {
                                    if (entryPointsModel != null) {
                                        if (entryPointsModel.getEntryPointsData() != null) {
                                            entryPointsModel = response.body();
                                            System.out.println("Broadcast Entrypoint");
                                            Intent intent = new Intent("CUSTOMERGLU_ENTRY_POINT_DATA");
                                            globalContext.sendBroadcast(intent);
                                            getBannerHeight(globalContext, entryPointsModel);
                                            Intent mqttIntent = new Intent("CG_MQTT_ENTRYPOINT_DATA_RECEIVED");
                                            globalContext.sendBroadcast(mqttIntent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<EntryPointsModel> call, Throwable t) {
                    ArrayList<MetaData> responseMetaData = new ArrayList<>();
                    responseMetaData.add(new MetaData("EntryPoint API onFailure ", "" + t.getMessage()));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                }
            });

        }
    }

    public static List<EntryPointsData> mergeArraysAndRemoveDuplicates(List<EntryPointsData> currentList, List<EntryPointsData> newList) {
        List<EntryPointsData> updateEntryPointList = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            boolean isPresent = false;
            int index = 0;
            for (int j = 0; j < currentList.size(); j++) {
                if (newList.get(i).get_id().equalsIgnoreCase(currentList.get(j).get_id())) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                updateEntryPointList.add(newList.get(i));
            }
        }
        //Step 1 : Merging of two arrays

        //Defining mergedArray with combined size of arrayA and arrayB

//        List<EntryPointsData> mergedArray = new List<EntryPointsData>[currentList.size() + newList.size()];
//
//        //Initializing pointers of arrayA, arrayB and mergedArray with 0
//
//        int i = 0, j = 0, k = 0;

        //Inserting all elements of arrayA into mergedArray

//        while (i < currentList.size()) {
//            mergedArray[k] = currentList[i];
//            k++;
//            i++;
//        }
//
//        //Inserting all elements of arrayB into mergedArray
//
//        while (j < arrayB.length) {
//            mergedArray[k] = arrayB[j];
//            k++;
//            j++;
//        }
//        currentList.addAll(newList);
//        //Step 2 : Removing duplicates from merged array
//
//        //Defining one HashSet object called setWithNoDuplicates
//        //Remember, HashSet allows only unique elements
//
//        //Adding all elements of mergedArray into setWithNoDuplicates
//        HashSet<EntryPointsData> hashSet = new HashSet<>();
//
//        // printing each element
//        hashSet.addAll(currentList);

//        Set<EntryPointsData> setWithNoDuplicates = new HashSet<>(currentList);
//        System.out.println("setWithNoDuplicates " + setWithNoDuplicates);
        //Now, setWithNoDuplicates will have only unique elements of mergedArray

        //So, now iterate setWithNoDuplicates and
        //add its elements into new array called mergedArrayWithNoDuplicates

        // Iterator<EntryPointsData> it = setWithNoDuplicates.iterator();


        //   updateEntryPointList = new ArrayList<>(hashSet);
        updateEntryPointList.addAll(currentList);

        printDebugLogs("setWithNoDuplicates List size " + updateEntryPointList.size());

//
//        int n = 0;
//
//        //Adding all elements of setWithNoDuplicates into mergedArrayWithNoDuplicates
//
//        while (it.hasNext()) {
//            mergedArrayWithNoDuplicates. = it.next();
//            n++;
//        }

        //Step 3 : Sorting merged array after removing duplicates

        //   Arrays.sort(mergedArrayWithNoDuplicates);

        return updateEntryPointList;
    }

    /**
     * Used to get a entry point data from CustomerGlu API
     */

    private void getEntryPointData(Context context) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                try {

                    if (!isBannerEntryPointsHasData) {
                        String cust_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                        if (!cust_token.equalsIgnoreCase("")) {
                            ArrayList<MetaData> metaData = new ArrayList<>();
                            metaData.add(new MetaData("token", cust_token));
                            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
                            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_GET_ENTRY_POINT_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                            if (!validateToken(cust_token)) {
                                Map<String, Object> registerData = new HashMap<>();

                                updateProfile(context, registerData, new DataListner() {
                                    @Override
                                    public void onSuccess(RegisterModal registerModal) {

                                    }

                                    @Override
                                    public void onFail(String message) {

                                    }
                                });

                            }
                            cust_token = "Bearer " + Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                            mService = Comman.getApiToken();
                            CGAPIHelper.enqueueWithRetry(Comman.getApiToken().getEntryPointsData(cust_token), new Callback<EntryPointsModel>() {
                                @Override
                                public void onResponse(Call<EntryPointsModel> call, Response<EntryPointsModel> response) {
                                    printDebugLogs("EntryPoint API " + response.code());
                                    ArrayList<MetaData> responseMetaData = new ArrayList<>();
                                    Gson gson = new Gson();
                                    String responseBody = gson.toJson(response.body());
                                    responseMetaData.add(new MetaData("EntryPoint API response code", "" + response.code()));
                                    responseMetaData.add(new MetaData("EntryPoint API response body - ", responseBody));
                                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);

                                    if (response.code() == 401) {
                                        //           queue.start();
                                        printDebugLogs("--------401---------");
                                        Map<String, Object> registerData = new HashMap<>();

                                        updateProfile(context, registerData, new DataListner() {
                                            @Override
                                            public void onSuccess(RegisterModal registerModal) {
                                                getEntryPointData(context);
                                            }

                                            @Override
                                            public void onFail(String message) {

                                            }
                                        });


                                    } else if (response.code() == 200) {
                                        if (response.body() != null) {
                                            if (response.body().getSuccess()) {

                                                ArrayList<MetaData> successMetaData = new ArrayList<>();
                                                diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);
                                                isBannerEntryPointsHasData = true;
                                                entryPointsModel = response.body();
                                                getBannerCount(context, entryPointsModel);
                                                getBannerHeight(context, entryPointsModel);
//
                                                printDebugLogs("Broadcast");

                                                Intent intent = new Intent("CUSTOMERGLU_ENTRY_POINT_DATA");
                                                context.sendBroadcast(intent);


                                            } else {
                                                isBannerEntryPointsHasData = false;
                                                entryPointsModel = new EntryPointsModel();
                                                entryPointsModel.setSuccess(false);
                                                printErrorLogs("" + entryPointsModel.getSuccess());
                                            }
                                        }

                                    } else {
                                        isBannerEntryPointsHasData = false;

                                        entryPointsModel = new EntryPointsModel();
                                        entryPointsModel.setSuccess(false);
                                        printErrorLogs("" + entryPointsModel.getSuccess());

                                        //   entryPointsModel.setSuccess(false);
                                    }


                                }

                                @Override
                                public void onFailure(Call<EntryPointsModel> call, Throwable t) {
                                    entryPointsModel = new EntryPointsModel();
                                    isBannerEntryPointsHasData = false;
                                    entryPointsModel.setSuccess(false);
                                    printErrorLogs("" + t);
                                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                                    failureMetaData.add(new MetaData("failure", "" + t));
                                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                                }
                            });
                            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_GET_ENTRY_POINT_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                        }
                    }
                } catch (Exception e) {
                    isBannerEntryPointsHasData = false;
                    entryPointsModel = new EntryPointsModel();
                    entryPointsModel.setSuccess(false);
                    printErrorLogs("df" + e);
                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                    failureMetaData.add(new MetaData("failure", "" + e));
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                    CustomerGlu.getInstance().sendCrashAnalytics(context, "Entry Point Fails" + e);
                }

            } else {
                isBannerEntryPointsHasData = false;
                printErrorLogs("No Internet Connection");

            }
        }
    }


    private void getBannerCount(Context context, EntryPointsModel entryPointsModel) {
        int n = entryPointsModel.getEntryPointsData().size();
        Map<String, Object> map = new HashMap();

        for (int i = 0; i < n; i++) {
            if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("BANNER")) {
                map.put(entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getBannerId(), entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size());
            }
        }


        Gson gson = new Gson();
        bannerCountData = gson.toJsonTree(map).getAsJsonObject();
        printDebugLogs(bannerCountData.toString());
        Intent intent = new Intent("CUSTOMERGLU_BANNER_LOADED");
        intent.putExtra("data", bannerCountData.toString());
        context.sendBroadcast(intent);

    }

    private static void getBannerHeight(Context context, EntryPointsModel entryPointsModel) {
        int n = entryPointsModel.getEntryPointsData().size();
        Map<String, Object> map = new HashMap();

        for (int i = 0; i < n; i++) {
            if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("BANNER")) {
                map.put(entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getBannerId(), entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getHeight());
            }
        }

        Gson gson = new Gson();
        JsonObject bannersHeight = gson.toJsonTree(map).getAsJsonObject();
        printDebugLogs(bannerCountData.toString());
        Intent intent = new Intent("CGBANNER_FINAL_HEIGHT");
        intent.putExtra("data", bannersHeight.toString());
        context.sendBroadcast(intent);

    }

    public void showPopUp(Context context, String screenName) {

        if (CustomerGlu.isBannerEntryPointsEnabled) {
            if (entryPointsModel == null || entryPointsModel.getEntryPointsData() == null) {
                printErrorLogs("No POPUP Present");
            } else {

                ArrayList<EntryPointsData> popupEntryPointsDataList = new ArrayList<>();
                printDebugLogs("ScreenName" + screenName);
                for (int i = 0; i < entryPointsModel.getEntryPointsData().size(); i++) {

                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData() != null) {
                        printDebugLogs("--------------Popup getMobileData()------------");

                        if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                            printDebugLogs("-------------- Popup getContainer()------------");


                            if (entryPointsModel.getEntryPointsData().get(i).getVisible()) {

                                printDebugLogs("-------------- Popup visible------------");

                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("POPUP")) {
                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations() != null) {
                                        if (!entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations().equalsIgnoreCase("INVALID")) {
                                            popupEntryPointsDataList.add(entryPointsModel.getEntryPointsData().get(i));
                                        }
                                    } else {
                                        popupEntryPointsDataList.add(entryPointsModel.getEntryPointsData().get(i));
                                    }
                                }


                            }

                        }
                    }
                }
                popupShowLogic(context, screenName, popupEntryPointsDataList);
                //     Log.e("PopUp ", "Popup list size " + popupEntryPointsDataList.size());


            }

        }
    }

    private void popupShowLogic(Context context, String screenName,
                                final ArrayList<EntryPointsData> popupEntryPointsDataList) {

        String current_date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        if (CustomerGlu.isBannerEntryPointsEnabled && !CustomerGlu.sdk_disable) {
            // entryPointManager.popupDismissThread();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {


                        campaignCountObj = Prefs.getEncCampaignIdObject(context);
                        printDebugLogs("Popup Saved obj" + Arrays.asList(campaignCountObj));

                        //    Log.e("CustomerGlu", "success");
                        // Popup banner list object
                        ArrayList<EntryPointsData> entryPointsDataList = popupEntryPointsDataList;

                        printDebugLogs("POPUP Entry point size" + entryPointsDataList.size());
                        if (campaignCountObj == null || campaignCountObj.isEmpty()) {
                            campaignCountObj = new HashMap<>();
                            for (int i = 0; i < entryPointsDataList.size(); i++) {

                                campaignCountObj.put(entryPointsDataList.get(i).get_id(), 0);
                            }
                            printDebugLogs("" + campaignCountObj.size());
                            Prefs.putEncCampaignIdObject(context, campaignCountObj);

                        }

                        if (entryPointsDataList.size() > 0) {


                            /* Checking if whether key is present or not if not then add it */


                            for (int j = 0; j < entryPointsDataList.size(); j++) {

                                if (!campaignCountObj.containsKey(entryPointsDataList.get(j).get_id())) {

                                    printDebugLogs("added new popup" + entryPointsDataList.get(j).get_id());
                                    campaignCountObj.put(entryPointsDataList.get(j).get_id(), 0);

                                }

                            }

                            /* Checking if whether key is present in response if not then Remove it from Pref */

                            for (int i = 0; i < campaignCountObj.size(); i++) {
                                boolean isPresent = false;
                                Object[] keys = campaignCountObj.keySet().toArray();
                                String id = String.valueOf(keys[i]);
                                printDebugLogs("campaignCountObj size" + keys.length);
                                printDebugLogs("campaignCountObj id" + id);
                                for (int j = 0; j < entryPointsDataList.size(); j++) {

                                    if (id.equals(entryPointsDataList.get(j).get_id())) {
                                        isPresent = true;
                                        String daily_refresh_date = Prefs.getEncKey(context, POPUP_DATE);
                                        if (!current_date.equalsIgnoreCase(daily_refresh_date) && entryPointsDataList.get(j).getMobileData().getConditions().getShowCount().isDailyRefresh()) {
                                            campaignCountObj.remove(id);
                                            printDebugLogs("Initialize to 0 " + id);
                                            campaignCountObj.put(entryPointsDataList.get(j).get_id(), 0);
                                        }
                                        break;
                                    }
                                }

                                if (!isPresent) {
                                    printDebugLogs("Old popup Removed" + id);
                                    campaignCountObj.remove(id);
                                }

                            }

                            Prefs.putEncCampaignIdObject(context, campaignCountObj);
                            campaignCountObj = Prefs.getEncCampaignIdObject(context);
                            printDebugLogs("popup lsit size " + entryPointsDataList.size());

                            entryPointsDataList = filterPopupForScreenName(screenName, entryPointsDataList);
                            printDebugLogs("Filter popup size " + entryPointsDataList.size());

                            /* if size is 1 */
                            if (entryPointsDataList.size() > 1) {
                                // Sorting
                                printDebugLogs("greater than 1");
                                entryPointsDataList = quickSort(entryPointsDataList);
                                printArr(entryPointsDataList, entryPointsDataList.size());

                            }
                            /* Daily Refresh logic for date comparison */
                            if (entryPointsDataList.size() > 0) {
                                printDebugLogs("Have popup  1");
                                int index = 0;
                                int i = 0;
                                Integer count = 100;
                                boolean isShowing = true;
                                if (campaignCountObj.get(entryPointsDataList.get(0).get_id()) != null) {
                                    printDebugLogs("Not null ");
                                    count = campaignCountObj.get(entryPointsDataList.get(0).get_id());
                                }
                                printDebugLogs("Count " + " " + count);


                                if (count >= entryPointsDataList.get(i).getMobileData().getConditions().getShowCount().getCount()) {

                                    isShowing = false;
                                    index++;
                                    if (entryPointsDataList.size() > 1) {
                                        while (i < entryPointsDataList.size()) {
                                            if (campaignCountObj.get(entryPointsDataList.get(index).get_id()) != null) {
                                                if (campaignCountObj.get(entryPointsDataList.get(index).get_id()) < entryPointsDataList.get(index).getMobileData().getConditions().getShowCount().getCount()) {
                                                    i++;
                                                    isShowing = true;
                                                    index = i;
                                                    count = campaignCountObj.get(entryPointsDataList.get(index).get_id());
                                                    break;
                                                }
                                            }
                                            i++;
                                        }
                                    }

                                }

                                if (isShowing) {

                                    if (entryPointsDataList.get(index).getMobileData().getConditions().getShowCount().isDailyRefresh()) {
                                        /* Check the _id is available and count value */

                                        String daily_refresh_date = Prefs.getEncKey(context, POPUP_DATE);
                                        String date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                                        if (!daily_refresh_date.equalsIgnoreCase("")) {
                                            // Need to save json
                                            if (daily_refresh_date.equalsIgnoreCase(date) && count < entryPointsDataList.get(index).getMobileData().getConditions().getShowCount().getCount()) {

                                                if (entryPointsDataList.get(index).getMobileData().getContent() != null && entryPointsDataList.get(index).getMobileData().getConditions() != null) {
                                                    printDebugLogs(" Time DailyRefresh Count " + entryPointsDataList.get(index).get_id() + " " + count);

                                                    int delay = entryPointsDataList.get(index).getMobileData().getConditions().getDelay();
                                                    delay = delay * 1000;
                                                    Thread.sleep(delay);
                                                    Prefs.putEncKey(context, POPUP_DATE, date);
                                                    printDebugLogs(" Count - " + entryPointsDataList.get(index).get_id() + " " + count);
                                                    campaignCountObj.remove(entryPointsDataList.get(index).get_id());
                                                    Integer newCount = count + 1;
                                                    campaignCountObj.put(entryPointsDataList.get(index).get_id(), newCount);
                                                    popup_open_layout = entryPointsDataList.get(index).getMobileData().getContent().get(0).getOpenLayout();
                                                    String campaign_id = entryPointsDataList.get(index).getMobileData().getContent().get(0).getCampaignId();
                                                    popupOpacity = entryPointsDataList.get(index).getMobileData().getConditions().getBackgroundOpacity();
                                                    Prefs.putEncCampaignIdObject(context, campaignCountObj);
                                                    printDebugLogs("" + Arrays.asList(campaignCountObj));
                                                    double relativeHeight = 0;
                                                    double absoluteHeight = 0;
                                                    boolean isClose = false;
                                                    if (entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                                        isClose = entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink();
                                                    }
                                                    if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                                                        relativeHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight();
                                                    }
                                                    if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                                                        absoluteHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight();
                                                    }


                                                    displayScreen.add(screenName);
                                                    Map<String, Object> nudgeData = new HashMap<>();
                                                    Map<String, Object> entry_point_content = new HashMap<>();
                                                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                                        entry_point_content.put("type", "WALLET");
                                                        entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                        entry_point_content.put("static_url", "");
                                                    } else {
                                                        entry_point_content.put("type", "CAMPAIGN");
                                                        entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                        entry_point_content.put("static_url", "");
                                                    }
                                                    if (isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId())) {
                                                        entry_point_content.put("type", "STATIC");
                                                        entry_point_content.put("static_url", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                        entry_point_content.put("campaign_id", "");

                                                    }
                                                    nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                                    nudgeData.put("entry_point_location", screenName);
                                                    if (entryPointsDataList.get(i).getName() != null) {
                                                        nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                                    } else {
                                                        nudgeData.put("entry_point_name", "");
                                                    }
                                                    nudgeData.put("entry_point_content", entry_point_content);
                                                    nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                                    CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
                                                    //          CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                                                    loadWeblink(context, entryPointsDataList, i, popupOpacity);
                                                    //    CustomerGlu.getInstance().loadPopUpBanner(context, campaign_id, popup_open_layout, popupOpacity, absoluteHeight, relativeHeight, isClose);
                                                    myThread = 0;

                                                }
                                            } else if (!daily_refresh_date.equalsIgnoreCase(date)) {
                                                printDebugLogs("Second Time DailyRefresh Count " + entryPointsDataList.get(index).get_id() + " " + count);

                                                int delay = entryPointsDataList.get(index).getMobileData().getConditions().getDelay();
                                                delay = delay * 1000;
                                                Thread.sleep(delay);
                                                Prefs.putEncKey(context, POPUP_DATE, date);
                                                printDebugLogs(" Count - " + entryPointsDataList.get(index).get_id() + " " + count);
                                                campaignCountObj.remove(entryPointsDataList.get(index).get_id());
                                                Integer newCount = count + 1;
                                                campaignCountObj.put(entryPointsDataList.get(index).get_id(), newCount);
                                                popup_open_layout = entryPointsDataList.get(index).getMobileData().getContent().get(0).getOpenLayout();
                                                String campaign_id = entryPointsDataList.get(index).getMobileData().getContent().get(0).getCampaignId();
                                                popupOpacity = entryPointsDataList.get(index).getMobileData().getConditions().getBackgroundOpacity();

                                                Prefs.putEncCampaignIdObject(context, campaignCountObj);
                                                printDebugLogs("Updated campaign Object" + Arrays.asList(campaignCountObj));
                                                double relativeHeight = 0;
                                                double absoluteHeight = 0;
                                                boolean isClose = false;
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                                    isClose = entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink();
                                                }
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                                                    relativeHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight();
                                                }
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                                                    absoluteHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight();
                                                }
                                                displayScreen.add(screenName);
                                                Map<String, Object> nudgeData = new HashMap<>();
                                                Map<String, Object> entry_point_content = new HashMap<>();
                                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                                    entry_point_content.put("type", "WALLET");
                                                    entry_point_content.put("campaign_id", "");
                                                    entry_point_content.put("static_url", "");
                                                } else if (isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId())) {
                                                    entry_point_content.put("type", "STATIC");
                                                    entry_point_content.put("static_url", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                    entry_point_content.put("campaign_id", "");

                                                } else {
                                                    entry_point_content.put("type", "CAMPAIGN");
                                                    entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                    entry_point_content.put("static_url", "");
                                                }

                                                nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                                nudgeData.put("entry_point_location", screenName);
                                                if (entryPointsDataList.get(i).getName() != null) {
                                                    nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                                } else {
                                                    nudgeData.put("entry_point_name", "");
                                                }
                                                nudgeData.put("entry_point_content", entry_point_content);
                                                nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                                CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
                                                //         CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());

                                                loadWeblink(context, entryPointsDataList, i, popupOpacity);
                                                myThread = 0;
                                            }
                                        } else {
                                            printDebugLogs("First Time DailyRefresh Count " + entryPointsDataList.get(index).get_id() + " " + count);

                                            if (entryPointsDataList.get(index).getMobileData().getContent() != null && entryPointsDataList.get(index).getMobileData().getConditions() != null) {
                                                int delay = entryPointsDataList.get(index).getMobileData().getConditions().getDelay();
                                                delay = delay * 1000;
                                                Thread.sleep(delay);
                                                Prefs.putEncKey(context, POPUP_DATE, date);
                                                printDebugLogs(" Count - " + entryPointsDataList.get(index).get_id() + " " + count);

                                                campaignCountObj.remove(entryPointsDataList.get(index).get_id());
                                                Integer newCount = count + 1;
                                                campaignCountObj.put(entryPointsDataList.get(index).get_id(), newCount);
                                                //     Log.e("CustomerGlu", "First time Popup Show - " + date);
                                                popup_open_layout = entryPointsDataList.get(index).getMobileData().getContent().get(0).getOpenLayout();
                                                String campaign_id = entryPointsDataList.get(index).getMobileData().getContent().get(0).getCampaignId();
                                                popupOpacity = entryPointsDataList.get(index).getMobileData().getConditions().getBackgroundOpacity();

                                                Prefs.putEncCampaignIdObject(context, campaignCountObj);
                                                printDebugLogs("Updated campaign Object" + Arrays.asList(campaignCountObj));

                                                displayScreen.add(screenName);
                                                double relativeHeight = 0;
                                                double absoluteHeight = 0;
                                                boolean isClose = false;
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                                    isClose = entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink();
                                                }
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                                                    relativeHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight();
                                                }
                                                if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                                                    absoluteHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight();
                                                }
                                                Map<String, Object> nudgeData = new HashMap<>();
                                                Map<String, Object> entry_point_content = new HashMap<>();
                                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                                    entry_point_content.put("type", "WALLET");
                                                    entry_point_content.put("campaign_id", "");
                                                    entry_point_content.put("static_url", "");
                                                } else if (isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId())) {
                                                    entry_point_content.put("type", "STATIC");
                                                    entry_point_content.put("static_url", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                    entry_point_content.put("campaign_id", "");

                                                } else {
                                                    entry_point_content.put("type", "CAMPAIGN");
                                                    entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                    entry_point_content.put("static_url", "");
                                                }
                                                nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                                nudgeData.put("entry_point_location", screenName);
                                                if (entryPointsDataList.get(i).getName() != null) {
                                                    nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                                } else {
                                                    nudgeData.put("entry_point_name", "");
                                                }
                                                nudgeData.put("entry_point_content", entry_point_content);
                                                nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                                CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
                                                //  CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());

                                                loadWeblink(context, entryPointsDataList, i, popupOpacity);
                                                myThread = 0;

                                            }
                                        }
                                    } else {
                                        printDebugLogs("Every App Launch Popup ");
                                        Integer newCount = count + 1;
                                        //       Log.e("Custo", "Count " + entryPointsDataList.get(index).get_id() + " " + newCount);

                                        if (entryPointsDataList.get(index).getMobileData().getContent() != null && entryPointsDataList.get(index).getMobileData().getConditions() != null) {
                                            int delay = entryPointsDataList.get(index).getMobileData().getConditions().getDelay();
                                            delay = delay * 1000;
                                            Thread.sleep(delay);
                                            printDebugLogs(" Count - " + entryPointsDataList.get(index).get_id() + " " + count);

                                            campaignCountObj.remove(entryPointsDataList.get(index).get_id());
                                            campaignCountObj.put(entryPointsDataList.get(index).get_id(), newCount);
                                            printDebugLogs("Updated campaign Object" + Arrays.asList(campaignCountObj));

                                            popup_open_layout = entryPointsDataList.get(index).getMobileData().getContent().get(0).getOpenLayout();
                                            String campaign_id = entryPointsDataList.get(index).getMobileData().getContent().get(0).getCampaignId();
                                            popupOpacity = entryPointsDataList.get(index).getMobileData().getConditions().getBackgroundOpacity();

                                            displayScreen.add(screenName);
                                            //     Log.e("Showing", "Sleeping");
                                            double relativeHeight = 0;
                                            double absoluteHeight = 0;
                                            boolean isClose = false;
                                            if (entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                                isClose = entryPointsDataList.get(index).getMobileData().getContent().get(0).isCloseOnDeepLink();
                                            }
                                            if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                                                relativeHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getRelativeHeight();
                                            }
                                            if (entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                                                absoluteHeight = entryPointsDataList.get(index).getMobileData().getContent().get(0).getAbsoluteHeight();
                                            }

                                            Prefs.putEncCampaignIdObject(context, campaignCountObj);
                                            printDebugLogs("" + Arrays.asList(campaignCountObj));

                                            Map<String, Object> nudgeData = new HashMap<>();
                                            Map<String, Object> entry_point_content = new HashMap<>();
                                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                                entry_point_content.put("type", "WALLET");
                                                entry_point_content.put("campaign_id", "");
                                                entry_point_content.put("static_url", "");
                                            } else if (isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId())) {
                                                entry_point_content.put("type", "STATIC");
                                                entry_point_content.put("static_url", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                entry_point_content.put("campaign_id", "");

                                            } else {
                                                entry_point_content.put("type", "CAMPAIGN");
                                                entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                                entry_point_content.put("static_url", "");
                                            }
                                            nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                            nudgeData.put("entry_point_location", screenName);
                                            if (entryPointsDataList.get(i).getName() != null) {
                                                nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                            } else {
                                                nudgeData.put("entry_point_name", "");
                                            }
                                            nudgeData.put("entry_point_content", entry_point_content);
                                            nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                            CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
                                            //      CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                                            //    Log.e("Showing", "Loading");

                                            loadWeblink(context, entryPointsDataList, i, popupOpacity);
                                            myThread = 0;

                                        }
                                        /*  1. Launch every time and update count value */
                                        // Need to check count
                                    }
                                }

                            }
                        } else {
                            printErrorLogs("No Popup banners added " + entryPointsDataList.size());
                            // Need to implement
                        }
                    } catch (Exception e) {
                        printErrorLogs("" + e);
                    }


                }
            });
            thread.start();
            myThread = thread.getId();
        } else {
            //  Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadWeblink(Context context, ArrayList<EntryPointsData> entryPointsDataList, int i, String opacity) {

        if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction() != null) {

            switch (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getType()) {
                case OPEN_DEEPLINK:
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                        try {
                            JSONObject dataObject = new JSONObject();
                            dataObject.put("deepLink", entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());

                            Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                            intent.putExtra("data", dataObject.toString());
                            context.sendBroadcast(intent);
                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK() != null && entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK()) {
                                Uri uri = Uri.parse(entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());
                                Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                                actionIntent.setData(uri);
                                context.startActivity(intent);
                            }
                        } catch (Exception e) {
                            Log.e("CUSTOMERGLU", "" + e);
                        }
                    }
                    break;
                case OPEN_WEBLINK:
                    NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                    nudgeConfiguration.setRelativeHeight(entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight());
                    nudgeConfiguration.setAbsoluteHeight(entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight());
                    nudgeConfiguration.setLayout(entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                    nudgeConfiguration.setCloseOnDeepLink(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCloseOnDeepLink());
                    nudgeConfiguration.setOpacity(Double.parseDouble(opacity));
                    nudgeConfiguration.setHyperlink(true);
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                        CustomerGlu.getInstance().displayCGNudge(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl(), nudgeConfiguration);
                    }
                    break;

                case OPEN_WALLET:
                    CustomerGlu.getInstance().loadPopUpBanner(context, CG_OPEN_WALLET, entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getCloseOnDeepLink());
                    break;

                default:
                    CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getCloseOnDeepLink());
            }
        } else {
            CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getCloseOnDeepLink());
        }
    }

    private ArrayList<EntryPointsData> filterPopupForScreenName(String
                                                                        screenName, ArrayList<EntryPointsData> entryPointsDataList) {
        ArrayList<EntryPointsData> popupEntryPointsDataList = new ArrayList<>();
        for (int i = 0; i < entryPointsDataList.size(); i++) {
            printDebugLogs("popup1 size " + entryPointsDataList.size());


            //      System.out.println("-------------- Popup visible------------");

            if (entryPointsDataList.get(i).getMobileData().getContainer().getAndroid() != null) {

                List<String> popupallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getAllowedActitivityList();
                List<String> popupdisallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getDisallowedActitivityList();
                printDebugLogs(("popup allowed List size " + popupallowedList.size()));

                if (popupallowedList.size() > 0 && popupdisallowedList.size() > 0) {
                    if (!popupdisallowedList.contains(screenName)) {
                        if (!displayScreen.contains(screenName)) {
                            popupEntryPointsDataList.add(entryPointsDataList.get(i));
                        }
                    }
                } else if (popupallowedList.size() > 0) {
                    if (popupallowedList.contains(screenName)) {

                        if (!displayScreen.contains(screenName)) {
                            popupEntryPointsDataList.add(entryPointsDataList.get(i));
                        }

                    }
                } else if (popupdisallowedList.size() > 0) {
                    if (!popupdisallowedList.contains(screenName)) {
                        if (!displayScreen.contains(screenName)) {
                            popupEntryPointsDataList.add(entryPointsDataList.get(i));
                        }
                    }
                }

            }

        }
        //     Log.e("PopUp ", "Popup list size " + popupEntryPointsDataList.size());
        printDebugLogs("Filter POPUP SIZE " + popupEntryPointsDataList.size());
        return popupEntryPointsDataList;


    }

    protected ArrayList<EntryPointsData> quickSort(ArrayList<EntryPointsData> list) {
        if (list.size() <= 1)
            return list; // Already sorted

        ArrayList<EntryPointsData> sorted = new ArrayList<>();
        ArrayList<EntryPointsData> lesser = new ArrayList<>();
        ArrayList<EntryPointsData> greater = new ArrayList<>();
        EntryPointsData pivot = list.get(list.size() - 1); // Use last Vehicle as pivot
        for (int i = 0; i < list.size() - 1; i++) {
            //int order = list.get(i).compareTo(pivot);
            if (list.get(i).getMobileData().getConditions().getPriority() > pivot.getMobileData().getConditions().getPriority())
                lesser.add(list.get(i));
            else
                greater.add(list.get(i));
        }

        lesser = quickSort(lesser);
        greater = quickSort(greater);

        lesser.add(pivot);
        lesser.addAll(greater);
        sorted = lesser;
        return sorted;
    }

    void printArr(ArrayList<EntryPointsData> a, int n) {
        int i;
        for (i = 0; i < n; i++)
            printDebugLogs(a.get(i).getMobileData().getConditions().getPriority() + " ");
    }



    /* Used to Update Profile */

    /**
     * Used to send crash report to  CustomerGlu API
     */

    public void sendCrashAnalytics(Context context, String stack_trace) {
        if (!sdk_disable) {
            if (enableSentry) {
//                SentryHelper.getInstance().captureSentryEvent(new Exception(stack_trace));
            }
            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        metaData.add(new MetaData("stack_trace", stack_trace));
                        diagnosticsHelper.sendDiagnosticsReport(CGConstants.EXCEPTION, CGConstants.CG_LOGGING_EVENTS.EXCEPTION, metaData);
                        long unixTime;
                        HashMap<String, Object> registerObject = new HashMap<>();
                        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        String version = pInfo.versionName;
                        String app_name = pInfo.packageName;
                        String os_version = String.valueOf(Build.VERSION.SDK_INT);
                        String my_user_id;
                        if (isLoginWithUserId) {
                            my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                        } else {
                            my_user_id = Prefs.getEncKey(context, ANONYMOUSID);
                        }
                        String write_key = getWriteKey(context);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            unixTime = Instant.now().getEpochSecond();
                            registerObject.put("timestamp", unixTime);
                        }

                        @SuppressLint("HardwareIds") String unique_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                        mService = Comman.getApiToken();
                        registerObject.put("platform", "android");
                        registerObject.put("device_id", unique_id);
                        registerObject.put("device_name", Build.MODEL);
                        registerObject.put("app_version", version);
                        registerObject.put("os_version", os_version);
                        registerObject.put("stack_trace", stack_trace);
                        registerObject.put("version", "1.0");
                        registerObject.put("user_id", my_user_id);
                        registerObject.put("app_name", app_name);
                        registerModal = new RegisterModal();
                        printDebugLogs(registerObject.toString());
                        if (!write_key.equalsIgnoreCase("")) {
                            CGAPIHelper.enqueueWithRetry(mService.sendCrashAnalytics(write_key, registerObject), new Callback<RegisterModal>() {

                                @Override
                                public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                                    try {
                                        if (response.code() == 200) {
                                            printDebugLogs("Crash Log sended");
                                        }
                                        if (response.code() == 400) {
                                        } else {

                                        }

                                    } catch (Exception ex) {
                                        printDebugLogs(ex.toString());
                                    }
                                }


                                @Override
                                public void onFailure(Call<RegisterModal> call, Throwable t) {

                                    //       Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    } catch (Exception e) {
                        //  Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
                        Comman.printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register Token Not present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }


    }
    /* Used to send Diagnostic report from  */


    /**
     * Used to store  assets from frontend to local for low internet connectivity
     * TO make webview load faster
     */

    public void enablePrecaching(Context context) {
        if (!sdk_disable) {

            isPrecachingEnable = true;
            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {

                        mService = Comman.getApiToken();
                        String write_key = getWriteKey(context);
                        mService.getConfiguration(write_key)
                                .enqueue(new Callback<ConfigurationData>() {
                                    @Override
                                    public void onResponse(Call<ConfigurationData> call, Response<ConfigurationData> response) {
                                        printDebugLogs("Precaching API " + response.code());

                                        if (response.code() == 200) {
                                            if (response.body() != null) {
                                                if (response.body().getSuccess()) {
                                                    if (response.body().getData() != null) {

                                                        if (response.body().getData().getHash() != null) {
                                                            String hash = response.body().getData().getHash();
                                                            String url = "";
                                                            if (response.body().getData().getcompressedAssets() != null) {
                                                                url = response.body().getData().getcompressedAssets();

                                                            }

                                                            if (Prefs.getEncKey(context, HASH_CODE) == null) {
                                                                hash_code = "";
                                                            } else {
                                                                hash_code = Prefs.getEncKey(context, HASH_CODE);
                                                            }
                                                            //  hash_code ="";

                                                            if (!hash.equalsIgnoreCase(hash_code)) {
                                                                ContextWrapper contextWrapper = new ContextWrapper(context);
                                                                File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                                                                File file = new File(zip, "CustomerGlu");
                                                                recursiveDelete(file);
                                                                startDownload(context, url, hash);
                                                            } else {
                                                                printDebugLogs("Resources are up to date");
                                                            }
                                                        }

                                                    } else {
                                                        printDebugLogs("Assets Not found");
                                                    }
                                                }
                                            }
                                        } else {
                                            Comman.printErrorLogs("" + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ConfigurationData> call, Throwable t) {

                                        Comman.printErrorLogs("" + t);

                                    }
                                });

                    } catch (Exception e) {
                        Comman.printErrorLogs(e.toString());
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                    }
                } else {
                    printErrorLogs("No Internet Connection");
                }
            }
        }


    }
    /* Used to get Campaigns Data from API */

    private void startDownload(Context context, String url, String hash) {
        Thread t = new Thread() {
            public void run() {
                downloadZip(context, url, hash);
            }
        };
        t.start();

    }
    /* Used to get Campaigns Data from API with Filters*/

    /* Used to download zip of assets from CustomerGlu*/
    private void downloadZip(Context context, String url, String hash) {
        try {
            long downloadID;

            String dowId = Prefs.getEncKey(context, "download_id");

            printDebugLogs("downloadId is" + dowId);
            if (dowId != null && !dowId.equalsIgnoreCase("")) {
                return;
            }
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            String title = URLUtil.guessFileName(url, null, "application/x-zip-compressed");
            request.setTitle(title);
            request.setDescription("Downloading....");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOCUMENTS, title);

            downloadID = downloadManager.enqueue(request);


            Prefs.putEncKey(context, "download_id", String.valueOf(downloadID));


            boolean finishDownload = false;
            int progress;
            while (!finishDownload) {
                Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(downloadID));
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_FAILED: {
                            Prefs.putEncKey(context, "download_id", "");
                            printErrorLogs("Assets Download failed");
                            ContextWrapper contextWrapper = new ContextWrapper(context);
                            File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                            deleteFile(context, zip + "/build.zip");
                            finishDownload = true;
                            break;
                        }
                        case DownloadManager.STATUS_PAUSED:
                            //  Log.e("CustomerGlu","paused");
                            break;
                        case DownloadManager.STATUS_PENDING:
                            break;
                        case DownloadManager.STATUS_RUNNING: {
                            @SuppressLint("Range") final long total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            if (total >= 0) {
                                @SuppressLint("Range") final long downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                progress = (int) ((downloaded * 100L) / total);
                                printDebugLogs("" + progress);
                            }
                            break;
                        }
                        // case DownloadManager.
                        case DownloadManager.STATUS_SUCCESSFUL: {
                            progress = 100;
                            // if you use aysnc task
                            // publishProgress(100);
                            finishDownload = true;
                            //   downloadID = Long.parseLong(null);
                            Prefs.putEncKey(context, "download_id", "");
                            printDebugLogs("Download completed");
                            Prefs.putEncKey(context, HASH_CODE, hash);
                            zipExtraction(context, title, hash);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Comman.printErrorLogs(e.toString());
            String s = e.toString();

            CustomerGlu.getInstance().sendCrashAnalytics(context, s);
        }
    }

    private void zipExtraction(Context context, String ZipName, String hash) {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(Comman.getZip(context, ZipName)));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(Comman.storeZip(context), zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        printDebugLogs("Extracting");
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

        } catch (Exception e) {
            String s = e.toString();

            CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            e.printStackTrace();
        } finally {
            deleteFile(context, Comman.getZip(context, ZipName));
        }

    }
    /* Used to Open List of All Campaigns*/

    /* Used to delete the Zip File after extraction */
    private void deleteFile(Context context, String path) {
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                printDebugLogs("file Deleted :");
            } else {
                printDebugLogs("file not Deleted :");
            }
        }
    }


    /* Used to Open List of Filtered Campaigns*/

    private File newFile(String destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir;
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    /* Used to delete all assets if new deployment occour from FrontEnd */
    private void recursiveDelete(File file) {
        //to end the recursive loop

        if (!file.exists()) {
            printDebugLogs("File not Exist");
            return;
        }

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        // printDebugLogs("Deleted file/folder: " + file.getAbsolutePath());
    }


    /* Used to display/handle CustomerGlu Nudges*/

    /**
     * Used to update a user details
     */
    @Deprecated
    public void updateProfile(Context context, Map<String, Object> userdata, DataListner
            dataListner) {
        if (sdk_disable) {
            return;
        } else {
            if (isOnline(context)) {
                try {
                    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    String version = pInfo.versionName;
                    String deviceOs = String.valueOf(Build.VERSION.SDK_INT);
                    String write_key = getWriteKey(context);
                    String my_user_id;
                    if (isLoginWithUserId) {
                        my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
                    } else {
                        my_user_id = Prefs.getEncKey(context, ANONYMOUSID);
                    }
                    @SuppressLint("HardwareIds") String unique_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    mService = Comman.getApiToken();

                    userdata.put("userId", my_user_id);
                    userdata.put("deviceType", "android");
                    userdata.put("deviceId", unique_id);
                    userdata.put("deviceName", Build.MODEL);
                    userdata.put("appVersion", version);
                    userdata.put("writeKey", write_key);
                    registerModal = new RegisterModal();
                    CGAPIHelper.enqueueWithRetry(mService.doRegister(debuggingMode, userdata), new Callback<RegisterModal>() {
                        @Override
                        public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                            printDebugLogs("Update Profile API " + response.code());

                            if (response.code() == 200) {
                                if (response.body() == null) {
                                    String s = "";
                                    CustomerGlu.getInstance().sendCrashAnalytics(context, "Update Profile Fails: response null" + s);
                                    dataListner.onFail("fail");
                                } else if (response.body().getSuccess().equalsIgnoreCase("true")) {
                                    String token = response.body().getData().getToken();
                                    cgUserData = response.body().data.getUser();
                                    SentryHelper.getInstance().setupUser(response.body().getData().getUser().getUserId(), response.body().getData().getUser().getClient());
                                    Prefs.putEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN, token);
                                    dataListner.onSuccess(registerModal);

                                } else {
                                    dataListner.onFail("fail");

                                }

                            }


                        }


                        @Override
                        public void onFailure(Call<RegisterModal> call, Throwable t) {
                            String s = "" + t;
                            dataListner.onFail("fail");

                            CustomerGlu.getInstance().sendCrashAnalytics(context, "Update Profile Fails" + s);
                            // Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show();

                        }
                    });


                } catch (Exception e) {
                    printErrorLogs(e.toString());
                    String s = e.toString();
                    dataListner.onFail("fail");

                    CustomerGlu.getInstance().sendCrashAnalytics(context, s);                 // System.out.println(e);
                }
            }
        }


    }

    /**
     * Used to Open a particular Campaign
     */

    public void retrieveData(Context context, RewardInterface callback) {
        if (!sdk_disable) {
            String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
            if (!token.isEmpty()) {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("token", token));
                try {
                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_LOAD_CAMPAIGN_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
                    diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                    String cust_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);

                    if (!validateToken(cust_token)) {
                        Map<String, Object> registerData = new HashMap<>();
                        updateProfile(context, registerData, new DataListner() {
                            @Override
                            public void onSuccess(RegisterModal registerModal) {

                            }

                            @Override
                            public void onFail(String message) {

                            }
                        });
                    }

                    cust_token = "Bearer " + Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);

                    mService = Comman.getApiToken();

                    CGAPIHelper.enqueueWithRetry(mService.customerRetrieveData(cust_token), new Callback<RewardModel>() {
                        @Override
                        public void onResponse
                                (Call<RewardModel> call, Response<RewardModel> response) {
                            try {

                                printDebugLogs("API Load Campaigns code " + response.code());
                                Gson gson = new Gson();
                                ArrayList<MetaData> responseMetaData = new ArrayList<>();
                                String responseBody = gson.toJson(response.body());
                                responseMetaData.add(new MetaData("Load Campaigns API response code", "" + response.code()));
                                responseMetaData.add(new MetaData("Load Campaigns API response body - ", responseBody));
                                diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_LOAD_CAMPAIGN_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                                //   System.out.println(response.body());
                                if (response.code() == 401) {
                                    //           queue.start();
                                    printDebugLogs("--------401---------");
                                    Map<String, Object> registerData = new HashMap<>();

                                    updateProfile(context, registerData, new DataListner() {
                                        @Override
                                        public void onSuccess(RegisterModal registerModal) {
                                            retrieveData(context, callback);
                                        }

                                        @Override
                                        public void onFail(String message) {

                                        }
                                    });


                                } else if (response.code() == 200) {

                                    if (response.body() == null) {
                                        //         queue.start();
                                        printErrorLogs("Failed to load campaigns response null");
                                        callback.onFailure("Failed to load campaigns response null");
                                    } else if (response.body().success == null) {

                                        callback.onFailure("Failed to load campaigns success null");

                                    } else if (response.body().success.equalsIgnoreCase("true")) {
                                        //      queue.start();
                                        LoadCampaignETAG = response.headers().get(ETAG);
                                        ArrayList<MetaData> successMetaData = new ArrayList<>();
                                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);
                                        //  Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                        hostUrl = response.body().defaultUrl;
                                        loadCampaignResponse = response.body();
                                        addCampaignIdToList(loadCampaignResponse);
                                        callback.onSuccess(response.body());


                                    } else {
                                        //     queue.start();
                                        printErrorLogs(response.body().message);
                                        callback.onFailure(response.body().message);
                                    }
                                }
                            } catch (Exception e) {
                                String s = e.toString();
                                ArrayList<MetaData> metaData1 = new ArrayList<>();
                                metaData1.add(new MetaData("Exception", "" + e));
                                diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_LOAD_CAMPAIGN_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData1);

                                CustomerGlu.getInstance().sendCrashAnalytics(context, "Load Campaign Api Fails" + s);
                                printErrorLogs(s);
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body());
                                printDebugLogs("CustomerGlu Catch " + json);
                            }

                            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

                        }

                        @Override
                        public void onFailure(Call<RewardModel> call, Throwable t) {
                            String s = "" + t;
                            ArrayList<MetaData> metaData1 = new ArrayList<>();
                            metaData1.add(new MetaData("Failure ", "" + s));
                            diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_LOAD_CAMPAIGN_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData1);
                            printDebugLogs("CustomerGlu LoadCampaign api call fails " + s);
                            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                            CustomerGlu.getInstance().sendCrashAnalytics(context, "Load Campaign Api Fails" + s);
                            printErrorLogs(s);

                        }
                    });


                } catch (Exception e) {
                    // queue.start();

                    String s = e.toString();
                    printDebugLogs("CustomerGlu LoadCampaign api catch  " + s);
                    CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                    printErrorLogs(e.toString());
                }
            }
        } else {
            printErrorLogs("Please register token not Present");
        }
    }

    private void addCampaignIdToList(RewardModel loadCampaignResponse) {

        if (loadCampaignResponse != null && loadCampaignResponse.getCampaigns() != null && loadCampaignResponse.getCampaigns().size() > 0) {

            for (int i = 0; i < loadCampaignResponse.getCampaigns().size(); i++) {
                if (loadCampaignResponse.getCampaigns().get(i).getUrl() != null && loadCampaignResponse.getCampaigns().get(i).getCampaignId() != null) {
                    if (!loadCampaignResponse.getCampaigns().get(i).getCampaignId().isEmpty()) {
                        campaignIdList.add(loadCampaignResponse.getCampaigns().get(i).getCampaignId());
                    }
                }
            }

        }

    }

    /**
     * Used to show list of campaign with filter
     */

    @Deprecated
    public void retrieveDataByFilter(Context context, Map<String, Object> params, RewardInterface callback) {
        if (!sdk_disable) {

            try {
                String cust_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);

                if (!validateToken(cust_token)) {
                    Map<String, Object> registerData = new HashMap<>();

                    updateProfile(context, registerData, new DataListner() {
                        @Override
                        public void onSuccess(RegisterModal registerModal) {

                        }

                        @Override
                        public void onFail(String message) {

                        }
                    });

                }


                cust_token = "Bearer " + Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);


                mService = Comman.getApiToken();

                mService.retrieveDataByFilter(cust_token, params)
                        .enqueue(new Callback<RewardModel>() {
                            @Override
                            public void onResponse(Call<RewardModel> call, Response<RewardModel> response) {

                                if (response.code() == 401) {
                                    Map<String, Object> registerData = new HashMap<>();

                                    updateProfile(context, registerData, new DataListner() {
                                        @Override
                                        public void onSuccess(RegisterModal registerModal) {
                                            retrieveDataByFilter(context, params, callback);
                                        }

                                        @Override
                                        public void onFail(String message) {

                                        }
                                    });


                                }

                                if (response.body() != null && response.body().success.equalsIgnoreCase("true")) {

                                    String default_url = response.body().defaultUrl;
                                    //  Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess(response.body());


                                } else {
                                    callback.onFailure("d");
                                }


                            }

                            @Override
                            public void onFailure(Call<RewardModel> call, Throwable t) {

                                String s = "" + t;

                                CustomerGlu.getInstance().sendCrashAnalytics(context, "Load Campaign Api Fails" + s);
                                printErrorLogs(s);
                            }
                        });

            } catch (Exception e) {
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                printErrorLogs(e.toString());
            }
        }
    }


    public void allowAnonymousRegistration(boolean value) {
        allowAnonymousRegistration = value;
    }


    public void openWalletAsFallback(boolean value) {
        allowOpenWallet = value;
    }


    /**
     * Used to open wallet
     */
    public void openWallet(Context context) {
        if (!sdk_disable) {

            if ((isOnline(context))) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_WALLET_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        loadCGCampaign(context, CG_OPEN_WALLET, nudgeConfiguration);


                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register token not Present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }

    }


    public void openWallet(Context context, boolean closeOnDeepLink) {
        if (!sdk_disable) {

            if ((isOnline(context))) {
                String isClosed = "false";
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        metaData.add(new MetaData("closeOnDeepLink - ", "" + closeOnDeepLink));
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_WALLET_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

                        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();

                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        nudgeConfiguration.setCloseOnDeepLink(closeOnDeepLink);

                        loadCGCampaign(context, CG_OPEN_WALLET, nudgeConfiguration);

//

                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register token not Present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }

    }

    public void openWallet(Context context, NudgeConfiguration nudgeConfiguration) {
        if (!sdk_disable) {

            if ((isOnline(context))) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        Gson gson = new Gson();
                        String config = gson.toJson(nudgeConfiguration);
                        metaData.add(new MetaData("nudgeConfiguration - ", "" + config));
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_OPEN_WALLET_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        loadCGCampaign(context, CG_OPEN_WALLET, nudgeConfiguration);

                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register token not Present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }

    }

    public void sendInvalidCampaignIdCallback(Context context, String campaign_id) {
        try {
            JSONObject jsonObject = new JSONObject(CGConstants.INVALID_CAMPAIGN_ID);
            JSONObject data = jsonObject.getJSONObject("data");
            data.put("campaignId", campaign_id);
            Intent intent = new Intent("CG_INVALID_CAMPAIGN_ID");
            intent.putExtra("data", data.toString());
            context.sendBroadcast(intent);
        } catch (Exception e) {
            printErrorLogs("CG_INVALID_CAMPAIGN_ID callback fails " + e);
        }
    }

    private void loadCGCampaign(Context context, String campaign_id, NudgeConfiguration
            nudgeConfiguration) {
        final boolean[] isThere = {false};
        if (campaign_id != null) {

            if (campaign_id.equalsIgnoreCase(CG_OPEN_WALLET)) {

                if (MQTT_STATE_SYNC && loadCampaignResponse != null && loadCampaignResponse.defaultUrl != null && !loadCampaignResponse.defaultUrl.isEmpty() && isMqttEnabled && isMqttConnected) {
                    displayCGNudge(context, loadCampaignResponse.defaultUrl, nudgeConfiguration);
                } else {
                    CustomerGlu.getInstance().retrieveData(context, new RewardInterface() {
                        @Override
                        public void onSuccess(RewardModel rewardModel) {
                            String default_url = rewardModel.defaultUrl;
                            displayCGNudge(context, default_url, nudgeConfiguration);
                        }

                        @Override
                        public void onFailure(String message) {
                            displayCGNudge(context, ERROR_URL, nudgeConfiguration);

                        }
                    });
                }

            } else if (campaign_id.equalsIgnoreCase("")) {
                checkOpenWalletOrNot(context, campaign_id, nudgeConfiguration);
            } else {
                if (MQTT_STATE_SYNC && loadCampaignResponse != null && loadCampaignResponse.getCampaigns() != null && isMqttEnabled && isMqttConnected) {
                    Campaigns campaigns = new Campaigns(campaign_id);
                    if (loadCampaignResponse.getCampaigns().size() > 0 && loadCampaignResponse.getCampaigns().contains(campaigns)) {
                        String url = loadCampaignResponse.campaigns.get(loadCampaignResponse.getCampaigns().lastIndexOf(campaigns)).getUrl();
                        displayCGNudge(context, url, nudgeConfiguration);
                    } else {
                        checkOpenWalletOrNot(context, campaign_id, nudgeConfiguration);
                    }
                } else {
                    CustomerGlu.getInstance().retrieveData(context, new RewardInterface() {
                        @Override
                        public void onSuccess(RewardModel rewardModel) {
                            isThere[0] = false;
                            Gson gson = new Gson();
                            String json = gson.toJson(rewardModel);


                            if (rewardModel.getCampaigns() != null && rewardModel.getCampaigns().size() <= 0) {

                                String default_url = rewardModel.defaultUrl;
                                if (allowOpenWallet) {

                                    displayCGNudge(context, default_url, nudgeConfiguration);
                                }
                                sendInvalidCampaignIdCallback(context, campaign_id);
                                // Toast.makeText(getApplicationContext(), "Invalid CampaignId", Toast.LENGTH_SHORT).show();
                            } else {
                                String json1 = gson.toJson(rewardModel);

                                for (int i = 0; i < rewardModel.getCampaigns().size(); i++) {
                                    System.out.println(i);
                                    String tag = "";

                                    if (rewardModel.getCampaigns().get(i).getBanner() != null && rewardModel.getCampaigns().get(i).getBanner().getTag() != null) {
                                        tag = rewardModel.getCampaigns().get(i).getBanner().getTag();
                                    }


                                    String id = rewardModel.getCampaigns().get(i).getCampaignId();


                                    if (campaign_id.equalsIgnoreCase(id) || campaign_id.equalsIgnoreCase(tag)) {
                                        //   System.out.println(tag);
                                        String url = rewardModel.getCampaigns().get(i).getUrl();
                                        //   System.out.println(url);
                                        //     Toast.makeText(OpenCustomerGluWeb.this, url, Toast.LENGTH_SHORT).show();
                                        isThere[0] = true;

                                        displayCGNudge(context, url, nudgeConfiguration);
                                        break;
                                    }

                                }
                                if (!isThere[0]) {
                                    sendInvalidCampaignIdCallback(context, campaign_id);
                                    String default_url = rewardModel.defaultUrl;
                                    if (allowOpenWallet) {
                                        displayCGNudge(context, default_url, nudgeConfiguration);
                                    }
                                }

                            }


                        }

                        @Override
                        public void onFailure(String message) {

                            String url = "https://end-user-ui.customerglu.com/error/?source=native-sdk&code=" + 504 + "&message=" + "Please Try again later";
                            displayCGNudge(context, url, nudgeConfiguration);
                        }
                    });
                }
            }
        } else {
            sendInvalidCampaignIdCallback(context, null);
        }

    }
        // Use of this method is only to check open wallet or not
    private void checkOpenWalletOrNot(Context context, String campaign_id, NudgeConfiguration nudgeConfiguration) {
        sendInvalidCampaignIdCallback(context, campaign_id);
        if (allowOpenWallet && MQTT_STATE_SYNC && loadCampaignResponse != null && loadCampaignResponse.defaultUrl != null && !loadCampaignResponse.defaultUrl.isEmpty() && isMqttEnabled && isMqttConnected) {
            displayCGNudge(context, loadCampaignResponse.defaultUrl, nudgeConfiguration);
        } else {
            if (allowOpenWallet) {
                CustomerGlu.getInstance().retrieveData(context, new RewardInterface() {
                    @Override
                    public void onSuccess(RewardModel rewardModel) {
                        String default_url = rewardModel.defaultUrl;
                        displayCGNudge(context, default_url, nudgeConfiguration);
                    }

                    @Override
                    public void onFailure(String message) {
                        displayCGNudge(context, ERROR_URL, nudgeConfiguration);

                    }
                });
            }
        }
    }

    /**
     * Used to open list of all campaigns
     */
    @Deprecated
    public void loadAllCampaigns(Context context) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {

                        Intent web = new Intent(context, RewardScreen.class);
                        web.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(web);

                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register token not Present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }

    }

    @Deprecated
    public void loadCampaignsByFilter(Context context, Map<String, Object> filter_params) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        Intent web = new Intent(context, FilterReward.class);
                        web.putExtra("params", (Serializable) filter_params);

                        web.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(web);
                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());
                    }
                } else {
                    printErrorLogs("Please register token not Present");

                }
                printErrorLogs("No Internet Connection");
            }
        }
    }

    /**
     * Used to open a campaign with respective campaign_id
     */
    public void loadCampaignById(Context context, String id) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        metaData.add(new MetaData("campaignId - ", "" + id));
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_BY_ID_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

                        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();

                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        loadCGCampaign(context, id, nudgeConfiguration);

//                        Intent web = new Intent(context, OpenCustomerGluWeb.class);
//                        web.putExtra("campaign_id", id);
//                        web.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(web);

                    } catch (Exception e) {
                        printErrorLogs(e.toString());
                        String s = e.toString();
                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                    }
                } else {
                    printErrorLogs("Please register token not Present");

                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }
    }

    public void loadCampaignById(Context context, String id, boolean closeOnDeepLink) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> metaData = new ArrayList<>();
                        metaData.add(new MetaData("campaignId - ", "" + id));
                        metaData.add(new MetaData("closeOnDeepLink - ", "" + closeOnDeepLink));
                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_BY_ID_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();

                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        if (closeOnDeepLink) {
                            nudgeConfiguration.setCloseOnDeepLink(true);
                        }

                        loadCGCampaign(context, id, nudgeConfiguration);


                    } catch (Exception e) {
                        printErrorLogs(e.toString());
                        String s = e.toString();
                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                    }
                } else {
                    printErrorLogs("Please register token not Present");

                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }
    }

    public void loadCampaignById(Context context, String id, NudgeConfiguration
            nudgeConfiguration) {
        if (!sdk_disable) {

            if (isOnline(context)) {
                Gson gson = new Gson();
                String config = gson.toJson(nudgeConfiguration);
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("campaignId - ", "" + id));
                metaData.add(new MetaData("nudgeConfiguration - ", "" + config));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_LOAD_CAMPAIGN_BY_ID_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        if (isPrecachingEnable) {
                            enablePrecaching(context);
                        }
                        loadCGCampaign(context, id, nudgeConfiguration);
                    } catch (Exception e) {
                        printErrorLogs(e.toString());
                        String s = e.toString();
                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                    }
                } else {
                    printErrorLogs("Please register token not Present");

                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }
    }

    public void testIntegration() {
        Intent intent = new Intent(globalContext, ClientTestingPage.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        globalContext.startActivity(intent);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public static void displayNotification(Context context, String title, String
            message, String url, int icon, String type, String absoluteHeight, String relativeHeight,
                                           double opacity, String isClosed, String nudge_id, String campaign_id) {
        try {
            Intent intent = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("body", message);
            jsonObject.put("type", "push");
            jsonObject.put("nudge_layout", type);
            jsonObject.put("nudge_id", nudge_id);
            jsonObject.put("click_action", url);
            jsonObject.put("campaign_id", campaign_id);
            if (type.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                intent = new Intent(context, BottomSheet.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else if (type.equalsIgnoreCase(CGConstants.BOTTOM_DEFAULT_NOTIFICATION) || type.equalsIgnoreCase(CGConstants.BOTTOM_POPUP)) {
                intent = new Intent(context, BottomDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());

                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else if (type.equalsIgnoreCase(CGConstants.MIDDLE_NOTIFICATION) || type.equalsIgnoreCase(CGConstants.MIDDLE_POPUP)) {
                intent = new Intent(context, MiddleDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent = new Intent(context, NotificationWeb.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            }
            PendingIntent pendingIntent = null;
            int reqCode = (int) (Math.random() * (400 - 200 + 1) + 200);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);

            } else {
                pendingIntent = PendingIntent.getActivity(context, reqCode, intent, 0);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2345")
                    .setSmallIcon(icon)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(message))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(PRIORITY_MAX)
                    .setDefaults(
                            DEFAULT_ALL
                    );
            NotificationManager manager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                manager = context.getSystemService(NotificationManager.class);
            } else {
                manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("2345", "CustomerGlu", NotificationManager.IMPORTANCE_HIGH);

                manager.createNotificationChannel(notificationChannel);
            }
            intent.replaceExtras(new Bundle());
            manager.notify(2345, builder.build());

        } catch (Exception e) {
            printErrorLogs(e.toString());
        }


    }

    /**
     * Used to display/handle CustomerGlu Nudges without Opacity
     */

    public void displayCustomerGluNotification(Context context, JSONObject json, int icon,
                                               double opacity) {
        JSONObject data = null;
        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String isClosed = "false";
                String image = "";
                String absoluteHeight = "0";
                String relativeHeight = "0";
                this.opacity = opacity;
                data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("type")) {
                    String type = data.getString("type");
                    String notification_type = "";
                    String glu_message_type = "";
                    String url = "";
                    if (type.equalsIgnoreCase("CustomerGlu")) {
                        if (data.has("nudge_url")) {
                            url = data.getString("nudge_url");
                        }
                        String body = data.getString("body");
                        String title = data.getString("title");
                        if (data.has("glu_message_type")) {
                            glu_message_type = data.getString("glu_message_type");
                        }
                        if (data.has("image")) {
                            image = data.getString("image");
                        }
                        if (data.has("page_type")) {
                            notification_type = data.getString("page_type");
                        }
                        if (data.has("absoluteHeight")) {
                            absoluteHeight = data.getString("absoluteHeight");
                        }
                        if (data.has("relativeHeight")) {
                            relativeHeight = data.getString("relativeHeight");
                        }
                        if (data.has("closeOnDeepLink")) {
                            isClosed = data.getString("closeOnDeepLink");
                        }
                        String nudge_id = "";
                        String campaign_id = "";
                        if (data.has("campaign_id")) {
                            campaign_id = data.getString("campaign_id");
                        }
                        if (data.has("nudge_id")) {
                            nudge_id = data.getString("nudge_id");
                        }

                        if (glu_message_type.equalsIgnoreCase("in-app")) {
                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            nudgeData.put("nudge_id", nudge_id);
                            cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                            handleInAppNotifications(context, url, notification_type, "false", opacity, absoluteHeight, relativeHeight);
                        } else {
                            if (image.equalsIgnoreCase("")) {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayNotification(context, title, body, url, icon, notification_type, absoluteHeight, relativeHeight, opacity, isClosed, nudge_id, campaign_id);

                            } else {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayExpandedNotification(context, title, body, url, image, icon, notification_type, absoluteHeight, relativeHeight, opacity, isClosed, nudge_id, campaign_id);
                            }
                        }


                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }

    public void displayCustomerGluNotification(Context context, JSONObject json, int icon,
                                               double opacity, boolean closeNudgeOnDeepLink) {
        JSONObject data = null;
        String isClosed = "false";
        if (closeNudgeOnDeepLink) {
            isClosed = "true";
        }
        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String image = "";
                this.opacity = opacity;
                String absoluteHeight = "0";
                String relativeHeight = "0";
                data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("type")) {
                    String type = data.getString("type");
                    String notification_type = "";
                    String glu_message_type = "";
                    String url = "";
                    if (type.equalsIgnoreCase("CustomerGlu")) {
                        if (data.has("nudge_url")) {
                            url = data.getString("nudge_url");
                        }
                        String body = data.getString("body");
                        String title = data.getString("title");
                        if (data.has("glu_message_type")) {
                            glu_message_type = data.getString("glu_message_type");
                        }
                        if (data.has("image")) {
                            image = data.getString("image");
                        }
                        if (data.has("page_type")) {
                            notification_type = data.getString("page_type");
                        }
                        if (data.has("absoluteHeight")) {
                            absoluteHeight = data.getString("absoluteHeight");
                        }
                        if (data.has("relativeHeight")) {
                            relativeHeight = data.getString("relativeHeight");
                        }
                        if (data.has("closeOnDeepLink")) {
                            isClosed = data.getString("closeOnDeepLink");
                        }
                        String nudge_id = "";
                        String campaign_id = "";
                        if (data.has("campaign_id")) {
                            campaign_id = data.getString("campaign_id");
                        }
                        if (data.has("nudge_id")) {
                            nudge_id = data.getString("nudge_id");
                        }

                        if (glu_message_type.equalsIgnoreCase("in-app")) {
                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            nudgeData.put("nudge_id", nudge_id);
                            cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                            handleInAppNotifications(context, url, notification_type, isClosed, opacity, absoluteHeight, relativeHeight);

                        } else {
                            if (image.equalsIgnoreCase("")) {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayNotification(context, title, body, url, icon, notification_type, absoluteHeight, relativeHeight, opacity, isClosed, nudge_id, campaign_id);

                            } else {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayExpandedNotification(context, title, body, url, image, icon, notification_type, absoluteHeight, relativeHeight, opacity, isClosed, nudge_id, campaign_id);
                            }
                        }


                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }


    public void displayCustomerGluNotification(Context context, JSONObject json, int icon) {
        JSONObject data = null;
        String isClosed = "false";
        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                String absoluteHeight = "0";
                String relativeHeight = "0";
                if (data.has("type")) {
                    String image = "";
                    String type = data.getString("type");
                    String notification_type = "";
                    String glu_message_type = "";
                    String url = "";
                    if (type.equalsIgnoreCase("CustomerGlu")) {
                        if (data.has("nudge_url")) {
                            url = data.getString("nudge_url");
                        }
                        String body = data.getString("body");
                        String title = data.getString("title");
                        if (data.has("glu_message_type")) {
                            glu_message_type = data.getString("glu_message_type");
                        }
                        if (data.has("image")) {
                            image = data.getString("image");
                        }
                        if (data.has("page_type")) {
                            notification_type = data.getString("page_type");
                        }
                        if (data.has("absoluteHeight")) {
                            absoluteHeight = data.getString("absoluteHeight");
                        }
                        if (data.has("relativeHeight")) {
                            relativeHeight = data.getString("relativeHeight");
                        }
                        if (data.has("closeOnDeepLink")) {
                            isClosed = data.getString("closeOnDeepLink");
                        }
                        String nudge_id = "";
                        String campaign_id = "";
                        if (data.has("campaign_id")) {
                            campaign_id = data.getString("campaign_id");
                        }
                        if (data.has("nudge_id")) {
                            nudge_id = data.getString("nudge_id");
                        }

                        if (glu_message_type.equalsIgnoreCase("in-app")) {
                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            nudgeData.put("nudge_id", nudge_id);
                            cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                            handleInAppNotifications(context, url, notification_type, "false", 0.5, absoluteHeight, relativeHeight);
                        } else {
                            if (image.equalsIgnoreCase("")) {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayNotification(context, title, body, url, icon, notification_type, absoluteHeight, relativeHeight, 0.5, isClosed, nudge_id, campaign_id);

                            } else {
                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, NOTIFICATION_LOAD, nudgeData);
                                displayExpandedNotification(context, title, body, url, image, icon, notification_type, absoluteHeight, relativeHeight, 0.5, isClosed, nudge_id, campaign_id);
                            }
                        }


                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }



    /* Used to check token Expiration*/

    public String getReferralId(Uri deeplink) {
        String referrerUserId = "";
        if (sdk_disable) {
            return "";
        } else {
            if (deeplink.getQueryParameter("userId") != null) {
                referrerUserId = deeplink.getQueryParameter("userId");
            }

            return referrerUserId;
        }
    }

    public void displayCustomerGluBackgroundNotification(Context context, JSONObject json) {
        String isClosed = "false";

        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String absoluteHeight = "0";
                String relativeHeight = "0";
                this.opacity = 0.5;
                JSONObject data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("google.message_id")) {
                    String firebaseId = data.getString("google.message_id");
                    if (firebaseMessageId.isEmpty() || !firebaseId.equalsIgnoreCase(firebaseMessageId)) {
                        firebaseMessageId = firebaseId;
                        if (data.has("type")) {
                            String type = data.getString("type");
                            String notification_type = "";
                            String glu_message_type = "";
                            String url = "";
                            if (type.equalsIgnoreCase("CustomerGlu")) {


                                if (data.has("nudge_url")) {
                                    url = data.getString("nudge_url");
                                }
                                if (data.has("page_type")) {
                                    notification_type = data.getString("page_type");
                                }
                                if (data.has("absoluteHeight")) {
                                    absoluteHeight = data.getString("absoluteHeight");
                                }
                                if (data.has("relativeHeight")) {
                                    relativeHeight = data.getString("relativeHeight");
                                }
                                if (data.has("closeOnDeepLink")) {
                                    isClosed = data.getString("closeOnDeepLink");
                                }
                                String body = data.getString("body");
                                String title = data.getString("title");
                                if (data.has("glu_message_type")) {
                                    glu_message_type = data.getString("glu_message_type");
                                }
                                String nudge_id = "";
                                String campaign_id = "";
                                if (data.has("campaign_id")) {
                                    campaign_id = data.getString("campaign_id");
                                }
                                if (data.has("nudge_id")) {
                                    nudge_id = data.getString("nudge_id");
                                }

                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_id", nudge_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                                handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                            }
                        }
                    }
                } else {
                    String glu_message_type = "";

                    if (data.has("type")) {
                        String type = data.getString("type");
                        String notification_type = "";
                        String url = "";
                        if (type.equalsIgnoreCase("CustomerGlu")) {
                            if (data.has("nudge_url")) {
                                url = data.getString("nudge_url");
                            }
                            if (data.has("page_type")) {
                                notification_type = data.getString("page_type");
                            }
                            if (data.has("absoluteHeight")) {
                                absoluteHeight = data.getString("absoluteHeight");
                            }
                            if (data.has("relativeHeight")) {
                                relativeHeight = data.getString("relativeHeight");
                            }
                            if (data.has("closeOnDeepLink")) {
                                isClosed = data.getString("closeOnDeepLink");
                            }
                            String body = data.getString("body");
                            String title = data.getString("title");
                            if (data.has("glu_message_type")) {
                                glu_message_type = data.getString("glu_message_type");
                            }
                            String nudge_id = "";
                            String campaign_id = "";
                            if (data.has("campaign_id")) {
                                campaign_id = data.getString("campaign_id");
                            }
                            if (data.has("nudge_id")) {
                                nudge_id = data.getString("nudge_id");
                            }

                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            nudgeData.put("nudge_id", nudge_id);
                            cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                            handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                        }
                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }

    public void displayCustomerGluBackgroundNotification(Context context, JSONObject json,
                                                         double opacity, boolean closeNudgeOnDeepLink) {
        String isClosed = "false";
        if (closeNudgeOnDeepLink) {
            isClosed = "true";
        }
        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String absoluteHeight = "0";
                String relativeHeight = "0";
                this.opacity = opacity;
                JSONObject data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("google.message_id")) {
                    String firebaseId = data.getString("google.message_id");
                    if (firebaseMessageId.isEmpty() || !firebaseId.equalsIgnoreCase(firebaseMessageId)) {
                        firebaseMessageId = firebaseId;
                        if (data.has("type")) {
                            String type = data.getString("type");
                            String notification_type = "";
                            String glu_message_type = "";
                            String url = "";
                            if (type.equalsIgnoreCase("CustomerGlu")) {
                                if (data.has("nudge_url")) {
                                    url = data.getString("nudge_url");
                                }
                                if (data.has("page_type")) {
                                    notification_type = data.getString("page_type");
                                }
                                if (data.has("absoluteHeight")) {
                                    absoluteHeight = data.getString("absoluteHeight");
                                }
                                if (data.has("relativeHeight")) {
                                    relativeHeight = data.getString("relativeHeight");
                                }
                                if (data.has("closeOnDeepLink")) {
                                    isClosed = data.getString("closeOnDeepLink");
                                }
                                String body = data.getString("body");
                                String title = data.getString("title");
                                if (data.has("glu_message_type")) {
                                    glu_message_type = data.getString("glu_message_type");
                                }
                                String nudge_id = "";
                                String campaign_id = "";
                                if (data.has("campaign_id")) {
                                    campaign_id = data.getString("campaign_id");
                                }
                                if (data.has("nudge_id")) {
                                    nudge_id = data.getString("nudge_id");
                                }

                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                                handleInAppNotifications(context, url, notification_type, isClosed, opacity, absoluteHeight, relativeHeight);


                            }
                        }
                    }
                } else {
                    String glu_message_type = "";

                    if (data.has("type")) {
                        String type = data.getString("type");
                        String notification_type = "";
                        String url = "";
                        if (type.equalsIgnoreCase("CustomerGlu")) {
                            if (data.has("nudge_url")) {
                                url = data.getString("nudge_url");
                            }
                            if (data.has("page_type")) {
                                notification_type = data.getString("page_type");
                            }
                            if (data.has("absoluteHeight")) {
                                absoluteHeight = data.getString("absoluteHeight");
                            }
                            if (data.has("relativeHeight")) {
                                relativeHeight = data.getString("relativeHeight");
                            }
                            if (data.has("closeOnDeepLink")) {
                                isClosed = data.getString("closeOnDeepLink");
                            }
                            String body = data.getString("body");
                            String title = data.getString("title");
                            if (data.has("glu_message_type")) {
                                glu_message_type = data.getString("glu_message_type");
                            }
                            String nudge_id = "";
                            String campaign_id = "";
                            if (data.has("campaign_id")) {
                                campaign_id = data.getString("campaign_id");
                            }
                            if (data.has("nudge_id")) {
                                nudge_id = data.getString("nudge_id");
                            }

                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            nudgeData.put("nudge_id", nudge_id);
                            cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                            handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                        }
                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }

    public void displayCustomerGluBackgroundNotification(Context context, JSONObject json,
                                                         double opacity) {
        String isClosed = "false";
        if (!sdk_disable) {

            try {
                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String absoluteHeight = "0";
                String relativeHeight = "0";
                this.opacity = opacity;
                JSONObject data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("google.message_id")) {
                    String firebaseId = data.getString("google.message_id");
                    if (firebaseMessageId.isEmpty() || !firebaseId.equalsIgnoreCase(firebaseMessageId)) {
                        firebaseMessageId = firebaseId;
                        if (data.has("type")) {
                            String type = data.getString("type");
                            String notification_type = "";
                            String glu_message_type = "";
                            String url = "";
                            if (type.equalsIgnoreCase("CustomerGlu")) {
                                if (data.has("nudge_url")) {
                                    url = data.getString("nudge_url");
                                }
                                if (data.has("page_type")) {
                                    notification_type = data.getString("page_type");
                                }
                                if (data.has("absoluteHeight")) {
                                    absoluteHeight = data.getString("absoluteHeight");
                                }
                                if (data.has("relativeHeight")) {
                                    relativeHeight = data.getString("relativeHeight");
                                }
                                if (data.has("closeOnDeepLink")) {
                                    isClosed = data.getString("closeOnDeepLink");
                                }
                                String body = data.getString("body");
                                String title = data.getString("title");
                                if (data.has("glu_message_type")) {
                                    glu_message_type = data.getString("glu_message_type");
                                }
                                String nudge_id = "";
                                String campaign_id = "";
                                if (data.has("campaign_id")) {
                                    campaign_id = data.getString("campaign_id");
                                }
                                if (data.has("nudge_id")) {
                                    nudge_id = data.getString("nudge_id");
                                }

                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                nudgeData.put("nudge_id", nudge_id);
                                cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                                handleInAppNotifications(context, url, notification_type, isClosed, opacity, absoluteHeight, relativeHeight);


                            }
                        }
                    }
                } else {

                    if (data.has("type")) {
                        String type = data.getString("type");
                        String notification_type = "";
                        String glu_message_type = "";
                        String url = "";
                        if (type.equalsIgnoreCase("CustomerGlu")) {
                            if (data.has("nudge_url")) {
                                url = data.getString("nudge_url");
                            }
                            if (data.has("page_type")) {
                                notification_type = data.getString("page_type");
                            }
                            if (data.has("absoluteHeight")) {
                                absoluteHeight = data.getString("absoluteHeight");
                            }
                            if (data.has("relativeHeight")) {
                                relativeHeight = data.getString("relativeHeight");
                            }
                            if (data.has("closeOnDeepLink")) {
                                isClosed = data.getString("closeOnDeepLink");
                            }
                            String body = data.getString("body");
                            String title = data.getString("title");
                            if (data.has("glu_message_type")) {
                                glu_message_type = data.getString("glu_message_type");
                            }
                            String nudge_id = "";
                            String campaign_id = "";
                            if (data.has("campaign_id")) {
                                campaign_id = data.getString("campaign_id");
                            }
                            if (data.has("nudge_id")) {
                                nudge_id = data.getString("nudge_id");
                            }

                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_id", nudge_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                            handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                        }
                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }

    public void displayCustomerGluBackgroundNotification(Context context, JSONObject json,
                                                         boolean closeNudgeOnDeepLink) {
        String isClosed = "false";
        if (closeNudgeOnDeepLink) {
            isClosed = "true";
        }
        if (!sdk_disable) {

            try {

                ArrayList<MetaData> metaData = new ArrayList<>();
                metaData.add(new MetaData("Notification Payload", json.toString()));
                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                if (isPrecachingEnable) {
                    enablePrecaching(context);
                }
                String absoluteHeight = "0";
                String relativeHeight = "0";
                this.opacity = 0.5;
                JSONObject data = new JSONObject(String.valueOf(json));
                printDebugLogs(data.toString());
                if (data.has("google.message_id")) {
                    String firebaseId = data.getString("google.message_id");
                    if (firebaseMessageId.isEmpty() || !firebaseId.equalsIgnoreCase(firebaseMessageId)) {
                        firebaseMessageId = firebaseId;
                        if (data.has("type")) {
                            String type = data.getString("type");
                            String notification_type = "";
                            String glu_message_type = "";
                            String url = "";
                            if (type.equalsIgnoreCase("CustomerGlu")) {
                                if (data.has("nudge_url")) {
                                    url = data.getString("nudge_url");
                                }

                                if (data.has("page_type")) {
                                    notification_type = data.getString("page_type");
                                }
                                if (data.has("absoluteHeight")) {
                                    absoluteHeight = data.getString("absoluteHeight");
                                }
                                if (data.has("relativeHeight")) {
                                    relativeHeight = data.getString("relativeHeight");
                                }
                                if (data.has("closeOnDeepLink")) {
                                    isClosed = data.getString("closeOnDeepLink");
                                }
                                String body = data.getString("body");
                                String title = data.getString("title");
                                if (data.has("glu_message_type")) {
                                    glu_message_type = data.getString("glu_message_type");
                                }
                                String nudge_id = "";
                                String campaign_id = "";
                                if (data.has("campaign_id")) {
                                    campaign_id = data.getString("campaign_id");
                                }
                                if (data.has("nudge_id")) {
                                    nudge_id = data.getString("nudge_id");
                                }

                                Map<String, Object> nudgeData = new HashMap<>();
                                nudgeData.put("type", glu_message_type);
                                nudgeData.put("title", title);
                                nudgeData.put("body", body);
                                nudgeData.put("campaign_id", campaign_id);
                                nudgeData.put("nudge_id", nudge_id);
                                nudgeData.put("nudge_layout", notification_type);
                                nudgeData.put("click_action", url);
                                cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                                handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                            }
                        }
                    }
                } else {

                    if (data.has("type")) {
                        String type = data.getString("type");
                        String notification_type = "";
                        String glu_message_type = "";
                        String url = "";
                        if (type.equalsIgnoreCase("CustomerGlu")) {
                            if (data.has("nudge_url")) {
                                url = data.getString("nudge_url");
                            }
                            if (data.has("page_type")) {
                                notification_type = data.getString("page_type");
                            }
                            if (data.has("absoluteHeight")) {
                                absoluteHeight = data.getString("absoluteHeight");
                            }
                            if (data.has("relativeHeight")) {
                                relativeHeight = data.getString("relativeHeight");
                            }
                            if (data.has("closeOnDeepLink")) {
                                isClosed = data.getString("closeOnDeepLink");
                            }
                            String body = data.getString("body");
                            String title = data.getString("title");
                            if (data.has("glu_message_type")) {
                                glu_message_type = data.getString("glu_message_type");
                            }
                            String nudge_id = "";
                            String campaign_id = "";
                            if (data.has("campaign_id")) {
                                campaign_id = data.getString("campaign_id");
                            }
                            if (data.has("nudge_id")) {
                                nudge_id = data.getString("nudge_id");
                            }

                            Map<String, Object> nudgeData = new HashMap<>();
                            nudgeData.put("type", glu_message_type);
                            nudgeData.put("title", title);
                            nudgeData.put("body", body);
                            nudgeData.put("campaign_id", campaign_id);
                            nudgeData.put("nudge_id", nudge_id);
                            nudgeData.put("nudge_layout", notification_type);
                            nudgeData.put("click_action", url);
                            cgAnalyticsEventManager(context, PUSH_NOTIFICATION_CLICK, nudgeData);
                            handleInAppNotifications(context, url, notification_type, isClosed, 0.5, absoluteHeight, relativeHeight);


                        }
                    }
                }
            } catch (JSONException e) {
                printErrorLogs(e.toString());
                String s = e.toString();

                CustomerGlu.getInstance().sendCrashAnalytics(context, s);
            }
        }


    }


    private void handleInAppNotifications(Context context, String url, String type, String
            isClosed, double opacity, String absoluteHeight, String relativeHeight) {

        try {
            if (type.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomSheet.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (type.equalsIgnoreCase(CGConstants.BOTTOM_DEFAULT_NOTIFICATION)) {
                Intent intent = new Intent(context, BottomDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (type.equalsIgnoreCase(CGConstants.MIDDLE_NOTIFICATION)) {
                Intent intent = new Intent(context, MiddleDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, NotificationWeb.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    private void displayExpandedNotification(Context context, String title, String
            message, String url, String image, int icon, String type, String absoluteHeight, String
                                                     relativeHeight, double opacity, String isClosed, String nudge_id, String campaign_id) {
        try {
            Intent intent = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("body", message);
            jsonObject.put("type", "push");
            jsonObject.put("nudge_layout", type);
            jsonObject.put("nudge_id", nudge_id);
            jsonObject.put("click_action", url);
            jsonObject.put("campaign_id", campaign_id);
            if (type.equalsIgnoreCase(CGConstants.BOTTOM_SHEET_NOTIFICATION)) {
                intent = new Intent(context, BottomSheet.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else if (type.equalsIgnoreCase(CGConstants.BOTTOM_DEFAULT_NOTIFICATION) || type.equalsIgnoreCase(CGConstants.BOTTOM_POPUP)) {
                intent = new Intent(context, BottomDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());

                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else if (type.equalsIgnoreCase(CGConstants.MIDDLE_NOTIFICATION) || type.equalsIgnoreCase(CGConstants.MIDDLE_POPUP)) {
                intent = new Intent(context, MiddleDialog.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("opacity", String.valueOf(opacity));
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent = new Intent(context, NotificationWeb.class);
                intent.putExtra("nudge_url", url);
                intent.putExtra("closeOnDeepLink", isClosed);
                intent.putExtra("absoluteHeight", absoluteHeight);
                intent.putExtra("relativeHeight", relativeHeight);
                intent.putExtra("notification", jsonObject.toString());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            }
            PendingIntent pendingIntent;
            int reqCode = (int) (Math.random() * (400 - 200 + 1) + 200);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(context, reqCode, intent, 0);

            }
            Bitmap bmp = null;
            try {
                InputStream in = new URL(image).openStream();
                bmp = BitmapFactory.decodeStream(in);
                printDebugLogs("image decode");

            } catch (IOException e) {
                e.printStackTrace();
                printErrorLogs(e.toString());

            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2345")
                    .setSmallIcon(icon)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(message))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(PRIORITY_MAX)
                    .setLargeIcon(bmp)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bmp)
                            .bigLargeIcon(null))
                    .setDefaults(DEFAULT_ALL);
            NotificationManager manager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                manager = context.getSystemService(NotificationManager.class);
            } else {
                manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("2345", "CustomerGlu", NotificationManager.IMPORTANCE_HIGH);

                manager.createNotificationChannel(notificationChannel);

            }
            manager.notify(2345, builder.build());


        } catch (Exception e) {
            printErrorLogs(e.toString());
        }
    }

    private boolean validateToken(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            JSONObject jsonObject = new JSONObject(getJson(split[1]));
            String exp = jsonObject.getString("exp");
            int exp_date = Integer.parseInt(exp);

            java.util.Date dateTime = new java.util.Date((long) Double.valueOf(exp_date).longValue() * 1000);
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            final String token_exp_date = df.format(dateTime);
            if (compareDate(token_exp_date)) {
                return true;
            }

        } catch (UnsupportedEncodingException e) {
            printErrorLogs(e.toString());


        }
        return false;
    }

    private boolean compareDate(String exp_date) {
        try {
            Date currentdate = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String currentDate = sdformat.format(currentdate);

            Date token_exp_date = null;
            Date d2 = null;
            token_exp_date = sdformat.parse(exp_date);
            d2 = sdformat.parse(currentDate);
            if (token_exp_date != null) {
            }


            if (token_exp_date.compareTo(d2) > 0) {
                //   System.out.println("Not Expired");
                return true;
            } else if (token_exp_date.compareTo(d2) < 0) {
                // System.out.println("Expired");
            } else if (token_exp_date.compareTo(d2) == 0) {
                // System.out.println("Both dates are equal");
            }

        } catch (Exception e) {
            printErrorLogs(String.valueOf(e));
        }
        return false;
    }


    /**
     * Used to send activity completion Events to CustomerGlu
     */

    public void sendEvent(Context context, String
            eventName, Map<String, Object> eventProperties) {
        if (!sdk_disable) {
            boolean writeKeyPresent = false;
            boolean userRegistered = false;
            if (!getWriteKey(context).isEmpty()) {
                writeKeyPresent = true;
            }

            if (!Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
                userRegistered = true;
            }
            ArrayList<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("writeKeyPresent", "" + writeKeyPresent));
            metaData.add(new MetaData("userRegistered", "" + userRegistered));
            metaData.add(new MetaData("eventName", "" + eventName));
            metaData.add(new MetaData("eventProperties", "" + eventProperties));
            diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_SEND_EVENT_START, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

            if (isOnline(context)) {
                String token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);
                if (!token.isEmpty()) {
                    try {
                        ArrayList<MetaData> callMetaData = new ArrayList<>();
                        callMetaData.add(new MetaData("writeKey", "" + writeKey));
                        callMetaData.add(new MetaData("token", "" + token));
                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_SERVER_EVENTS_CALLED, CGConstants.CG_LOGGING_EVENTS.METRICS, metaData);
                        String cust_token = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN);

                        if (!validateToken(cust_token)) {
                            Map<String, Object> registerData = new HashMap<>();

                            updateProfile(context, registerData, new DataListner() {
                                @Override
                                public void onSuccess(RegisterModal registerModal) {

                                }

                                @Override
                                public void onFail(String message) {

                                }
                            });

                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        EventData eventData = new EventData();
                        String uniqueId = UUID.randomUUID().toString();
                        String userid;
                        if (isLoginWithUserId) {
                            userid = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);

                        } else {
                            userid = Prefs.getEncKey(context, ANONYMOUSID);
                        }
                        eventData.setUser_id(userid);
                        eventData.setEvent_id(uniqueId);
                        eventData.setEvent_name(eventName);
                        eventData.setEventProperties(eventProperties);
                        String write_key = getWriteKey(context);
                        eventData.setTimestamp(currentDateandTime);
                        Gson gson = new Gson();
                        String json = gson.toJson(eventData);
                        printDebugLogs("EventData body " + json);
                        registerModal = new RegisterModal();
                        mService = Comman.getApiToken();
                        registerModal = new RegisterModal();
                        CGAPIHelper.enqueueWithRetry(mService.sendEvents(write_key, eventData), new Callback<RegisterModal>() {
                            @Override
                            public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                                try {

                                    printDebugLogs("Send Event API " + response.code());
                                    ArrayList<MetaData> responseMetaData = new ArrayList<>();
                                    Gson gson = new Gson();
                                    String responseBody = gson.toJson(response.body());
                                    responseMetaData.add(new MetaData("Send Event  API response code", "" + response.code()));
                                    responseMetaData.add(new MetaData("Send Event  API response body - ", responseBody));
                                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
                                    if (response.code() == 401) {
                                        Map<String, Object> registerData = new HashMap<>();

                                        updateProfile(context, registerData, new DataListner() {
                                            @Override
                                            public void onSuccess(RegisterModal registerModal) {
                                                sendEvent(context, eventName, eventProperties);

                                            }

                                            @Override
                                            public void onFail(String message) {

                                            }
                                        });

                                    } else if (response.code() == 200) {
                                        ArrayList<MetaData> successMetaData = new ArrayList<>();
                                        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_SERVER_EVENTS_SUCCESS, CGConstants.CG_LOGGING_EVENTS.METRICS, successMetaData);
                                        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_SEND_EVENT_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                                        printDebugLogs("Event Sent");
                                        //     Toast.makeText(context, "Event sent", Toast.LENGTH_SHORT).show();

                                    }

                                } catch (Exception e) {
                                    String s = e.toString();
                                    ArrayList<MetaData> failureMetaData = new ArrayList<>();
                                    failureMetaData.add(new MetaData("Exception", "" + e));
                                    diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                                    CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                                    printErrorLogs(e.toString());

                                }
                            }


                            @Override
                            public void onFailure(Call<RegisterModal> call, Throwable t) {
                                String s = "" + t;
                                if (t.getMessage() != null) {
                                    s = t.getMessage();
                                }
                                ArrayList<MetaData> failureMetaData = new ArrayList<>();
                                failureMetaData.add(new MetaData("failure", "" + s));
                                diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_ENTRY_POINTS_FAILURE, CGConstants.CG_LOGGING_EVENTS.METRICS, failureMetaData);
                                diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_SEND_EVENT_END, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
                                CustomerGlu.getInstance().sendCrashAnalytics(context, "Event Api Fails " + s);
                                printErrorLogs(s);

                            }
                        });

                    } catch (Exception e) {
                        String s = e.toString();

                        CustomerGlu.getInstance().sendCrashAnalytics(context, s);
                        printErrorLogs(e.toString());

                    }
                } else {
                    printErrorLogs("Please do register token in not present");
                }
            } else {
                printErrorLogs("No Internet Connection");
            }
        }
    }

    /**
     * Used to Clear CustomerGLu SDK Data
     */
    public void clearGluData(Context context) {
        boolean writeKeyPresent = false;
        boolean userRegistered = false;
        if (!getWriteKey(context).isEmpty()) {
            writeKeyPresent = true;
        }

        if (!Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
            userRegistered = true;
        }
        ArrayList<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("writeKeyPresent", "" + writeKeyPresent));
        metaData.add(new MetaData("userRegistered", "" + userRegistered));
        diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_CLEAR_GLU_DATA_CALLED, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);
        Prefs.removeKey(context, CUSTOMERGLU_USER_ID);
        Prefs.removeKey(context, CUSTOMERGLU_TOKEN);
        Prefs.putEncKey(context, HASH_CODE, "");
        Prefs.putEncKey(context, CGConstants.FLOATING_DATE, "");
        Prefs.putEncKey(context, POPUP_DATE, "");
        Prefs.putEncKey(context, CG_USER_DATA, "");
        Prefs.putCampaignIdObject(context, null);
        Prefs.putEncCampaignIdObject(context, null);
        Prefs.putDismissedEntryPoints(context, null);
        Prefs.putEncDismissedEntryPoints(context, null);
        dismissedEntryPoints = new HashMap<>();
        displayScreen = new ArrayList<>();
        Prefs.putEncKey(context, ENCRYPTED_CUSTOMERGLU_TOKEN, "");
        Prefs.putEncKey(context, CLIENT_ID, "");
        Prefs.putEncKey(context, MQTT_IDENTIFIER, "");
        Prefs.putEncKey(context, IS_LOGIN, "");
        Prefs.putEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID, "");
        Prefs.putEncKey(context, ANONYMOUSID, "");
        myThread = 0;
        cgUserData = null;
        entryPointsModel = null;
        isBannerEntryPointsHasData = false;
        entryPointManager = null;
        appSessionId = UUID.randomUUID().toString();
        ContextWrapper contextWrapper = new ContextWrapper(context);
        screenList = new ArrayList<>();
        EntryPointETAG = "";
        LoadCampaignETAG = "";
        campaignIdList = new ArrayList<>();
        File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(zip, "CustomerGlu");
        recursiveDelete(file);
        SentryHelper.getInstance().logoutSentry();
    }


}
