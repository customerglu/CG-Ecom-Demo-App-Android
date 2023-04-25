package com.customerglu.sdk.Utils;

public class CGConstants {
    public enum CGSTATE {
        SUCCESS,
        USER_NOT_SIGNED_IN,
        INVALID_URL,
        INVALID_CAMPAIGN,
        CAMPAIGN_UNAVAILABLE,
        NETWORK_EXCEPTION,
        DEEPLINK_URL,
        EXCEPTION
    }

    public enum SDK_ENV {
        DEBUG,
        PRODUCTION
    }

    public enum CG_LOGGING_EVENTS {
        DIAGNOSTICS,
        METRICS,
        CRASH,
        EXCEPTION
    }


    public static final String CG_PLATFORM_VALUE = "android-native-sdk";
    public static final String CG_PLATFORM_KEY = "cg-app-platform";
    public static final String CG_SDK_VERSION_KEY = "cg-sdk-version";
    public static final String CG_SDK_VERSION_VAL = "2.3.4";
    public static final String MQTT_SERVER_HOST = "hermes.customerglu.com";
    public static final String DEV_MQTT_SERVER_HOST = "dev-hermes.customerglu.com";

    public enum LOTTIE_FILE {
        LIGHT,
        DARK,
        EMBED_LIGHT,
        EMBED_DARK
    }


    // One Link DEEPLINK SUPPORT

    public static final String OPEN_CAMPAIGN = "OPEN_CAMPAIGN";
    public static final String OPEN_WALLET = "OPEN_WALLET";
    public static final String OPEN_DEEPLINK = "OPEN_DEEPLINK";
    public static final String OPEN_WEBLINK = "OPEN_WEBLINK";
    public static final String CG_GLU_US = "cglu.us";

    // Error page URl

    public static final String ERROR_URL = "https://constellation.customerglu.com/error.html";

    //Diagnostics constants
    public static final String CG_DIAGNOSTICS_INIT_START = "CGDiagnostics - SDK Init Start";
    public static final String CG_DIAGNOSTICS_INIT_END = "CGDiagnostics - SDK Init End";
    public static final String CG_DIAGNOSTICS_USER_REGISTRATION_START = "CGDiagnostics - SDK User registration Start";
    public static final String CG_DIAGNOSTICS_USER_REGISTRATION_END = "CGDiagnostics - SDK User registration End";
    public static final String CG_DIAGNOSTICS_LOAD_CAMPAIGN_START = "CGDiagnostics - SDK User Load Campaign Start";
    public static final String CG_DIAGNOSTICS_LOAD_CAMPAIGN_END = "CGDiagnostics - SDK Load Campaign End";
    public static final String CG_DIAGNOSTICS_GET_ENTRY_POINT_START = "CGDiagnostics - SDK getEntryPoint  Start";
    public static final String CG_DIAGNOSTICS_GET_ENTRY_POINT_END = "CGDiagnostics - SDK getEntryPoint End";
    public static final String CG_DIAGNOSTICS_SEND_EVENT_START = "CGDiagnostics - SDK sendEventData Start";
    public static final String CG_DIAGNOSTICS_SEND_EVENT_END = "CGDiagnostics - SDK sendEventData End";
    public static final String CG_DIAGNOSTICS_OPEN_WALLET_CALLED = "CGDiagnostics - SDK OpenWallet Called";
    public static final String CG_DIAGNOSTICS_OPEN_NUDGE_CALLED = "CGDiagnostics - SDK OpenNudge Called";
    public static final String CG_DIAGNOSTICS_LOAD_CAMPAIGN_BY_ID_CALLED = "CGDiagnostics - SDK LoadCampaignByID Called";
    public static final String CG_DIAGNOSTICS_CLEAR_GLU_DATA_CALLED = "CGDiagnostics - SDK clearGluData Called";
    public static final String CG_DIAGNOSTICS_ENABLE_ANALYTICS_CALLED = "CGDiagnostics - SDK enableAnalyticsEvent Called";
    public static final String CG_DIAGNOSTICS_SET_DARK_MODE_CALLED = "CGDiagnostics - SDK setDarkMode Called";
    public static final String CG_DIAGNOSTICS_LISTEN_SYSTEM_DARK_MODE_CALLED = "CGDiagnostics - SDK listenToDarkMode Called";
    public static final String CG_DIAGNOSTICS_LOADER_COLOR_CALLED = "CGDiagnostics - SDK configureLoaderColour Called";
    public static final String CG_DIAGNOSTICS_CONFIGURE_SAFE_AREA_CALLED = "CGDiagnostics - SDK configureSafeArea Called";
    public static final String CG_DIAGNOSTICS_GLU_SDK_DEBUGGING_MODE_CALLED = "CGDiagnostics - SDK gluSDKDebuggingMode Called";
    public static final String CG_DIAGNOSTICS_DISABLE_SDK_CALLED = "CGDiagnostics - SDK disableGluSdk Called";
    public static final String CG_DIAGNOSTICS_NOTIFICATION_CALLED = "CGDiagnostics - SDK Push notification Received ";
    public static final String CG_DIAGNOSTICS_BACKGROUND_NOTIFICATION_CALLED = "CGDiagnostics - SDK background notification Received";
    public static final String CG_DIAGNOSTICS_CTA_CALLBACK = "CGDiagnostics - JS CTA CALLBACK";
    public static final String CG_DIAGNOSTICS_MQTT_INITIALIZING = "CGDiagnostics - MQTT Initialized";
    public static final String CG_DIAGNOSTICS_MQTT_CONNECTED = "CGDiagnostics - MQTT Connected";
    public static final String CG_DIAGNOSTICS_MQTT_CALLBACK = "CGDiagnostics - MQTT Callback";


