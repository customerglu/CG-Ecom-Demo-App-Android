package com.customerglu.sdk.Web;

import static com.customerglu.sdk.Utils.Comman.printErrorLogs;
import static com.customerglu.sdk.Utils.Comman.validateURL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.R;

public class RewardWeb extends Activity {
    public static boolean exit = false;
    String url = "";
    ProgressBar pg;
    WebViewJavaScriptInterface webViewJavaScriptInterface;
    WebView web;


    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);
                String s = "Uncaught Exception" + e;

                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);

            }
        });
        findViews();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void findViews() {
        url = getIntent().getStringExtra("url");
        web = findViewById(R.id.web);
        pg = findViewById(R.id.pg);
        String color;
        color = CustomerGlu.configure_loader_color;
        if (color.isEmpty()) {
            color = "#FF000000";
        }
        pg.getIndeterminateDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
        web.setWebViewClient(new CGWebClient(getApplicationContext()));
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAppCacheEnabled(true);
        web.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.addJavascriptInterface(new WebViewJavaScriptInterface(getApplicationContext(), this, true), "app"); // **IMPORTANT** call it app
        web.loadUrl(validateURL(url));

    }

}
