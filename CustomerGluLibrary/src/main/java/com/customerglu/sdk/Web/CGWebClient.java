package com.customerglu.sdk.Web;

import static com.customerglu.sdk.Utils.Comman.build;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
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
import java.util.HashMap;

public class CGWebClient extends WebViewClient {
    Context context;
    HashMap<String, Object> webData;
    int count = 0;

    public CGWebClient(Context context) {
        this.context = context;

    }

    public CGWebClient(Context context, HashMap<String, Object> webData) {
        this.context = context;
        this.webData = webData;
    }


    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String url = request.getUrl().toString();
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

        //  Log.e("CustomerGlu","cache miss");

        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        printDebugLogs("WebEvents onPageFinished");


        //   pg.setVisibility(View.GONE);
        if (count == 0) {
            if (webData != null) {
                webData.put("webview_url", url);
                printDebugLogs("WebEvents loaded");
                CustomerGlu.getInstance().cgAnalyticsEventManager(context, CGConstants.WEBVIEW_LOAD, webData);
                count++;
            }

        }


        super.onPageFinished(view, url);
    }

//    @Override
//    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//        if (pg != null) {
////            pg.setVisibility(View.GONE);
//        }
//        super.onReceivedError(view, errorCode, description, failingUrl);
//    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//        if (pg != null) {
//            pg.setVisibility(View.GONE);
//        }
        super.onReceivedError(view, request, error);
    }
}