    // Metrics Event
    public static final String CG_METRICS_SDK_READY = "CGMetrics - SDK Ready ";
    public static final String CG_METRICS_SDK_CONFIG_CALLED = "CGMetrics - SDK Config Called ";
    public static final String CG_METRICS_SDK_CONFIG_RESPONSE = "CGMetrics - SDK Config Response ";
    public static final String CG_METRICS_SDK_CONFIG_SUCCESS = "CGMetrics - SDK Config Success";
    public static final String CG_METRICS_SDK_CONFIG_FAILURE = "CGMetrics - SDK Config Failure";
    public static final String CG_METRICS_SDK_REGISTER_CALLED = "CGMetrics - SDK Register Called ";
    public static final String CG_METRICS_SDK_REGISTER_RESPONSE = "CGMetrics - SDK Register Response ";
    public static final String CG_METRICS_SDK_REGISTER_SUCCESS = "CGMetrics - SDK Register Success";
    public static final String CG_METRICS_SDK_REGISTER_FAILURE = "CGMetrics - SDK Register Failure";
    public static final String CG_METRICS_SDK_LOAD_CAMPAIGN_CALLED = "CGMetrics - SDK Load campaign Called ";
    public static final String CG_METRICS_SDK_LOAD_CAMPAIGN_RESPONSE = "CGMetrics - SDK Load campaign Response ";
    public static final String CG_METRICS_SDK_LOAD_CAMPAIGN_SUCCESS = "CGMetrics - SDK Load campaign Success";
    public static final String CG_METRICS_SDK_LOAD_CAMPAIGN_FAILURE = "CGMetrics - SDK Load campaign Failure";
    public static final String CG_METRICS_SDK_ENTRY_POINTS_CALLED = "CGMetrics - SDK EntryPoints Called ";
    public static final String CG_METRICS_SDK_ENTRY_POINTS_UPDATE_CALLED = "CGMetrics - SDK EntryPoints Update Called ";
    public static final String CG_METRICS_SDK_ENTRY_POINTS_RESPONSE = "CGMetrics - SDK EntryPoints Response ";
    public static final String CG_METRICS_SDK_ENTRY_POINTS_SUCCESS = "CGMetrics - SDK EntryPoints Success";
    public static final String CG_METRICS_SDK_ENTRY_POINTS_FAILURE = "CGMetrics - SDK EntryPoints Failure";
    public static final String CG_METRICS_SDK_SERVER_EVENTS_CALLED = "CGMetrics - SDK Server Events Called ";
    public static final String CG_METRICS_SDK_SERVER_EVENTS_RESPONSE = "CGMetrics - SDK Server Events Response ";
    public static final String CG_METRICS_SDK_SERVER_EVENTS_SUCCESS = "CGMetrics - SDK Server Events Success";
    public static final String CG_METRICS_SDK_SERVER_EVENTS_FAILURE = "CGMetrics - SDK Server Events Failure";
    public static final String CG_METRICS_SDK_WORMHOLE_CALLED = "CGMetrics - SDK Wormhole Called ";
    public static final String CG_METRICS_SDK_WORMHOLE_RESPONSE = "CGMetrics - SDK Wormhole Response ";
    public static final String CG_METRICS_SDK_WORMHOLE_SUCCESS = "CGMetrics - SDK Wormhole Success";
    public static final String CG_METRICS_SDK_WORMHOLE_FAILURE = "CGMetrics - SDK Wormhole Failure";
    public static final String CG_METRICS_SDK_MQTT_CALLBACK_SUCCESS = "CGMetrics - SDK MQTT Callback Success";
    public static final String CG_METRICS_SDK_MQTT_CALLBACK_FAILURE = "CGMetrics - SDK MQTT Callback Failure";

