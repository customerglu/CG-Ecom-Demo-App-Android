package com.customerglu.sdk.Utils;

import static com.customerglu.sdk.CustomerGlu.cg_sdk_version;
import static com.customerglu.sdk.CustomerGlu.isOnline;
import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CUSTOMERGLU_USER_ID;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.CGLoggingEventModel;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.RegisterModal;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiagnosticsHelper {
    private static DiagnosticsHelper Instance;
    private String writeKey = "";
    private Context context;

    // Sentry Instance.
    public static synchronized DiagnosticsHelper getInstance() {
        if (Instance == null) {
            Instance = new DiagnosticsHelper();
        }
        return Instance;
    }

    public void initDiagnostics(Context context, String writeKey) {
        this.context = context;
        this.writeKey = writeKey;
    }

    public void sendDiagnosticsReport(String event_name, CGConstants.CG_LOGGING_EVENTS logging_events, ArrayList<MetaData> meta_data) {
        if (!CustomerGlu.sdk_disable) {

            if (isOnline(context)) {
                Gson gson = new Gson();
                //String metaData = gson.toJson(meta_data);
                try {
                    String type = "exception";
                    switch (logging_events) {
                        case DIAGNOSTICS:
                            type = "diagnostics";
                            if (CustomerGlu.isDiagnosticsEnabled) {
                                sendLoggingReport(type, event_name, meta_data);
                            }
                            break;
                        case METRICS:
                            type = "metrics";
                            if (CustomerGlu.isMetricsEnabled) {
                                sendLoggingReport(type, event_name, meta_data);
                            }
                            break;
                        case CRASH:
                            type = "crash";
                            if (CustomerGlu.isCrashLoggingEnabled) {
                                sendLoggingReport(type, event_name, meta_data);
                            }
                            break;
                        default:
                            if (CustomerGlu.isCrashLoggingEnabled) {
                                sendLoggingReport(type, event_name, meta_data);
                            }

                    }

                } catch (Exception e) {
                    //  Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
                    Comman.printErrorLogs(e.toString());
                }

            } else {
                printErrorLogs("No Internet Connection");
            }
        }


    }

    private void sendLoggingReport(String type, String event_name, ArrayList<MetaData> metaData) {
        try {
            Gson gson = new Gson();
            CGLoggingEventModel cgLoggingEventModel = new CGLoggingEventModel();
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String appVersion = pInfo.versionName;
            String app_name = pInfo.packageName;
            String os_version = String.valueOf(Build.VERSION.SDK_INT);
            String my_user_id = "";
            my_user_id = Prefs.getEncKey(context, ENCRYPTED_CUSTOMERGLU_USER_ID);
            String write_key = writeKey;

            long currentTime = Calendar.getInstance().getTimeInMillis();
            cgLoggingEventModel.setTimestamp("" + currentTime);

            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            int version = Build.VERSION.SDK_INT;
            cgLoggingEventModel.platform_details = new CGLoggingEventModel.PlatformDetails();
            cgLoggingEventModel.platform_details.setModel(model);
            cgLoggingEventModel.platform_details.setOs("android");
            cgLoggingEventModel.platform_details.setOs_version("" + version);
            cgLoggingEventModel.platform_details.setManufacturer(manufacturer);
            cgLoggingEventModel.optional_payload = new CGLoggingEventModel.OptionalPayload();
            cgLoggingEventModel.optional_payload.setMetadata(metaData);
            cgLoggingEventModel.setType("SYSTEM");
            cgLoggingEventModel.setLog_type(type);
            cgLoggingEventModel.setEvent_name(event_name);
            cgLoggingEventModel.setEvent_id(UUID.randomUUID().toString());
            cgLoggingEventModel.setSession_id(CustomerGlu.appSessionId);
            cgLoggingEventModel.setSession_time("" + currentTime);
            cgLoggingEventModel.setUser_id(my_user_id);
            cgLoggingEventModel.setSdk_version(cg_sdk_version);
            cgLoggingEventModel.setSdk_type("cg-sdk-android");
            cgLoggingEventModel.setAnalytics_version("4.0.0");
            String body = gson.toJson(cgLoggingEventModel);
            printDebugLogs(event_name + body);
            if (!write_key.equalsIgnoreCase("")) {
                CGAPIHelper.enqueueWithRetry(Comman.getApiToken().sendDiagnosticsEvent(write_key, cgLoggingEventModel), new Callback<RegisterModal>() {
                    @Override
                    public void onResponse(Call<RegisterModal> call, Response<RegisterModal> response) {
                        try {
                            if (response.code() == 200) {
                                printDebugLogs(event_name + "Log sent");
                            }

                        } catch (Exception ex) {
                            printDebugLogs(ex.toString());
                        }
                    }


                    @Override
                    public void onFailure(Call<RegisterModal> call, Throwable t) {


                    }
                });

            }
        } catch (Exception e) {

        }
    }


}
