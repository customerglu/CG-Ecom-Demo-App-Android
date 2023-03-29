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
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

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

public class MiddleDialog extends BaseActivity {
    public static boolean closeOnDeepLink = false;
    String url = "";
    double opacity = 0.5, backgroundOpcatity = 0.5;
    RelativeLayout main;
    ProgressBar pg;
    CardView card;
    WebView webView;
    double height = 0;
    double absoluteHeight = 0;
    double relativeHeight = 0;
    double width = 0;
    String final_url = "";
    HashMap<String, Object> finalData;
    BroadcastReceiver broadcastReceiver;
    CountDownTimer cTimer = null;
    String darkMode = "darkMode=false";
    private ProgressLottieView progressLottieView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                try {

                    printDebugLogs("==================Recieved============");
                    if (intent.getAction().equalsIgnoreCase("HIDE_LOADER")) {
                        printDebugLogs("HIDE_LOADER");
                        stopLottieProgressView();
                        if (webView != null) {
                            webView.setVisibility(View.VISIBLE);
                        }
                        cancelTimer();

                    }
                } catch (Exception e) {
                    cancelTimer();
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
        cTimer = new CountDownTimer(5000, 1000) {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_dialog);
        startTimer();
        progressLottieView = findViewById(R.id.lottie_view);
        setupProgressView(progressLottieView);
        startLottieProgressView();
        if (CustomerGlu.isDarkModeEnabled(getApplicationContext())) {
            darkMode = "darkMode=true";
        }
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);
                String s = "Uncaught Exception" + e;
                cancelTimer();
                webView.setVisibility(View.VISIBLE);
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
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            width = displaymetrics.widthPixels;
            double screenHeight = displaymetrics.heightPixels;


            String absoluteHeight1 = getIntent().getStringExtra("absoluteHeight");

            String relativeHeight1 = getIntent().getStringExtra("relativeHeight");
            absoluteHeight = Double.parseDouble(absoluteHeight1);
            relativeHeight = Double.parseDouble(relativeHeight1);

            if (relativeHeight > 0) {
//
                height = (int) screenHeight * (relativeHeight / 100);
            } else if (absoluteHeight > 0) {
                height = (int) (absoluteHeight * Resources.getSystem().getDisplayMetrics().density);
            } else {
                height = (displaymetrics.heightPixels) / 1.4;
            }
            finalData = new HashMap<>();
            if (relativeHeight > 0 && absoluteHeight > 0) {
                finalData.put("relative_height", relativeHeight);
                finalData.put("absolute_height", absoluteHeight);
            } else if (relativeHeight > 0) {
                finalData.put("relative_height", relativeHeight);
                finalData.put("absolute_height", "0");

            } else if (absoluteHeight > 0) {
                finalData.put("absolute_height", absoluteHeight);
                finalData.put("relative_height", "0");

            } else {
                finalData.put("relative_height", "70");
                finalData.put("absolute_height", "0");
            }
            if (relativeHeight >= 100) {
                finalData.put("relative_height", "100");

            }
            finalData.put("webview_layout", CGConstants.MIDDLE_POPUP);
            findViews();
        } catch (Exception e) {
            printErrorLogs(e.toString());
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
//    @Override
//    protected void onStart() {
//        super.onStart();
//        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.buzzer);
//        mediaPlayer.start();
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private void findViews() {
        webView = findViewById(R.id.web_notification);
        pg = findViewById(R.id.pg);
        card = findViewById(R.id.card);
        String color;
        color = CustomerGlu.configure_loader_color;
        if (color.isEmpty()) {
            color = "#FF000000";
        }
        pg.getIndeterminateDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);

        main = findViewById(R.id.main);

        if (!CustomerGlu.configure_loading_screen_color.isEmpty()) {
            card.setBackgroundColor(Color.parseColor(CustomerGlu.configure_loading_screen_color));
        }
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerGlu.dismiss_trigger = CGConstants.UI_BUTTON;
                finish();
            }
        });
        url = getIntent().getStringExtra("nudge_url");
        String view_opacity = getIntent().getStringExtra("opacity");
        opacity = Double.parseDouble(view_opacity);
        backgroundOpcatity = 255 * opacity;

        final_url = url;

        main.getBackground().setAlpha((int) backgroundOpcatity);

        System.out.println("RET");
        System.out.println(url);

//        double newHeight = 540;
//        double newWidth = 353;
//        height = (int) (newHeight * Resources.getSystem().getDisplayMetrics().density);
//        width = (newWidth * getResources().getDisplayMetrics().densityDpi / 160f);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams((int) width, (int) height));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.leftMargin = 20;
        layoutParams.rightMargin = 20;
        card.setLayoutParams(layoutParams);
        webView.setWebViewClient(new CGWebClient(getApplicationContext(), finalData));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
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
