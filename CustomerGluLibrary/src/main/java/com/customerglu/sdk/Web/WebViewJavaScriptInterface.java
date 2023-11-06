package com.customerglu.sdk.Web;


import static com.customerglu.sdk.CustomerGlu.doLoadCampaignAndEntryPointCall;
import static com.customerglu.sdk.CustomerGlu.euiCallbackHandler;
import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_CTA_CALLBACK;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.core.app.ShareCompat;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.NudgeConfiguration;
import com.customerglu.sdk.Utils.CGConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebViewJavaScriptInterface {
    private final Context context;
    private final Activity activity;
    private boolean closeOnDeeplink = true;
    //    List imageUrls;
//    List imageUriList;
    WebView webView;
    String text = "", image = "", channelName = "OTHERS";
    //ProgressDialog progressDialog;

    /*
     * Need a reference to the context in order to sent a post message
     */
    public WebViewJavaScriptInterface(Context context, Activity activity, boolean closeOnDeeplink) {
        this.context = context;
        this.activity = activity;
        this.closeOnDeeplink = closeOnDeeplink;
    }

    public WebViewJavaScriptInterface(Context context, Activity activity, boolean closeOnDeeplink, WebView webView) {
        this.context = context;
        this.activity = activity;
        this.closeOnDeeplink = closeOnDeeplink;
        this.webView = webView;
    }


    @JavascriptInterface
    public void callback(String message) {
        JSONObject data = null;
        printDebugLogs("JS callback " + message);
        try {
            data = new JSONObject(message);
            ArrayList<MetaData> metaData = new ArrayList<>();
            metaData.add(new MetaData("CTA Callback Data", data.toString()));
            CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CGConstants.CG_DIAGNOSTICS_CTA_CALLBACK, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, metaData);

            ArrayList<MetaData> responseMetaData = new ArrayList();
            responseMetaData.add(new MetaData("JS CTA Event", data.toString()));
            CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_CTA_CALLBACK, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, responseMetaData);
            String event = data.getString("eventName");

            if (event.equalsIgnoreCase("CLOSE")) {
                CustomerGlu.dismiss_trigger = CGConstants.UI_BUTTON;
                activity.finish();

            }
            if (event.equalsIgnoreCase("REQUEST_API_DATA")) {
                if (CustomerGlu.euiProxyEnabled) {

                    CustomerGlu.euiCallbackHandler.callJavaScriptFunction(webView, data);
                }

            }
            if (event.equalsIgnoreCase("REFRESH_API_DATA")) {
                if (CustomerGlu.euiProxyEnabled) {

                    euiCallbackHandler.getProgramData();
                    euiCallbackHandler.getRewardData();

                    euiCallbackHandler.callJavaScriptFunction(webView, data);
                }

            }

            if (event.equalsIgnoreCase("HIDE_LOADER")) {
                printDebugLogs("HIDE_LOADER");
                Intent intent = new Intent("HIDE_LOADER");
//                intent.putExtra("data", me.toString());
                activity.sendBroadcast(intent);

            }
            if (event.equalsIgnoreCase("OPEN_CG_WEBVIEW")) {
                JSONObject jsonObject = data.getJSONObject("data");
                String type = "";
                String url = "";
                String campaignId = "";
                String layoutType = "";
                int absoluteHeight = 0;
                int relativeHeight = 0;
                boolean closeOnDeeplink = false;
                String hidePrevious = "false";
                if (jsonObject.has("content")) {
                    JSONObject content = jsonObject.getJSONObject("content");
                    if (content.has("type")) {
                        type = content.getString("type");
                    }
                    if (content.has("url")) {
                        url = content.getString("url");
                    }
                    if (content.has("campaignId")) {
                        campaignId = content.getString("campaignId");
                    }
                }
                if (jsonObject.has("container")) {
                    JSONObject container = jsonObject.getJSONObject("container");
                    if (container.has("type")) {
                        layoutType = container.getString("type");
                    }
                    if (container.has("absoluteHeight")) {
                        absoluteHeight = Integer.parseInt(container.getString("absoluteHeight"));
                    }
                    if (container.has("relativeHeight")) {
                        relativeHeight = Integer.parseInt(container.getString("relativeHeight"));
                    }
                }
                if (jsonObject.has("hidePrevious")) {
                    hidePrevious = jsonObject.getString("hidePrevious");
                }
                if (hidePrevious.equalsIgnoreCase("true")) {
                    closeOnDeeplink = true;
                } else {
                    closeOnDeeplink = false;
                }
                NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                nudgeConfiguration.setCloseOnDeepLink(closeOnDeeplink);
                nudgeConfiguration.setLayout(layoutType);
                nudgeConfiguration.setOpacity(0.5);
                nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
                nudgeConfiguration.setRelativeHeight(relativeHeight);
                if (type.equalsIgnoreCase("WALLET")) {
                    CustomerGlu.getInstance().openWallet(context, nudgeConfiguration);
                } else if (type.equalsIgnoreCase("CAMPAIGN")) {
                    CustomerGlu.getInstance().loadCampaignById(context, campaignId, nudgeConfiguration);
                } else {
                    CustomerGlu.getInstance().displayCGNudge(context, url, "", nudgeConfiguration);
                }

                if (closeOnDeeplink) {
                    activity.finish();
                }

            }

            if (event.equalsIgnoreCase("OPEN_DEEPLINK")) {
                String ctaCloseOnDeepLink = "false";
                JSONObject me = data.getJSONObject("data");
                Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                intent.putExtra("data", me.toString());
                activity.sendBroadcast(intent);


                if (me.has("closeOnDeepLink")) {
                    ctaCloseOnDeepLink = me.getString("closeOnDeepLink");
                }


                if (me.has("isHandledByCG")) {
                    if (me.getString("isHandledByCG").equalsIgnoreCase("true")) {
                        if (me.has("deepLink")) {
                            String deepLinkUrl = me.getString("deepLink");
                            handleDeepLinkByCG(deepLinkUrl);
                        }

                    }

                }
                if (closeOnDeeplink || ctaCloseOnDeepLink.equalsIgnoreCase("true")) {
                    printDebugLogs("Webview closed");
                    CustomerGlu.dismiss_trigger = CGConstants.CTA_REDIRECT;
                    activity.finish();
                }

            }

            if (event.equalsIgnoreCase("ANALYTICS")) {

                JSONObject me = data.getJSONObject("data");
                if (me.has("event_name")) {
                    String event_name = me.getString("event_name");
                    if (event_name.equalsIgnoreCase("GAME_PLAYED")) {
                        doLoadCampaignAndEntryPointCall();
                    }

                }
                if (CustomerGlu.isAnalyticsEventEnabled) {
                    Intent intent = new Intent("CUSTOMERGLU_ANALYTICS_EVENT");
                    intent.putExtra("data", me.toString());
                    activity.sendBroadcast(intent);
                }
            }

            if (event.equalsIgnoreCase("SHARE")) {

                JSONObject me = data.getJSONObject("data");
                printDebugLogs(me.toString());
                text = me.getString("text");
                if (!text.isEmpty()) {
                    text = text.replace("\\n", "\n");
                }
                if (me.has("channelName")) {
                    channelName = me.getString("channelName");
                }
                if (me.has("image")) {
                    image = me.getString("image");

                }

//                imageUrls = new ArrayList<>();
//                imageUriList = new ArrayList<>();
//                progressDialog = new ProgressDialog(activity);
//                progressDialog.setMessage("Please wait");
//                progressDialog.setCancelable(false);
//                progressDialog.show();

                if (image.equalsIgnoreCase("")) {
                    if (channelName.equalsIgnoreCase("WHATSAPP")) {
                        sendToWhatsapp();
                    } else {
                        sendToOtherApps();
                    }

                } else {
                    Intent intent = new Intent("CUSTOMERGLU_SHARE_EVENT");
                    intent.putExtra("data", me.toString());
                    activity.sendBroadcast(intent);
                }
//                else {
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            imageUrls.add(image);
//                            try {
//                                sendImagesToOtherApp();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                printErrorLogs("WebViewJavaInterface  " + e);
//
//                            }
//                        }
//
//                    }, 500);
//                }
                if (closeOnDeeplink) {
                    Log.e("Cust", "Webview closed");
                    activity.finish();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // data has the event information
    }

    private void handleDeepLinkByCG(String deepLinkUrl) {
        Uri uri = Uri.parse(deepLinkUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void sendToOtherApps() {
        //  progressDialog.dismiss();
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Share")
                .setText(text)
                .getIntent();
        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    private void sendToWhatsapp() {
        //    progressDialog.dismiss();
        Intent intent = new Intent();
        intent.setPackage("com.whatsapp");
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        activity.startActivity(Intent.createChooser(intent, "share Image"));
    }

/*
    private void sendImagesToOtherApp() throws IOException {
        //Picasso
        for (Object imgUrl : imageUrls) {
            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {

                    Glide.with(context).asBitmap().load(String.valueOf(imgUrl)).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                imageUriList.add(getLocalBitmapUri(context, bitmap));
                                if (imageUriList.size() == imageUrls.size()) {
                                    if (channelName.equalsIgnoreCase("WHATSAPP")) {
                                        //         progressDialog.dismiss();
                                        shareImageToWhatsapp();
                                    } else {
                                        //    progressDialog.dismiss();
                                        shareImage(bitmap);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                printErrorLogs("Image Sharing " + e);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
            });

        }
    }
*/

//    private void shareImage(Bitmap bitmap) {
//        Intent shareIntent = null;
//        try {
//            shareIntent = ShareCompat.IntentBuilder.from(activity)
//                    .setType("image/*")
//                    .setChooserTitle("Share")
//                    .setText(text)
//                    .setStream(getLocalBitmapUri(context, bitmap))
//                    .getIntent();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
//            activity.startActivity(shareIntent);
//        }
//    }
//
//    private Uri getLocalBitmapUri(Context mContext, Bitmap bitmap) throws IOException {
//        Uri bmpUri = null;
//        try {
//            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_Image" + System.currentTimeMillis() + ".jpg");
//            FileOutputStream out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 85, out);
//            out.flush();
//            out.close();
//            file.setReadable(true, false);
//            bmpUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
//        } catch (Exception e) {
//            printErrorLogs("" + e);
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }

//
//    public void shareImageToWhatsapp() {
//        //multiple image
////     String smsNumber = “91”+ edtMob.getText().toString();
//        Intent intent = new Intent();
//        intent.setPackage("com.whatsapp");
//        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
////     intent.putExtra(“jid”, smsNumber + “@s.whatsapp.net”);
//        intent.putExtra(Intent.EXTRA_TEXT, text);
//        intent.setType("text/plain");
//        //    intent.putExtra(Intent.EXTRA_STREAM,imageUriList);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setType("image/*");
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<? extends Parcelable>) imageUriList);
////        context.startActivity(Intent.createChooser(intent,"share Image"));
//        activity.startActivity(Intent.createChooser(intent, "share Image"));
//        imageUriList.clear();
//        imageUrls.clear();
////        progressDialog.dismiss();
//    }

}