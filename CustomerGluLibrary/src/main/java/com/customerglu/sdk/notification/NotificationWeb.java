package com.customerglu.sdk.notification;

import static com.customerglu.sdk.Utils.CGConstants.PUSH_NOTIFICATION_CLICK;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Web.CGWebClient;
import com.customerglu.sdk.Web.WebViewJavaScriptInterface;
import com.customerglu.sdk.custom.base.BaseActivity;
import com.customerglu.sdk.custom.views.ProgressLottieView;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NotificationWeb extends BaseActivity {


    public static boolean closeOnDeepLink = false;
    String url = "";
    RelativeLayout main;
    ProgressBar pg;
    WebView webView;
    String final_url = "";
    HashMap<String, Object> finalData;
    LottieAnimationView lottieAnimationView;
    BroadcastReceiver broadcastReceiver;
    CountDownTimer cTimer = null;
    String darkMode = "darkMode=false";
    private ProgressLottieView progressLottieView;
    String statusBarColor = "";

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                try {

                    printDebugLogs("==================Broadcast Recieved============");
                    if (intent.getAction().equalsIgnoreCase("HIDE_LOADER")) {
                        printDebugLogs("HIDE_LOADER callback");
                        stopLottieProgressView();
                        if (webView != null) {
                            webView.setVisibility(View.VISIBLE);
                        }
                        cancelTimer();

                    }
                } catch (Exception e) {
                    stopLottieProgressView();
                    webView.setVisibility(View.VISIBLE);

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
                if (webView != null) {
                    stopLottieProgressView();
                    webView.setVisibility(View.VISIBLE);
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!CustomerGlu.configure_loading_screen_color.isEmpty()) {
            setTheme(R.style.Theme_Transparent);
        }
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.web_activity);
        findViews();
        if (getIntent().getStringExtra("isHyperLink") != null && getIntent().getStringExtra("isHyperLink").equalsIgnoreCase("true")) {

            progressLottieView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        } else {
            startTimer();
            setupProgressView(progressLottieView);
            startLottieProgressView();
        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (statusBarColor.isEmpty()) {
            String statusBarColor = CustomerGlu.configure_status_bar_color;

            window.setStatusBarColor(Color.parseColor(statusBarColor));
        } else {
            window.setStatusBarColor(Color.parseColor(statusBarColor));
        }
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);
                String s = "Uncaught Exception" + e;
                cancelTimer();
                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);
            }
        });
        try {
            if (getIntent().getStringExtra("notification") != null) {
                String data = getIntent().getStringExtra("notification");
                JSONObject jsonObject = new JSONObject(data);
                Map<String, Object> nudgeData = new HashMap<>();
                nudgeData.put("type", jsonObject.getString("type"));
                nudgeData.put("title", jsonObject.getString("title"));
                nudgeData.put("body", jsonObject.getString("body"));
                nudgeData.put("nudge_layout", jsonObject.getString("nudge_layout"));
                nudgeData.put("nudge_id", jsonObject.getString("nudge_id"));
                nudgeData.put("click_action", jsonObject.getString("click_action"));
                nudgeData.put("campaign_id", jsonObject.getString("campaign_id"));
                CustomerGlu.getInstance().cgAnalyticsEventManager(getApplicationContext(), PUSH_NOTIFICATION_CLICK, nudgeData);
            }


            if (getIntent().getStringExtra("closeOnDeepLink") != null && getIntent().getStringExtra("closeOnDeepLink").equalsIgnoreCase("true")) {
                String val = getIntent().getStringExtra("closeOnDeepLink");
                System.out.println(val);
                closeOnDeepLink = true;
            } else {
                closeOnDeepLink = false;
            }
            finalData = new HashMap<>();
            finalData.put("relative_height", "100");
            finalData.put("absolute_height", "0");
            finalData.put("webview_layout", CGConstants.Full_NOTIFICATION);
            setUpWebView();
        } catch (Exception e) {
            cancelTimer();

        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
            CustomerGlu.dismiss_trigger = CGConstants.PHYSICAL_BUTTON;

        }

    }


    @Override
    protected void onDestroy() {
        printDebugLogs(" Activity Destroyed");
        finalData.put("webview_url", final_url);

        CustomerGlu.getInstance().cgAnalyticsEventManager(getApplicationContext(), CGConstants.WEBVIEW_DISMISS, finalData);
        super.onDestroy();

    }

    private void findViews() {
        main = findViewById(R.id.main);
        webView = findViewById(R.id.web);
        pg = findViewById(R.id.pg);
        progressLottieView = findViewById(R.id.lottie_view);


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView() {

        if (!CustomerGlu.configure_loading_screen_color.isEmpty()) {
            main.setBackgroundColor(Color.parseColor(CustomerGlu.configure_loading_screen_color));
        }

        int color;
        try {
            color = Color.parseColor(CustomerGlu.configure_loader_color);
            // color is a valid color
        } catch (IllegalArgumentException ex) {
            color = Color.parseColor("#65DCAB");
        }
        pg.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        url = getIntent().getStringExtra("nudge_url");
        System.out.println("RET");
        final_url = url;

        System.out.println(url);
        webView.setWebViewClient(new CGWebClient(getApplicationContext(), finalData));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebViewJavaScriptInterface(getApplicationContext(), this, closeOnDeepLink), "app"); // **IMPORTANT** call it app
        try {
            URL checkurl = new URL(url);
            if (checkurl.getQuery() == null) {
                darkMode = "?" + darkMode;
            } else {
                darkMode = "&" + darkMode;

            }
            // Toast.makeText(OpenCustomerGluWeb.this, "cg" + url.getQuery(), Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        webView.loadUrl(validateURL(url + darkMode));

    }


}
