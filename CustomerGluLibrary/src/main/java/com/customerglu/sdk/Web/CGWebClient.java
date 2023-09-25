package com.customerglu.sdk.Web;

import static com.customerglu.sdk.Utils.Comman.build;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Environment;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Utils.CGConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

public class CGWebClient extends WebViewClient {
    Context context;
    HashMap<String, Object> webData;
    int count = 0;
    Activity activity;

    public CGWebClient(Context context) {
        this.context = context;

    }

    public CGWebClient(Context context, HashMap<String, Object> webData, Activity activity) {
        this.context = context;
        this.webData = webData;
        this.activity = activity;
    }


    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String url = request.getUrl().toString();
        //    printErrorLogs("shouldInterceptRequest " + url);
        String[] split = url.split("/");
        int size = split.length;
        String req = "/" + split[size - 1];
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(zip, "CustomerGlu");
        String asset_path = file.getPath() + "/build/";
        if (url.contains("/wallet")) {

            File asset_file = new File(asset_path + "/wallet/index.html");
            try {
                InputStream is = new FileInputStream(asset_file);
                return new WebResourceResponse("text/html", "UTF-8", is);


            } catch (FileNotFoundException e) {
            }
        }
        if (url.contains("/program-nudge")) {

            File asset_file = new File(asset_path + "/program-nudge/index.html");
            try {
                InputStream is = new FileInputStream(asset_file);
                return new WebResourceResponse("text/html", "UTF-8", is);


            } catch (FileNotFoundException e) {
            }
        }
        if (url.contains("/program/?token")) {

            File asset_file = new File(asset_path + "/program/index.html");
            try {
                InputStream is = new FileInputStream(asset_file);
                return new WebResourceResponse("text/html", "UTF-8", is);


            } catch (FileNotFoundException e) {
            }
        }
        if (url.contains("/embedded")) {

            File asset_file = new File(asset_path + "/embedded/index.html");
            try {
                InputStream is = new FileInputStream(asset_file);
                return new WebResourceResponse("text/html", "UTF-8", is);


            } catch (FileNotFoundException e) {
            }
        }
        if (url.contains("/reward/?token")) {

            File asset_file = new File(asset_path + "/reward/index.html");
            try {
                InputStream is = new FileInputStream(asset_file);
                return new WebResourceResponse("text/html", "UTF-8", is);


            } catch (FileNotFoundException e) {
            }
        }
        if (req.contains(".js")) {
            try {
                //   System.out.println("Start "+counter+"ms");

                File asset_file = new File(req);

                InputStream is = new FileInputStream(asset_path + asset_file);

                //   System.out.println("End "+counter+"ms");

                return new WebResourceResponse("text/javascript", "UTF-8", is);

            } catch (IOException e) {


            }
        }
        if (req.contains(".woff2")) {
            try {
                //   System.out.println("Start "+counter+"ms");

                File asset_file = new File(req);

                InputStream is = new FileInputStream(asset_path + "/fonts" + asset_file);

                //   System.out.println("End "+counter+"ms");

                return new WebResourceResponse("font/woff2", "UTF-8", is);

            } catch (IOException e) {


            }
        }
        if (req.contains(".woff")) {
            try {
                //   System.out.println("Start "+counter+"ms");

                File asset_file = new File(req);

                InputStream is = new FileInputStream(asset_path + "/fonts" + asset_file);

                //   System.out.println("End "+counter+"ms");

                return new WebResourceResponse("font/woff", "UTF-8", is);

            } catch (IOException e) {

            }
        }


        if (req.contains(".css")) {
            try {

                File asset_file = new File(asset_path + req);

                InputStream is = new FileInputStream(asset_file);


                return new WebResourceResponse("text/css", "UTF-8", is);

            } catch (IOException e) {

            }
        }
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {

            return build();
        }


        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (!CustomerGlu.isHyperLinkUrl && CustomerGlu.sslPinningEnable) {
            if (CustomerGlu.sslCertificate != null) {
                SslCertificate webViewSslCertificate = view.getCertificate();
                if (!webViewSslCertificate.toString().equalsIgnoreCase(CustomerGlu.sslCertificate.toString())) {
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        try {
            if (CustomerGlu.sslPinningEnable) {
                boolean isValidSSLCertificate = error.getCertificate().getValidNotAfterDate().after(new Date());

                if (!isValidSSLCertificate) {

                    printDebugLogs("ssl Invalid");
                    handler.cancel();
                }
                if (!CustomerGlu.isHyperLinkUrl) {
                    if (!view.getCertificate().toString().equalsIgnoreCase(CustomerGlu.sslCertificate.toString())) {
                        handler.cancel();
                    }
                }
            }
        } catch (Exception e) {
            printErrorLogs("" + e);
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        printDebugLogs("WebEvents onPageFinished");

        String campaignId = "";
        if (count == 0) {
            if (webData != null) {
                webData.put("webview_url", url);
                printDebugLogs("WebEvents loaded");
                if (webData.containsKey("campaignId")) {
                    campaignId = "" + webData.get("campaignId");
                }
                CustomerGlu.getInstance().cgAnalyticsEventManager(context, CGConstants.WEBVIEW_LOAD, campaignId, webData);
                count++;
            }

        }


        super.onPageFinished(view, url);
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//        if (pg != null) {
//            pg.setVisibility(View.GONE);
//        }
        super.onReceivedError(view, request, error);
    }
}
