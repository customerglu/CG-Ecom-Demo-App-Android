package com.customerglu.sdk.entrypoints;

import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_LOAD;
import static com.customerglu.sdk.Utils.Comman.isValidURL;
import static com.customerglu.sdk.Utils.Comman.validateURL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.EntryPointsData;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.MobileData;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.Modal.ScreenListModal;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Web.CGWebClient;
import com.customerglu.sdk.Web.EmbeddedViewJavaScriptInterface;
import com.customerglu.sdk.custom.base.BaseRelativeLayout;
import com.customerglu.sdk.custom.views.ProgressLottieView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CGEmbedView extends BaseRelativeLayout {
    public List<MobileData.Content> contentList = new ArrayList<>();
    WebView cg_webView;
    EntryPointsModel entryPointsModel;
    RelativeLayout main;
    double height = 0;
    int id;
    Context context;
    String elementId, opacity = "0.5", campaign_id = "", screenName = "";
    int screenHeight, screenWidth;
    BroadcastReceiver broadcastReceiver;
    boolean isLoaded = false;
    Boolean isThere = false;
    Boolean isClosed = false;
    double absoluteHeight = 0;
    double relativeHeight = 0;
    CountDownTimer cTimer = null;
    ProgressLottieView progressLottieView;
    String darkMode = "darkMode=false";

    public CGEmbedView(Context context, String id) {
        super(context);
        this.context = context;
        elementId = id;
        System.out.println(elementId);
        LayoutInflater.from(context).inflate(R.layout.embedded_view, this, true);
        startTimer();
        init();
    }

    public CGEmbedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        id = this.getId();
        main = findViewById(id);

        //    main.setVisibility(GONE);
        elementId = getResources().getResourceEntryName(this.getId());
        System.out.println(elementId);
        LayoutInflater.from(context).inflate(R.layout.embedded_view, this, true);

//        findViews();
        startTimer();
        init();
    }

    void startTimer() {
        cTimer = new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (cg_webView != null) {

                    cg_webView.setVisibility(View.VISIBLE);

                    stopLottieProgressView();
                    cancelTimer();
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

    private Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    private void init() {
        if (!CustomerGlu.embedIdList.contains(elementId)) {
            CustomerGlu.embedIdList.add(elementId);
            ScreenListModal screenListModal = new ScreenListModal();
            screenListModal.setEmbedIds(CustomerGlu.embedIdList);
            CustomerGlu.getInstance().sendActivities(context, screenListModal);
        }

        if (!CustomerGlu.lastScreenName.equalsIgnoreCase("")) {
            screenName = CustomerGlu.lastScreenName;
        } else {
            screenName = context.getClass().getCanonicalName();
        }
        if (CustomerGlu.isDarkModeEnabled(CustomerGlu.globalContext)) {
            darkMode = "darkMode=true";
        }
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        Log.e("screenHeight", "" + screenHeight);
        Log.e("screenWidth", "" + screenWidth);
        if (CustomerGlu.isBannerEntryPointsEnabled && CustomerGlu.isBannerEntryPointsHasData) {
            getEntryPointData();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("==================Recieved============");
                if (intent.getAction().equalsIgnoreCase("CUSTOMERGLU_ENTRY_POINT_DATA")) {
                    if (!isLoaded) {
                        startTimer();
                        getEntryPointData();

                    }
                }
                if (intent.getAction().equalsIgnoreCase("HIDE_LOADER")) {
                    stopLottieProgressView();
                    invalidate();
                    if (cg_webView != null) {
                        cg_webView.setVisibility(View.VISIBLE);
                    }
                    cancelTimer();

                }
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter("CUSTOMERGLU_ENTRY_POINT_DATA"));
        context.registerReceiver(broadcastReceiver, new IntentFilter("HIDE_LOADER"));

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getEntryPointData() {
        try {

            entryPointsModel = CustomerGlu.entryPointsModel;

            if (entryPointsModel == null || entryPointsModel.getEntryPointsData() == null) {
                Comman.printErrorLogs("EntryPoint Data Null");

            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (entryPointsModel.getSuccess()) {
                            isLoaded = true;
                            //    String bannerData = CustomerGlu.bannerCountData.toString();

                            if (entryPointsModel.getEntryPointsData().size() > 0) {
                                for (int i = 0; i < entryPointsModel.getEntryPointsData().size(); i++) {
                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData() != null) {
                                        if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                                            if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("EMBEDDED")) {
                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getBannerId().equalsIgnoreCase(elementId)) {
                                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations() != null) {
                                                        if (!entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations().equalsIgnoreCase("INVALID")) {
                                                            if (entryPointsModel.getEntryPointsData().get(i).getVisible()) {
                                                                //Banner container
                                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                                                    isClosed = entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().get(0).isCloseOnDeepLink();
                                                                }


                                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackgroundOpacity() != null) {
                                                                    opacity = entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackgroundOpacity();
                                                                }

                                                                // Banner content
                                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                                                                    campaign_id = entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().get(0).getCampaignId();
                                                                    absoluteHeight = entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().get(0).getAbsoluteHeight();
                                                                    relativeHeight = entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().get(0).getRelativeHeight();


                                                                    if (relativeHeight > 0) {

                                                                        height = (int) screenHeight * (relativeHeight / 100);
                                                                    } else if (absoluteHeight > 0) {
                                                                        height = (int) (absoluteHeight * Resources.getSystem().getDisplayMetrics().density);
                                                                    } else {
                                                                        height = 0;
                                                                    }
                                                                    sendAnalytics(entryPointsModel.getEntryPointsData().get(i));
                                                                    findViews();

                                                                }


                                                            }
                                                        }
                                                    }
                                                    break;
                                                }

                                            }


                                        }
                                    }
                                }

                            }
                            isLoaded = false;
                        }
                    }

                });
            }


        } catch (Exception e) {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "Registration Api Fails" + e);

        }

    }

    private void sendAnalytics(EntryPointsData entryPointsDataList) {
        Map<String, Object> nudgeData = new HashMap<>();
        Map<String, Object> entry_point_content = new HashMap<>();
        if (entryPointsDataList.getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
            entry_point_content.put("type", "WALLET");
            entry_point_content.put("campaign_id", entryPointsDataList.getMobileData().getContent().get(0).getCampaignId());
            entry_point_content.put("static_url", "");

        } else {
            entry_point_content.put("type", "CAMPAIGN");
            entry_point_content.put("campaign_id", entryPointsDataList.getMobileData().getContent().get(0).getCampaignId());
            entry_point_content.put("static_url", "");

        }
        if (isValidURL(entryPointsDataList.getMobileData().getContent().get(0).getCampaignId())) {
            entry_point_content.put("type", "STATIC");
            entry_point_content.put("campaign_id", "");
            entry_point_content.put("static_url", entryPointsDataList.getMobileData().getContent().get(0).getCampaignId());

        }
        nudgeData.put("entry_point_id", entryPointsDataList.getMobileData().getContent().get(0).get_id());
        nudgeData.put("entry_point_location", screenName);
        if (entryPointsDataList.getName() != null) {
            nudgeData.put("entry_point_name", entryPointsDataList.getName());
        } else {
            nudgeData.put("entry_point_name", "");
        }
        nudgeData.put("entry_point_content", entry_point_content);
        nudgeData.put("entry_point_container", entryPointsDataList.getMobileData().getContainer().getType());
        CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);

        return super.onInterceptTouchEvent(ev);
    }

    public void changeHeight(double changedHeight) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                height = changedHeight;
                cg_webView = findViewById(R.id.cg_webView);

                ViewGroup.LayoutParams params = cg_webView.getLayoutParams();
                params.height = (int) changedHeight;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                cg_webView.setLayoutParams(params);

            }
        });
    }

    public String getEmbedViewId() {
        return elementId;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void findViews() {
        boolean closeOnDeepLink = false;
        String color;
        color = CustomerGlu.configure_loader_color;
        if (color.isEmpty()) {
            color = "#FF000000";
        }

        cg_webView = findViewById(R.id.cg_webView);
        progressLottieView = findViewById(R.id.lottie_view);
        ViewGroup.LayoutParams params = progressLottieView.getLayoutParams();
        params.height = (int) height;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        progressLottieView.setLayoutParams(params);
        setupProgressView(progressLottieView);
        startLottieProgressView();
        ViewGroup.LayoutParams webParams = cg_webView.getLayoutParams();
        webParams.height = (int) height;
        webParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        cg_webView.setLayoutParams(webParams);
        cg_webView.setWebViewClient(new CGWebClient(context));
        cg_webView.getSettings().setJavaScriptEnabled(true);
        cg_webView.setInitialScale(1);
        cg_webView.getSettings().setLoadWithOverviewMode(true);
        cg_webView.getSettings().setUseWideViewPort(true);
        Map<String, Object> map = new HashMap();

        map.put(elementId, height);
        Gson gson = new Gson();
        JsonObject changedHeightObj = gson.toJsonTree(map).getAsJsonObject();
        Intent intent = new Intent("CGEMBED_FINAL_HEIGHT");
        intent.putExtra("data", changedHeightObj.toString());
        context.sendBroadcast(intent);

        cg_webView.addJavascriptInterface(new EmbeddedViewJavaScriptInterface(context, getActivity(context), isClosed, CGEmbedView.this), "app"); // **IMPORTANT** call it app
        if (campaign_id.equalsIgnoreCase("")) {

            CustomerGlu.getInstance().retrieveData(context, new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    String default_url = rewardModel.defaultUrl;
                    cg_webView.loadUrl(validateURL(default_url + darkMode));

                }

                @Override
                public void onFailure(String message) {
                    String url = "https://end-user-ui.customerglu.com/error/?source=native-sdk&code=" + 504 + "&message=" + "Please Try again later";
                    cg_webView.loadUrl(url);

                }
            });
        } else {

            CustomerGlu.getInstance().retrieveData(context, new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    isThere = false;
                    Gson gson = new Gson();
                    String json = gson.toJson(rewardModel);


                    if (rewardModel.getCampaigns().size() <= 0) {

                        String default_url = rewardModel.defaultUrl;
                        cg_webView.loadUrl(validateURL(default_url + darkMode));
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
                                String url = rewardModel.getCampaigns().get(i).getUrl();
                                //   System.out.println(url);
                                //     Toast.makeText(OpenCustomerGluWeb.this, url, Toast.LENGTH_SHORT).show();
                                isThere = true;

                                cg_webView.loadUrl(validateURL(url + darkMode));
                                Map<String, Object> map = new HashMap();

                                map.put(elementId, height);
                                JsonObject changedHeightObj = gson.toJsonTree(map).getAsJsonObject();
                                Intent intent = new Intent("CGEMBED_FINAL_HEIGHT");
                                intent.putExtra("data", changedHeightObj.toString());
                                context.sendBroadcast(intent);
                                break;
                            }

                        }
                        if (!isThere) {
                            String default_url = rewardModel.defaultUrl;
                            cg_webView.loadUrl(validateURL(default_url + darkMode));
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    String url = "https://end-user-ui.customerglu.com/error/?source=native-sdk&code=" + 504 + "&message=" + "Please Try again later";
                    cg_webView.loadUrl(url);
                }
            });
        }


    }

}
