package com.customerglu.sdk.Web;


import static com.customerglu.sdk.Utils.CGConstants.CG_DIAGNOSTICS_CTA_CALLBACK;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.webkit.JavascriptInterface;

import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.MetaData;
import com.customerglu.sdk.Modal.NudgeConfiguration;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.entrypoints.CGEmbedView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmbeddedViewJavaScriptInterface {
    private final Context context;
    private final Activity activity;
    private final boolean closeOnDeeplink;
    List imageUrls;
    List imageUriList;
    String text = "", image = "", channelName = "";
    ProgressDialog progressDialog;
    CGEmbedView embeddedView;

    /*
     * Need a reference to the context in order to sent a post message
     */
    public EmbeddedViewJavaScriptInterface(Context context, Activity activity, boolean closeOnDeeplink, CGEmbedView embeddedView) {
        this.context = context;
        this.activity = activity;
        this.closeOnDeeplink = closeOnDeeplink;
        this.embeddedView = embeddedView;

    }

    @JavascriptInterface
    public void callback(String message) {
        JSONObject data = null;

        try {
            data = new JSONObject(message);
            printDebugLogs("JS Event " + data.toString());
            printDebugLogs(data.toString());
            ArrayList<MetaData> responseMetaData = new ArrayList();
            responseMetaData.add(new MetaData("Type", "Embed"));
            responseMetaData.add(new MetaData("JS CTA Event", data.toString()));
            CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CG_DIAGNOSTICS_CTA_CALLBACK, CGConstants.CG_LOGGING_EVENTS.DIAGNOSTICS, responseMetaData);
            String event = data.getString("eventName");

            if (event.equalsIgnoreCase("CLOSE")) {

                embeddedView.changeHeight(0);

            }
            if (event.equalsIgnoreCase("HIDE_LOADER")) {
                printDebugLogs("HIDE_LOADER");
                Intent intent = new Intent("HIDE_LOADER");
//                intent.putExtra("data", me.toString());
                activity.sendBroadcast(intent);

            }
            if (event.equalsIgnoreCase("DIMENSIONS_UPDATE")) {
                JSONObject me = data.getJSONObject("data");

                String height = me.getString("height");
                double changeHeight = Double.parseDouble(height);

                double finalHeight = (int) (changeHeight * Resources.getSystem().getDisplayMetrics().density);

                embeddedView.changeHeight(finalHeight);
                Map<String, Object> map = new HashMap();

                map.put(embeddedView.getEmbedViewId(), changeHeight);
                Gson gson = new Gson();
                JsonObject changedHeightObj = gson.toJsonTree(map).getAsJsonObject();
                Intent intent = new Intent("CGEMBED_FINAL_HEIGHT");
                intent.putExtra("data", changedHeightObj.toString());
                context.sendBroadcast(intent);
//                embeddedView.getEmbedViewId()
            }


            if (event.equalsIgnoreCase("OPEN_DEEPLINK")) {
                JSONObject me = data.getJSONObject("data");

                Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                intent.putExtra("data", me.toString());
                context.sendBroadcast(intent);

                if (closeOnDeeplink) {
                    embeddedView.changeHeight(0);
                }


            }

            if (event.equalsIgnoreCase("ANALYTICS") && CustomerGlu.isAnalyticsEventEnabled) {

                JSONObject me = data.getJSONObject("data");


                Intent intent = new Intent("CUSTOMERGLU_ANALYTICS_EVENT");
                intent.putExtra("data", me.toString());
                context.sendBroadcast(intent);

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
                    JSONObject content = data.getJSONObject("content");
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
                    JSONObject container = data.getJSONObject("container");
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
                    CustomerGlu.getInstance().displayCGNudge(context, url, nudgeConfiguration);
                }

            }


            if (event.equalsIgnoreCase("SHARE")) {

                JSONObject me = data.getJSONObject("data");
                printDebugLogs(me.toString());
                text = me.getString("text");

                channelName = me.getString("channelName");
                if (me.has("image")) {
                    image = me.getString("image");

                }

                imageUrls = new ArrayList<>();
                imageUriList = new ArrayList<>();
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (image.equalsIgnoreCase("")) {
                    if (channelName.equalsIgnoreCase("WHATSAPP")) {
                        sendToWhatsapp();
                    } else {
                        sendToOtherApps();
                    }

                } else {
                    //Handler
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageUrls.add(image);
                            try {
                                sendImagesToOtherApp();
                            } catch (IOException e) {
                                e.printStackTrace();
                                printErrorLogs("WebViewJavaInterface  " + e);

                            }
                        }

                    }, 500);
                }
//                if (closeOnDeeplink) {
//                    Log.e("Cust", "Webview closed");
//                    activity.finish();
//                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // data has the event information
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void sendToOtherApps() {
        progressDialog.dismiss();
        if (activity != null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setChooserTitle("Share")
                    .setText(text)
                    .getIntent();
            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(shareIntent);
            }
        }
    }

    private void sendToWhatsapp() {
        progressDialog.dismiss();
        if (activity != null) {

            Intent intent = new Intent();
            intent.setPackage("com.whatsapp");
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            activity.startActivity(Intent.createChooser(intent, "share Image"));
        }
    }

    private void sendImagesToOtherApp() throws IOException {
        //Picasso
        for (Object imgUrl : imageUrls) {
            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(context).load(String.valueOf(imgUrl)).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                imageUriList.add(getLocalBitmapUri(bitmap, context));
                                if (imageUriList.size() == imageUrls.size()) {
                                    if (channelName.equalsIgnoreCase("WHATSAPP")) {
                                        progressDialog.dismiss();
                                        shareImageToWhatsapp();
                                    } else {
                                        progressDialog.dismiss();
                                        shareImage(bitmap);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                printErrorLogs("Image Sharing " + e);
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            printErrorLogs("Picasso Check:" + errorDrawable);

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                }
            });

        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void shareImage(Bitmap bitmap) {
        Intent shareIntent = null;
        try {
            if (activity != null) {

                shareIntent = ShareCompat.IntentBuilder.from(activity)
                        .setType("text/plain")
                        .setChooserTitle("Share")
                        .setText(text)
                        .setStream(getLocalBitmapUri(bitmap, context))
                        .getIntent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (shareIntent != null && shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    private Uri getLocalBitmapUri(Bitmap bitmap, Context mContext) throws IOException {
        Uri bmpUri = null;
        try {
            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_Image" + System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, out);
            out.flush();
            out.close();
            file.setReadable(true, false);
            bmpUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } catch (Exception e) {
            printErrorLogs("" + e);
            e.printStackTrace();
        }
        return bmpUri;
    }

    public void shareImageToWhatsapp() {
        //multiple image
//     String smsNumber = “91”+ edtMob.getText().toString();
        if (activity != null) {

            Intent intent = new Intent();
            intent.setPackage("com.whatsapp");
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//     intent.putExtra(“jid”, smsNumber + “@s.whatsapp.net”);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            //    intent.putExtra(Intent.EXTRA_STREAM,imageUriList);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("image/png");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<? extends Parcelable>) imageUriList);
//        context.startActivity(Intent.createChooser(intent,"share Image"));
            activity.startActivity(Intent.createChooser(intent, "share Image"));
            imageUriList.clear();
            imageUrls.clear();
        }
//        progressDialog.dismiss();
    }

}