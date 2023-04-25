package com.customerglu.sdk.Web;

import static com.customerglu.sdk.CustomerGlu.diagnosticsHelper;
import static com.customerglu.sdk.Utils.CGConstants.CG_METRICS_SDK_REGISTER_RESPONSE;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;
import static com.customerglu.sdk.Utils.Comman.validateURL;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.custom.base.BaseActivity;
import com.customerglu.sdk.custom.views.ProgressLottieView;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class OpenCustomerGluWeb extends BaseActivity {

    public static boolean closeOnDeepLink = false;
    String type = "", campaign_id = "";
    Boolean isThere = false;
    Boolean fromWallet = false;
    ProgressBar pg;
    WebView web;
    String final_url = "";
    RelativeLayout main;
    HashMap<String, Object> finalData;
    String darkMode = "darkMode=false";
    BroadcastReceiver broadcastReceiver;
    CountDownTimer cTimer = null;
    ProgressLottieView progressLottieView;
    String statusBarColor = "";

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent

                System.out.println("==================Recieved============");
                if (intent.getAction().equalsIgnoreCase("HIDE_LOADER")) {
                    stopLottieProgressView();
                    if (web != null) {
                        web.setVisibility(View.VISIBLE);
                    }
                    cancelTimer();

                }
            }


        };
        registerReceiver(broadcastReceiver, new IntentFilter("HIDE_LOADER"));
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterReceiver(broadcastReceiver);
    }

    void startTimer() {
        cTimer = new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (web != null) {
                    web.setVisibility(View.VISIBLE);
                    stopLottieProgressView();
                }
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!CustomerGlu.configure_loading_screen_color.isEmpty()) {
            setTheme(R.style.Theme_Transparent);
        }
        super.onCreate(savedInstanceState);
        ArrayList<MetaData> responseMetaData = new ArrayList<>();
        responseMetaData.add(new MetaData("Activity Created", "" + "true"));
        diagnosticsHelper.sendDiagnosticsReport(CG_METRICS_SDK_REGISTER_RESPONSE, CGConstants.CG_LOGGING_EVENTS.METRICS, responseMetaData);
        if (CustomerGlu.isDarkModeEnabled(getApplicationContext())) {
            darkMode = "darkMode=true";
            if (CustomerGlu.darkStatusBarColor != null) {
                statusBarColor = CustomerGlu.darkStatusBarColor;
            }

        } else {
            if (CustomerGlu.lightStatusBarColor != null) {
                statusBarColor = CustomerGlu.lightStatusBarColor;
            }
        }
        startTimer();
        setContentView(R.layout.web_activity);
        progressLottieView = findViewById(R.id.lottie_view);
        setupProgressView(progressLottieView);
        startLottieProgressView();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (statusBarColor.isEmpty()) {
            String statusBarColor = CustomerGlu.configure_status_bar_color;

            window.setStatusBarColor(Color.parseColor(statusBarColor));
        } else {
            window.setStatusBarColor(Color.parseColor(statusBarColor));

        }
        main = findViewById(R.id.main);
        //   main.setBackgroundColor(0);
        //       setActivityBackgroundColor(Color.argb(0, 0, 0, 0));

        printErrorLogs("Wallet Activity Created");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                String s = "";
                if (e.getCause() != null) {
                    printErrorLogs("Uncaught Exception" + e);
                    s = "Uncaught Exception" + e.getCause();
                    web.setVisibility(View.VISIBLE);
                    cancelTimer();
                } else {
                    s = "" + e;
                }

                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);
            }
        });

        try {
            if (getIntent().getStringExtra("closeOnDeepLink") != null && getIntent().getStringExtra("closeOnDeepLink").equalsIgnoreCase("true")) {
                String val = getIntent().getStringExtra("closeOnDeepLink");
                System.out.println(val);
                closeOnDeepLink = true;
            } else {
                closeOnDeepLink = false;
            }
            if (getIntent().getStringExtra("fromWallet") != null && getIntent().getStringExtra("fromWallet").equalsIgnoreCase("true")) {
                fromWallet = true;
            } else {
                fromWallet = false;
            }

            finalData = new HashMap<>();
            finalData.put("relative_height", "100");
            finalData.put("absolute_height", "0");
            finalData.put("webview_layout", CGConstants.Full_NOTIFICATION);
            findViews();
            getRewardsData();
        } catch (Exception e) {
            CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), e.toString());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printDebugLogs(" Activity Destroyed");
        finalData.put("webview_url", final_url);
        CustomerGlu.getInstance().cgAnalyticsEventManager(getApplicationContext(), CGConstants.WEBVIEW_DISMISS, finalData);

    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
            CustomerGlu.dismiss_trigger = CGConstants.PHYSICAL_BUTTON;
        }

    }


    private void getRewardsData() {

        campaign_id = getIntent().getStringExtra("campaign_id").trim();

        if (campaign_id.equalsIgnoreCase("")) {


            CustomerGlu.getInstance().retrieveData(getApplicationContext(), new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    String default_url = rewardModel.defaultUrl;
                    final_url = default_url;
                    try {
                        URL url = new URL(default_url);
                        if (url.getQuery() == null) {
                            darkMode = "?" + darkMode;
                        } else {
                            darkMode = "&" + darkMode;

                        }
                        // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    web.loadUrl(validateURL(default_url + darkMode));
                    URL url = null;


                    if (!fromWallet) {
                        CustomerGlu.getInstance().sendInvalidCampaignIdCallback(getApplicationContext(), campaign_id);
                    }

                }

                @Override
                public void onFailure(String message) {
                    String errorUrl = "https://end-user-ui.customerglu.com/error/?source=native-sdk&code=" + 504 + "&message=" + "Please Try again later";
                    final_url = errorUrl;
                    try {
                        URL url = new URL(final_url);
                        if (url.getQuery() == null) {
                            darkMode = "?" + darkMode;
                        } else {
                            darkMode = "&" + darkMode;

                        }
                        // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    web.loadUrl(errorUrl + darkMode);
                    Log.e("CG URL", errorUrl + darkMode);
                    web.setVisibility(View.VISIBLE);
                    cancelTimer();

                }
            });
        } else {

            CustomerGlu.getInstance().retrieveData(getApplicationContext(), new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    isThere = false;
                    Gson gson = new Gson();
                    String json = gson.toJson(rewardModel);


                    if (rewardModel.getCampaigns().size() <= 0) {

                        String default_url = rewardModel.defaultUrl;
                        final_url = default_url;
                        try {
                            URL url = new URL(final_url);
                            if (url.getQuery() == null) {
                                darkMode = "?" + darkMode;
                            } else {
                                darkMode = "&" + darkMode;

                            }
                            // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        web.loadUrl(validateURL(default_url + darkMode));
                        CustomerGlu.getInstance().sendInvalidCampaignIdCallback(getApplicationContext(), campaign_id);
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
                                String campaignUrl = rewardModel.getCampaigns().get(i).getUrl();
                                //   System.out.println(url);
                                //     Toast.makeText(OpenCustomerGluWeb.this, url, Toast.LENGTH_SHORT).show();
                                isThere = true;
                                final_url = campaignUrl;
                                try {
                                    URL checkurl = new URL(final_url);
                                    if (checkurl.getQuery() == null) {
                                        darkMode = "?" + darkMode;
                                    } else {
                                        darkMode = "&" + darkMode;

                                    }
                                    // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                web.loadUrl(validateURL(campaignUrl + darkMode));
                                break;
                            }

                        }
                        if (!isThere) {
                            String default_url = rewardModel.defaultUrl;
                            final_url = default_url;
                            CustomerGlu.getInstance().sendInvalidCampaignIdCallback(getApplicationContext(), campaign_id);
                            try {
                                URL url = new URL(final_url);
                                if (url.getQuery() == null) {
                                    darkMode = "?" + darkMode;
                                } else {
                                    darkMode = "&" + darkMode;

                                }
                                // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            web.loadUrl(validateURL(default_url + darkMode));
                        }

                    }


                }

                @Override
                public void onFailure(String message) {

                    String errorUrl = "https://end-user-ui.customerglu.com/error/?source=native-sdk&code=" + 504 + "&message=" + "Please Try again later";
                    final_url = errorUrl;
                    try {
                        URL url = new URL(final_url);
                        if (url.getQuery() == null) {
                            darkMode = "?" + darkMode;
                        } else {
                            darkMode = "&" + darkMode;

                        }
                        // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    web.loadUrl(errorUrl + darkMode);
                    web.setVisibility(View.VISIBLE);
                    cancelTimer();
                }
            });
        }


    }


    @SuppressLint("SetJavaScriptEnabled")
    private void findViews() {

        web = findViewById(R.id.web);
        main = findViewById(R.id.main);
        if (!CustomerGlu.configure_loading_screen_color.isEmpty()) {
            main.setBackgroundColor(Color.parseColor(CustomerGlu.configure_loading_screen_color));
        }

        pg = findViewById(R.id.pg);
        int color;
        try {
            color = Color.parseColor(CustomerGlu.configure_loader_color);
            // color is a valid color
        } catch (IllegalArgumentException ex) {
            color = Color.parseColor("#65DCAB");
        }
        pg.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        //  web = CustomerGlu.webView;
        printErrorLogs("WebEvents CGWebClient");

        web.setWebViewClient(new CGWebClient(getApplicationContext(), finalData));
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.addJavascriptInterface(new WebViewJavaScriptInterface(getApplicationContext(), this, closeOnDeepLink), "app"); // **IMPORTANT** call it app

    }


}