    public static final String CG_CRASH = "CGCRASH";
    public static final String ANDROID = "ANDROID";
    public static final String FLUTTER = "FLUTTER";
    public static final String REACT_NATIVE = "REACT_NATIVE";
    public static final String ENTRYPOINT = "ENTRYPOINT";
    public static final String NO_INTERNET = "No Internet Connection";
    public static final String EXCEPTION = "CustomerGlu Exception:";
    public static final String CGLU = ".cglu.";
    public static final String LIGHT_LOTTIE_FILE_NAME = "LightLottieFileName";
    public static final String LIGHT_LOTTIE_FILE = "LightLottieFile";
    public static final String DARK_LOTTIE_FILE_NAME = "DarkLottieFileName";
    public static final String DARK_LOTTIE_FILE = "DarkLottieFile";
    public static final String EMBED_LIGHT_LOTTIE_FILE_NAME = "EmbedLightLottieFileName";
    public static final String EMBED_LIGHT_LOTTIE_FILE = "EmbedLightLottieFile";
    public static final String EMBED_DARK_LOTTIE_FILE_NAME = "EmbedDarkLottieFileName";
    public static final String EMBED_DARK_LOTTIE_FILE = "EmbedDarkLottieFile";
    public static final String MIDDLE_NOTIFICATION = "middle-default";
    public static final String Full_NOTIFICATION = "full-default";
    public static final String BOTTOM_SHEET_NOTIFICATION = "bottom-slider";
    public static final String BOTTOM_DEFAULT_NOTIFICATION = "bottom-default";
    public static final String MIDDLE_POPUP = "middle-popup";
    public static final String FULL_SCREEN = "fullscreen";
    public static final String BOTTOM_POPUP = "bottom-popup";
    public static final String CUSTOMERGLU_USER_ID = "cust_user_id";
    public static final String ENCRYPTED_CUSTOMERGLU_USER_ID = "customerglu_user_id";
    public static final String CUSTOMERGLU_TOKEN = "cust_user_token";
    public static final String ENCRYPTED_CUSTOMERGLU_TOKEN = "customerglu_user_token";
    public static final String DEFAULT_URL = "default_url";
    public static final String HASH_CODE = "hash_code";
    public static final String TRACK = "track";
    public static final String FLOATING_DATE = "floating_daily_date";
    public static final String POPUP_DATE = "daily_date";
    public static final String ANONYMOUSID = "anonymous_id";
    public static final String IS_LOGIN = "is_login";
    public static final String CG_USER_DATA = "cg_user_data";
    public static final String CLIENT_ID = "clientId";
    public static final String MQTT_IDENTIFIER = "mqtt_identifier";
    public static final String WEBVIEW_DISMISS = "WEBVIEW_DISMISS";
    public static final String WEBVIEW_LOAD = "WEBVIEW_LOAD";
    public static final String NOTIFICATION_LOAD = "NOTIFICATION_LOAD";
    public static final String PUSH_NOTIFICATION_CLICK = "PUSH_NOTIFICATION_CLICK";
    public static final String ENTRY_POINT_LOAD = "ENTRY_POINT_LOAD";
    public static final String ENTRY_POINT_DISMISS = "ENTRY_POINT_DISMISS";
    public static final String ENTRY_POINT_CLICK = "ENTRY_POINT_CLICK";
    public static final String PHYSICAL_BUTTON = "PHYSICAL_BUTTON";
    public static final String UI_BUTTON = "UI_BUTTON";
    public static final String CTA_REDIRECT = "CTA_REDIRECT";
    public static final String CAMPAIGN_OBJ = "Campaign_object";
    public static final String ENCRYPTED_CAMPAIGN_OBJ = "encrypted_campaign_object";
    public static final String DISMISSED_ENTRY_POINTS = "dismiss_entry_points";
    public static final String ENCRYPTED_DISMISSED_ENTRY_POINTS = "encrypted_dismiss_entry_points";
    public static final String INVALID_CAMPAIGN_ID = "{\n" +
            "\"eventName\":\"CG_INVALID_CAMPAIGN_ID\",\n" +
            "\"data\":{\n" +
            "\"message\":\"Invalid campaignId/Tag, opening Wallet\"\n" +
            "}\n" +
            "}";
    public static final String URL_REGEX = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
    public static String register_response = "";

    public static String load_campaign_response = "";


}
