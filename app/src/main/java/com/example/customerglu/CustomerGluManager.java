//package com.example.customerglu;
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.Toast;
//
//import com.customerglu.sdk.CustomerGlu;
//import com.customerglu.sdk.Interface.CGDeepLinkListener;
//import com.customerglu.sdk.Interface.DataListner;
//import com.customerglu.sdk.Modal.DeepLinkWormholeModel;
//import com.customerglu.sdk.Modal.NudgeConfiguration;
//import com.customerglu.sdk.Modal.RegisterModal;
//import com.customerglu.sdk.Utils.CGConstants;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Set;
//
//public class CustomerGluManager {
//
//    public static void initializeSDK(Context context, boolean debugMode) {
//        CustomerGlu.getInstance().initializeSdk(context);
//        CustomerGlu.getInstance().gluSDKDebuggingMode(context, debugMode);
//        setUpCTACallback(context);
//    }
//
//    private static void setUpCTACallback(Context context) {
//        // Log.e("WebAnalytics ", "" + jsonObject);
//        // Toast.makeText(context, "Analytics " + jsonObject, Toast.LENGTH_SHORT).show();
//        // Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
//        // banner.setVisibility(GONE);
//        // Log.e("WebAnalytics INVALID", data);
//        // Add the logic to redirect to appropriate page
//        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                try {
//                    new Handler().postDelayed(() -> {
//                        if (intent.getAction().equals("CUSTOMERGLU_DEEPLINK_EVENT")) {
//                            String data = intent.getStringExtra("data");
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.has("deepLink")) {
//                                    Object deeplink = jsonObject.get("deepLink");
//                                    Toast.makeText(context, "Please handle CTA Deeplink", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception e) {
//
//                            }
//                        }
//                        if (intent.getAction().equals("CUSTOMERGLU_ANALYTICS_EVENT")) {
//                            String data = intent.getStringExtra("data");
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                            } catch (Exception e) {
//
//                            }
//                            // Log.e("WebAnalytics ", "" + jsonObject);
//                            // Toast.makeText(context, "Analytics " + jsonObject, Toast.LENGTH_SHORT).show();
//                        }
//                        if (intent.getAction().equals("CUSTOMERGLU_BANNER_LOADED")) {
//                            String data = intent.getStringExtra("data");
//                            // Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
//                            // banner.setVisibility(GONE);
//                        }
//                        if (intent.getAction().equals("CG_INVALID_CAMPAIGN_ID")) {
//                            String data = intent.getStringExtra("data");
//                            // Log.e("WebAnalytics INVALID", data);
//                        }
//                        // Add the logic to redirect to appropriate page
//                    }, 500);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        };
//        context.registerReceiver(mMessageReceiver, new IntentFilter("CUSTOMERGLU_DEEPLINK_EVENT"));
//    }
//
//    public static void enableEntryPoints(Context context) {
//        CustomerGlu.getInstance().enableEntryPoints(context, true);
//    }
//
//    public static void registerUser(Context context, HashMap<String, Object> userData) {
//        CustomerGlu.getInstance().registerDevice(context, userData, new DataListner() {
//            @Override
//            public void onSuccess(RegisterModal registerModal) {
//                // TODO
//            }
//
//            @Override
//            public void onFail(String message) {
//                // TODO
//            }
//        });
//    }
//
//    public static void clearGluData(Context context) {
//        CustomerGlu.getInstance().clearGluData(context);
//    }
//
//    public static void enableAnalytics() {
//        CustomerGlu.getInstance().enableAnalyticsEvent(true);
//    }
//
//    public static void openWallet(Context context, NudgeConfiguration nudgeConfiguration) {
//        CustomerGlu.getInstance().openWallet(context, nudgeConfiguration);
//    }
//
//    public static void loadCampaignById(Context context, String campaignId, NudgeConfiguration nudgeConfiguration) {
//        CustomerGlu.getInstance().loadCampaignById(context, campaignId, nudgeConfiguration);
//    }
//
//    public static void openNudge(Context context, String nudgeId, NudgeConfiguration nudgeConfiguration) {
//        CustomerGlu.getInstance().openNudge(context, nudgeId, nudgeConfiguration);
//    }
//
//    public static void setClassName(Activity activity, String screenName) {
//        CustomerGluManager.setClassNameForCG(activity, screenName);
//    }
//    public void displayCGNotifications(Context context, JSONObject jsonObject, int icon) {
//        CustomerGlu.getInstance().displayCustomerGluNotification(context, jsonObject, icon);
//    }
//
//    public void displayCGBackgroundNotification(Activity activity, long delayInSecond) {
//        Intent parent_intent = activity.getIntent();
//        String intentSource = "none";
//        if (parent_intent != null) {
//            Bundle extras = parent_intent.getExtras();
//            if (extras != null) {
//                intentSource = extras.getString("type", "none");
//                String myType = extras.getString("from", "none");
//                if (intentSource.equalsIgnoreCase("CustomerGlu")) {
//                    JSONObject json = new JSONObject();
//                    Set<String> keys = extras.keySet();
//                    for (String key : keys) {
//                        try {
//                            json.put(key, JSONObject.wrap(extras.get(key)));
//                        } catch (JSONException e) {
//                            //Handle exception here
//                        }
//                    }
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            CustomerGlu.getInstance()
//                                    .displayCustomerGluBackgroundNotification(activity.getApplicationContext(), json);
//                        }
//                    }, (delayInSecond * 100));
//                }
//            }
//        }
//    }
//
//    public void setDarkTheme(Context context, boolean value) {
//        CustomerGlu.getInstance().setDarkMode(context, value);
//    }
//
//    public void listenToSystemDarkMode(boolean value) {
//        CustomerGlu.getInstance().listenToSystemDarkMode(value);
//    }
//
//    public void setCGDeeplinkData(Activity activity) {
//        CustomerGlu.getInstance().setupCGDeepLinkIntentData(activity);
//    }
//
//    public void setCgDeeplinkCallbackListener() {
//        CustomerGlu.getInstance().setCgDeepLinkListener(new CGDeepLinkListener() {
//            @Override
//            public void onSuccess(CGConstants.CGSTATE message, DeepLinkWormholeModel.DeepLinkData deepLinkData) {
//                if (message == CGConstants.CGSTATE.DEEPLINK_URL) {
//                    String url = "";
//                    if (deepLinkData.getContent().getUrl() != null) {
//                        url = deepLinkData.getContent().getUrl();
//                    }
//                    // Add your logic
//                }
//                if (message == CGConstants.CGSTATE.SUCCESS) {
//                    //TODO: Not yet implemented
//                }
//            }
//
//            @Override
//            public void onFailure(CGConstants.CGSTATE exceptions) {
//                switch (exceptions) {
//                    case EXCEPTION:
//                        //TODO: Not yet implemented
//                        break;
//                    case NETWORK_EXCEPTION:
//                        //TODO: Not yet implemented
//                        break;
//                    case INVALID_CAMPAIGN:
//                        //TODO: Not yet implemented
//                        break;
//                    case CAMPAIGN_UNAVAILABLE:
//                        //TODO: Not yet implemented
//                        break;
//                    case INVALID_URL:
//                        //TODO: Not yet implemented
//                        break;
//                    case USER_NOT_SIGNED_IN:
//                        //TODO: Not yet implemented
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }
//
//    public void configureLoaderColour(Context context, String color) {
//        CustomerGlu.getInstance().configureLoaderColour(context, color);
//    }
//
//    public void sendEventsToCG(Context context, String eventName, HashMap<String, Object> eventProperties) {
//        CustomerGluManager.sendEventsToCG(context, eventName, eventProperties);
//    }
//
//}
