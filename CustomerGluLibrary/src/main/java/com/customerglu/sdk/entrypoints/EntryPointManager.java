package com.customerglu.sdk.entrypoints;


import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static com.customerglu.sdk.CustomerGlu.myThread;
import static com.customerglu.sdk.Utils.CGConstants.CG_OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_CLICK;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_DISMISS;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_LOAD;
import static com.customerglu.sdk.Utils.CGConstants.FLOATING_DATE;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_DEEPLINK;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WEBLINK;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.EntryPointsData;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.MobileData;
import com.customerglu.sdk.Modal.NudgeConfiguration;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class EntryPointManager extends View {


    private final static float CLICK_DRAG_TOLERANCE = 20; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    RelativeLayout close_lyt, closeButtonLayout, closeLayout, myLayout;
    Context context;
    String image;
    String open_layout = "", opacity = "0.5";
    String popup_open_layout = "FULL_DEFAULT";
    String entrypointName = "";
    String popupOpacity = "0.5";
    EntryPointsModel entryPointsModel;
    boolean isDraggable;
    ImageView closeButton;
    int screenHeight, screenWidth;
    int curntPositionX = 0, curntPositionY = 0;
    String screenName = "";
    List<String> disallowedList;
    List<String> allowedList;
    static EntryPointManager entryPointManager;
    RelativeLayout lContainerLayout;
    HashMap<String, Integer> campaignCountObj = null;
    ArrayList<EntryPointsData> entryPointsDataList;
    boolean isLoaded = false;
    BroadcastReceiver broadcastReceiver;
    private float downRawX, downRawY;
    private float dX, dY;

    public EntryPointManager(Context context) {
        super(context);

        this.context = context;


        screenName = context.getClass().getCanonicalName();
        printDebugLogs(screenName);
        init();

    }

    public static EntryPointManager getInstance(Context mContext, String currentScreenName) {

        if (entryPointManager == null) {
            entryPointManager = new EntryPointManager(mContext, currentScreenName);
        }
        if (mContext != null) {
            entryPointManager.context = mContext;
        }
        return entryPointManager;
    }

    public EntryPointManager(Context context, String currentScreenName) {
        super(context);

        this.context = context;


        screenName = currentScreenName;
        printDebugLogs(screenName);
        init();

    }

    public void setScreenName(String newScreenName) {
        screenName = newScreenName;
        getEntryPointData();
    }
//    public void deRegisterReceiver() {
//        context.unregisterReceiver(broadcastReceiver);
//    }

    // Setup views
    private void init() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                printDebugLogs("==================Recieved============");
                if (intent.getAction().equalsIgnoreCase("CUSTOMERGLU_ENTRY_POINT_DATA")) {
                    if (!isLoaded) {
                        getEntryPointData();
                    }
                }
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter("CUSTOMERGLU_ENTRY_POINT_DATA"));

        this.setId(R.id.web);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        if (CustomerGlu.isBannerEntryPointsEnabled && CustomerGlu.isBannerEntryPointsHasData) {

            getEntryPointData();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }

    public void getEntryPointData() {

        try {
            String current_date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    entryPointsModel = CustomerGlu.entryPointsModel;
                    if (entryPointsModel == null || entryPointsModel.getEntryPointsData() == null) {
                        printErrorLogs("EntryPoint Data Null");

                    } else {
                        if (entryPointsModel.getSuccess()) {
                            isLoaded = true;
                            for (int i = 0; i < CustomerGlu.entryPointId.size(); i++) {
                                @SuppressLint("ResourceType") View myView = ((Activity) context).findViewById(CustomerGlu.entryPointId.get(i));
                                if (myView != null) {
                                    printDebugLogs("Remove" + "" + CustomerGlu.entryPointId.get(i));
                                    ViewGroup parent = (ViewGroup) myView.getParent();
                                    parent.removeView(myView);
                                }
                            }
                            campaignCountObj = Prefs.getEncDismissedEntryPoints(context);

                            entryPointsDataList = new ArrayList<>();

                            if (entryPointsModel.getEntryPointsData().size() > 0) {


                                printDebugLogs("--------------getEntryPointsData()------------");

                                for (int i = 0; i < entryPointsModel.getEntryPointsData().size(); i++) {

                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData() != null) {
                                        //   Common.printDebugLogs("--------------getMobileData()------------");

                                        if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                                            //   Common.printDebugLogs("--------------getContainer()------------");


                                            //        Common.printDebugLogs("--------------getElementId------------");
                                            if (entryPointsModel.getEntryPointsData().get(i).getVisible()) {

                                                //      Common.printDebugLogs("--------------visible------------");
                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("FLOATING")) {


                                                    //   printErrorLogs("CustomerGlu", "HashMap " + CustomerGlu.dismissedEntryPoints.size());
                                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations() != null) {
                                                        if (!entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations().equalsIgnoreCase("INVALID")) {
                                                            entryPointsDataList.add(entryPointsModel.getEntryPointsData().get(i));
                                                        }
                                                    } else {
                                                        entryPointsDataList.add(entryPointsModel.getEntryPointsData().get(i));
                                                    }

                                                }


                                            }


                                        }

                                        //     break;


                                    }
                                }
                                //  printErrorLogs("Cust", "entryPointDataList" + entryPointsDataList.size());

                                if (campaignCountObj == null || campaignCountObj.isEmpty()) {
                                    printErrorLogs("Cust Pref null Object Initialized");
                                    campaignCountObj = new HashMap<>();
                                    for (int i = 0; i < entryPointsDataList.size(); i++) {

                                        campaignCountObj.put(entryPointsDataList.get(i).get_id(), 0);
                                        //  campaignCountObj.put("", entry_id);
                                    }
                                    printErrorLogs("Cust" + campaignCountObj.size());
                                    Prefs.putEncDismissedEntryPoints(context, campaignCountObj);

                                }

                                if (entryPointsDataList.size() > 0) {


                                    /* Checking if whether key is present or not if not then add it */


                                    for (int j = 0; j < entryPointsDataList.size(); j++) {

                                        if (!campaignCountObj.containsKey(entryPointsDataList.get(j).get_id())) {

                                            //       printErrorLogs("Cust", "added new " + entryPointsDataList.get(j).get_id());
                                            campaignCountObj.put(entryPointsDataList.get(j).get_id(), 0);

                                        }

                                    }

                                    /* Checking if whether key is present in response if not then Remove it from Pref */

                                    for (int i = 0; i < campaignCountObj.size(); i++) {
                                        boolean isPresent = false;
                                        Object[] keys = campaignCountObj.keySet().toArray();
                                        String id = String.valueOf(keys[i]);
                                        printErrorLogs("size " + keys.length);
                                        printErrorLogs(id);
                                        for (int j = 0; j < entryPointsDataList.size(); j++) {

                                            if (id.equals(entryPointsDataList.get(j).get_id())) {
                                                isPresent = true;
                                                String daily_refresh_date = Prefs.getEncKey(context, FLOATING_DATE);

                                                if (!current_date.equalsIgnoreCase(daily_refresh_date) && entryPointsDataList.get(j).getMobileData().getConditions().getShowCount().isDailyRefresh()) {
                                                    campaignCountObj.remove(id);
                                                    //        printErrorLogs("Removed", entryPointsDataList.get(i).get_id());
                                                    campaignCountObj.put(entryPointsDataList.get(j).get_id(), 0);
                                                }
                                                break;
                                            }
                                        }

                                        if (!isPresent) {
                                            //  printErrorLogs("Cust", "removed");
                                            campaignCountObj.remove(id);
                                        }

                                    }

                                    Prefs.putEncDismissedEntryPoints(context, campaignCountObj);
                                }
                                //   printErrorLogs("campaignCountObj Size ", "" + campaignCountObj.size());
                                try {
                                    if (entryPointsDataList.size() > 0) {
                                        for (int i = 0; i < entryPointsDataList.size(); i++) {
                                            int count = campaignCountObj.get(entryPointsDataList.get(i).get_id());

                                            printErrorLogs("CalledTimes " + i);
                                            printErrorLogs("CountTimes " + count);
                                            printErrorLogs("campaignOBJSize " + campaignCountObj.size());

                                            if (campaignCountObj.get(entryPointsDataList.get(i).get_id()) != null && campaignCountObj.get(entryPointsDataList.get(i).get_id()) < entryPointsDataList.get(i).getMobileData().getConditions().getShowCount().getCount()) {
                                                //         printErrorLogs("Null", "Times " + i);

                                                if (!CustomerGlu.dismissedEntryPoints.containsKey(entryPointsDataList.get(i).get_id())) {
                                                    //            printErrorLogs("Not dismissed", "Times " + i);
                                                    String date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

                                                    if (entryPointsDataList.get(i).getMobileData().getConditions().getShowCount().isDailyRefresh()) {
                                                        //            printErrorLogs("daily ", "Times " + i);

                                                        String daily_refresh_date = Prefs.getEncKey(context, FLOATING_DATE);
                                                        printErrorLogs("daily date " + daily_refresh_date);
                                                        if (!daily_refresh_date.equalsIgnoreCase("")) {
                                                            // Need to save json
                                                            if (daily_refresh_date.equalsIgnoreCase(date) && count < entryPointsDataList.get(i).getMobileData().getConditions().getShowCount().getCount()) {
                                                                printErrorLogs("daily count " + count);

                                                                Prefs.putEncKey(context, FLOATING_DATE, date);

                                                                if (entryPointsDataList.get(i).getMobileData().getContainer().getAndroid() != null) {
                                                                    allowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getAllowedActitivityList();
                                                                    disallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getDisallowedActitivityList();
                                                                    if (allowedList.size() > 0 && disallowedList.size() > 0) {
                                                                        if (!disallowedList.contains(screenName)) {
                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

                                                                        }
                                                                    } else if (allowedList.size() > 0) {
                                                                        if (allowedList.contains(screenName)) {
                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                        }
                                                                    } else if (disallowedList.size() > 0) {
                                                                        if (!disallowedList.contains(screenName)) {
                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                        }
                                                                    }

                                                                }
                                                            } else if (!daily_refresh_date.equalsIgnoreCase(date)) {
                                                                Prefs.putEncKey(context, FLOATING_DATE, date);

                                                                if (entryPointsDataList.get(i).getMobileData().getContainer().getAndroid() != null) {
                                                                    allowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getAllowedActitivityList();
                                                                    disallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getDisallowedActitivityList();
                                                                    if (allowedList.size() > 0 && disallowedList.size() > 0) {
                                                                        if (!disallowedList.contains(screenName)) {


                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

                                                                        }
                                                                    } else if (allowedList.size() > 0) {
                                                                        if (allowedList.contains(screenName)) {
                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                        }
                                                                    } else if (disallowedList.size() > 0) {
                                                                        if (!disallowedList.contains(screenName)) {
                                                                            myLayout = addButton(i);
                                                                            ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                        }
                                                                    }


                                                                }
                                                            }
                                                        } else {
                                                            Prefs.putEncKey(context, FLOATING_DATE, date);
                                                            if (entryPointsDataList.get(i).getMobileData().getContainer().getAndroid() != null) {
                                                                allowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getAllowedActitivityList();
                                                                disallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getDisallowedActitivityList();
                                                                if (allowedList.size() > 0 && disallowedList.size() > 0) {
                                                                    if (!disallowedList.contains(screenName)) {
                                                                        myLayout = addButton(i);
                                                                        ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

                                                                    }
                                                                } else if (allowedList.size() > 0) {
                                                                    if (allowedList.contains(screenName)) {
                                                                        myLayout = addButton(i);
                                                                        ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                    }
                                                                } else if (disallowedList.size() > 0) {
                                                                    if (!disallowedList.contains(screenName)) {
                                                                        myLayout = addButton(i);
                                                                        ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                    }
                                                                }


                                                            }
                                                        }


                                                    } else {
                                                        //    printErrorLogs("Called", "called");
                                                        if (entryPointsDataList.get(i).getMobileData().getContainer().getAndroid() != null) {
                                                            allowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getAllowedActitivityList();
                                                            disallowedList = entryPointsDataList.get(i).getMobileData().getContainer().getAndroid().getDisallowedActitivityList();
                                                            if (allowedList.size() > 0 && disallowedList.size() > 0) {
                                                                if (!disallowedList.contains(screenName)) {

                                                                    myLayout = addButton(i);
                                                                    ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

                                                                }
                                                            } else if (allowedList.size() > 0) {
                                                                if (allowedList.contains(screenName)) {

                                                                    myLayout = addButton(i);
                                                                    ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                }
                                                            } else if (disallowedList.size() > 0) {
                                                                if (!disallowedList.contains(screenName)) {

                                                                    myLayout = addButton(i);
                                                                    ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
                                                                }
                                                            }


                                                        }
                                                    }

                                                }

                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                    printErrorLogs("EntryPoints Null " + e);
                                    CustomerGlu.getInstance().sendCrashAnalytics(context, e.toString());
                                }


                                addCloseLayout();
                                addCloseButton();

                                /**   Display PopUp Logic  */
                                //     CustomerGlu.getInstance().popupEntryPoint(context, screenName);
                                CustomerGlu.getInstance().showPopUp(context, screenName);


                            }

                        }
                        isLoaded = false;
                    }
                }
            });

        } catch (Exception e) {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "Floating button error" + e);

        }

    }

    public void popupDismissThread() {
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

        for (Thread thread : setOfThread) {
            if (thread.getId() == myThread) {
                printErrorLogs("" + myThread);
                thread.interrupt();
            }
        }
    }


    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor", "ResourceType"})
    private RelativeLayout addButton(int i) {
        //  String screenName = getContext().getClass().getCanonicalName();
        //  printErrorLogs("CustomerGlu", screenName);
        String floatingButtonUrl = "";
        MobileData.Content data = entryPointsDataList.get(i).getMobileData().getContent().get(0);
        lContainerLayout = new RelativeLayout(context);
        lContainerLayout.setId(1000013 + i);
        if (!CustomerGlu.entryPointId.contains(lContainerLayout.getId())) {
            CustomerGlu.entryPointId.add(lContainerLayout.getId());
        }


        lContainerLayout.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));


// Custom view
        CardView cardView = new CardView(context);

        if (entryPointsDataList.get(i).getMobileData().getContainer().getBorderRadius() != null) {
            int borderRadius = Integer.parseInt(entryPointsDataList.get(i).getMobileData().getContainer().getBorderRadius());
            cardView.setRadius(borderRadius);
        } else {
            cardView.setRadius(0);
        }
        cardView.setElevation(0);
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        ImageView myImageView = new ImageView(context);
        myImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        myImageView.setLayoutParams(new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
        cardView.addView(myImageView);


        // Floating Banner draggable condition
        if (entryPointsDataList.get(i).getMobileData().getConditions() != null) {
            isDraggable = entryPointsDataList.get(i).getMobileData().getConditions().getDraggable();
            if (entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity() != null) {
                opacity = entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity();
            }
        }
        if (!isDraggable) {
            myImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUrlValiadte = false;

                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                        isUrlValiadte = Comman.isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                    }
                    if (isUrlValiadte) {
                        //    CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", "CUSTOM_URL", entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                    } else {
                        //  CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                    }
                    printDebugLogs(open_layout);
                    double relativeHeight = 0;
                    double absoluteHeight = 0;
                    boolean isClose = false;
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                        isClose = entryPointsDataList.get(i).getMobileData().getContent().get(0).isCloseOnDeepLink();
                    }
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                        relativeHeight = entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight();
                    }
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                        absoluteHeight = entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight();
                    }
                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction() != null) {
                        switch (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getType()) {
                            case OPEN_DEEPLINK:
                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                                    try {
                                        JSONObject dataObject = new JSONObject();
                                        dataObject.put("deepLink", entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());

                                        Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                                        intent.putExtra("data", dataObject.toString());
                                        context.sendBroadcast(intent);
                                        if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK() != null && entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK()) {
                                            Uri uri = Uri.parse(entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());
                                            Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                                            actionIntent.setData(uri);
                                            context.startActivity(intent);
                                        }
                                    } catch (Exception e) {
                                        printErrorLogs("" + e);
                                    }
                                }
                                break;
                            case OPEN_WEBLINK:
                                NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                                nudgeConfiguration.setRelativeHeight(relativeHeight);
                                nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
                                nudgeConfiguration.setLayout(data.getOpenLayout());
                                nudgeConfiguration.setCloseOnDeepLink(isClose);
                                nudgeConfiguration.setOpacity(Double.parseDouble(opacity));
                                nudgeConfiguration.setHyperlink(true);
                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                                    CustomerGlu.getInstance().displayCGNudge(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl(), nudgeConfiguration);
                                }
                                break;

                            case OPEN_WALLET:
                                CustomerGlu.getInstance().loadPopUpBanner(context, CG_OPEN_WALLET, entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                                break;

                            default:
                                CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                        }
                    } else {
                        CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                    }

                }
            });
        }


        // Floating Banner container
        String position = entryPointsDataList.get(i).getMobileData().getContainer().getPosition();
        //    printErrorLogs("CustomerGlu", position);
        int height = (screenHeight / 100) * Integer.parseInt(entryPointsDataList.get(i).getMobileData().getContainer().getHeight());
        int width = (screenWidth / 100) * Integer.parseInt(entryPointsDataList.get(i).getMobileData().getContainer().getWidth());

        if (position.equalsIgnoreCase("BOTTOM-LEFT")) {
            //   printErrorLogs("CustomerGlu", "BOTTOM-LEFT");

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.setMargins(10, 0, 0, screenHeight / 20);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("BOTTOM-CENTER")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, 0, 0, screenHeight / 20);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("TOP-LEFT")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.setMargins(0, 0, 10, screenHeight / 20);

            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("TOP-CENTER")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, screenHeight / 20, 0, 0);

            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("TOP-RIGHT")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutParams.setMargins(0, screenHeight / 20, 10, 0);

            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("MIDDLE-RIGHT")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("MIDDLE-LEFT")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else if (position.equalsIgnoreCase("MIDDLE-CENTER")) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        } else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutParams.setMargins(0, 0, 10, screenHeight / 20);
            cardView.setLayoutParams(layoutParams);
            lContainerLayout.addView(cardView);
        }


        // Floating banner content
        if (entryPointsDataList.get(i).getMobileData().getContent() != null && entryPointsDataList.get(i).getMobileData().getContent().size() > 0) {
            // Toast.makeText(context, "sads", Toast.LENGTH_SHORT).show();
            if (CustomerGlu.isDarkModeEnabled(CustomerGlu.globalContext)) {
                if (data != null && data.getDarkUrl() != null && !data.getDarkUrl().isEmpty()) {
                    floatingButtonUrl = data.getDarkUrl();
                }
            } else {
                if (data != null && data.getLightUrl() != null && !data.getLightUrl().isEmpty()) {
                    floatingButtonUrl = data.getLightUrl();
                }
            }
            if (floatingButtonUrl.isEmpty()) {
                if (data != null && data.getUrl() != null && !data.getUrl().isEmpty()) {
                    floatingButtonUrl = data.getUrl();
                }
            }
            image = floatingButtonUrl;

            //  Toast.makeText(context, ""+image, Toast.LENGTH_SHORT).show();
            if (image.contains(".gif") || image.contains(".Gif")) {
                Glide.with(context)
                        .asGif()
                        .load(image)
                        .into(myImageView);
            } else {
                Glide.with(context).asBitmap().load(image).into(myImageView);
            }
            //  main.setVisibility(VISIBLE);

        }

        if (isDraggable) {
            cardView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {

                        downRawX = motionEvent.getRawX();
                        downRawY = motionEvent.getRawY();
                        dX = view.getX() - downRawX;
                        dY = view.getY() - downRawY;

                        return true; // Consumed

                    } else if (action == MotionEvent.ACTION_MOVE) {
                        curntPositionX = (int) ((int) motionEvent.getRawX() + dX);
                        curntPositionY = (int) ((int) motionEvent.getRawY() + dY);
                        closeButtonLayout.setVisibility(VISIBLE);
                        closeLayout.setVisibility(VISIBLE);
                        int viewWidth = view.getWidth();
                        int viewHeight = view.getHeight();

                        View viewParent = (View) view.getParent();
                        int parentWidth = viewParent.getWidth();
                        int parentHeight = viewParent.getHeight();

                        float newX = motionEvent.getRawX() + dX;
                        newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
                        newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

                        float newY = motionEvent.getRawY() + dY;
                        newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
                        newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

                        view.animate()
                                .x(newX)
                                .y(newY)
                                .setDuration(0)
                                .start();

                        int close_button_width = closeButton.getWidth();
                        if (isViewOverlapping(cardView, closeButton)) {
                            closeButton.setBackgroundResource(R.drawable.ic_delete_bin_red);
                        } else {
                            closeButton.setBackgroundResource(R.drawable.ic_delete_bin);
                        }
//                        if (Math.abs(newY - closeButton.getY()) < 200 && Math.abs(newX - (closeButton.getX() - 100)) < 100) {
//                                                       closeButton.setBackgroundResource(R.drawable.ic_delete_bin_red);
//                        } else {
//                            closeButton.setBackgroundResource(R.drawable.ic_delete_bin);
//
//                        }
                        curntPositionX = (int) newX;
                        curntPositionY = (int) newY;

                        return true; // Consumed

                    } else if (action == MotionEvent.ACTION_UP) {

                        int diffX = (int) Math.abs(curntPositionX - closeButton.getX() - 100);
                        int diffY = (int) Math.abs(curntPositionY - closeButton.getY());

                        if (isViewOverlapping(cardView, closeButton)) {
                            int count = campaignCountObj.get(entryPointsDataList.get(i).get_id());
                            printErrorLogs("dismissId " + entryPointsDataList.get(i).get_id());
                            printErrorLogs("dismiss Count " + count);
                            int newCount = count + 1;
                            campaignCountObj.remove(entryPointsDataList.get(i).get_id());
                            campaignCountObj.put(entryPointsDataList.get(i).get_id(), newCount);
                            printErrorLogs("CustomerGluId " + entryPointsDataList.get(i).get_id());
                            printErrorLogs("CustomerGluCount " + campaignCountObj.get(entryPointsDataList.get(i).get_id()));
                            Prefs.putEncDismissedEntryPoints(context, campaignCountObj);


                            CustomerGlu.dismissedEntryPoints.put(entryPointsDataList.get(i).get_id(), true);
                            campaignCountObj = Prefs.getEncDismissedEntryPoints(context);
                            if (campaignCountObj != null) {
                                printErrorLogs("campaignOBJSize " + campaignCountObj.size());
                            }

                            cardView.setVisibility(GONE);
                            Map<String, Object> dismissObject = new HashMap<>();
                            dismissObject.put("entry_point_id", entryPointsDataList.get(i).get_id());
                            if (entryPointsDataList.get(i).getName() != null) {
                                dismissObject.put("entry_point_name", entryPointsDataList.get(i).getName());
                            } else {
                                dismissObject.put("entry_point_name", "");
                            }
                            dismissObject.put("entry_point_location", screenName);
                            CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_DISMISS, dismissObject);
                            //  removeMyView();

                        }

                        closeButtonLayout.setVisibility(GONE);
                        closeLayout.setVisibility(GONE);

                        float upRawX = motionEvent.getRawX();
                        float upRawY = motionEvent.getRawY();

                        float upDX = upRawX - downRawX;
                        float upDY = upRawY - downRawY;

                        if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                            // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                            boolean isUrlValiadte = false;

                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                isUrlValiadte = Comman.isValidURL(entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                            }
                            if (isUrlValiadte) {
                                Map<String, Object> nudgeData = new HashMap<>();
                                Map<String, Object> entry_point_content = new HashMap<>();
                                Map<String, Object> entry_point_action = new HashMap<>();
                                Map<String, Object> open_content = new HashMap<>();
                                entry_point_content.put("type", "STATIC");
                                entry_point_content.put("campaign_id", "");
                                open_content.put("type", "STATIC");
                                open_content.put("campaign_id", "");
                                open_content.put("static_url", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                entry_point_action.put("action_type", "open");
                                entry_point_action.put("open_container", entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                                entry_point_action.put("open_content", open_content);
                                entry_point_content.put("static_url", image);
                                nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                if (entryPointsDataList.get(i).getName() != null) {
                                    nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                } else {
                                    nudgeData.put("entry_point_name", "");
                                }
                                nudgeData.put("entry_point_location", screenName);
                                nudgeData.put("entry_point_content", entry_point_content);
                                nudgeData.put("entry_point_action", entry_point_action);
                                nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_CLICK, nudgeData);
                                //     CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", "CUSTOM_URL", entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                            } else {
                                Map<String, Object> nudgeData = new HashMap<>();
                                Map<String, Object> entry_point_content = new HashMap<>();
                                Map<String, Object> entry_point_action = new HashMap<>();
                                Map<String, Object> open_content = new HashMap<>();
                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                    open_content.put("type", "WALLET");
                                } else {
                                    open_content.put("type", "CAMPAIGN");
                                }
                                open_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                open_content.put("static_url", "");
                                entry_point_action.put("action_type", "open");
                                entry_point_action.put("open_container", entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                                entry_point_action.put("open_content", open_content);
                                if (image != null && !image.isEmpty()) {
                                    entry_point_content.put("type", "STATIC");
                                    entry_point_content.put("campaign_id", "");
                                    entry_point_content.put("static_url", image);
                                } else {
                                    if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId().isEmpty()) {
                                        entry_point_content.put("type", "WALLET");
                                    } else {
                                        entry_point_content.put("type", "CAMPAIGN");
                                    }
                                    entry_point_content.put("campaign_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId());
                                    entry_point_content.put("static_url", "");
                                }
                                if (entryPointsDataList.get(i).getName() != null) {
                                    nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
                                } else {
                                    nudgeData.put("entry_point_name", "");
                                }
                                nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
                                nudgeData.put("entry_point_location", screenName);
                                nudgeData.put("entry_point_content", entry_point_content);
                                nudgeData.put("entry_point_action", entry_point_action);
                                nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
                                CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_CLICK, nudgeData);
                                //    CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "OPEN", entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
                            }
                            printDebugLogs(open_layout);

                            double relativeHeight = 0;
                            double absoluteHeight = 0;
                            boolean isClose = false;
                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).isCloseOnDeepLink() != null) {
                                isClose = entryPointsDataList.get(i).getMobileData().getContent().get(0).isCloseOnDeepLink();
                            }
                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight() != null) {
                                relativeHeight = entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight();
                            }
                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAbsoluteHeight() != null) {
                                absoluteHeight = entryPointsDataList.get(i).getMobileData().getContent().get(0).getRelativeHeight();
                            }
                            if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction() != null) {

                                switch (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getType()) {
                                    case OPEN_DEEPLINK:
                                        if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                                            try {
                                                JSONObject dataObject = new JSONObject();
                                                dataObject.put("deepLink", entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());

                                                Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                                                intent.putExtra("data", dataObject.toString());
                                                context.sendBroadcast(intent);
                                                if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK() != null && entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().isHandledBySDK()) {
                                                    Uri uri = Uri.parse(entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl());
                                                    Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                                                    actionIntent.setData(uri);
                                                    context.startActivity(intent);
                                                }
                                            } catch (Exception e) {
                                                printErrorLogs("" + e);
                                            }
                                        }
                                        break;
                                    case OPEN_WEBLINK:
                                        NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                                        nudgeConfiguration.setRelativeHeight(relativeHeight);
                                        nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
                                        nudgeConfiguration.setLayout(data.getOpenLayout());
                                        nudgeConfiguration.setCloseOnDeepLink(isClose);
                                        nudgeConfiguration.setOpacity(Double.parseDouble(opacity));
                                        nudgeConfiguration.setHyperlink(true);
                                        if (entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl() != null && !entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl().isEmpty()) {
                                            CustomerGlu.getInstance().displayCGNudge(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getAction().getUrl(), nudgeConfiguration);
                                        }
                                        break;

                                    case OPEN_WALLET:
                                        CustomerGlu.getInstance().loadPopUpBanner(context, CG_OPEN_WALLET, entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                                        break;

                                    default:
                                        CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                                }
                            } else {
                                CustomerGlu.getInstance().loadPopUpBanner(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).getCampaignId(), entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout(), entryPointsDataList.get(i).getMobileData().getConditions().getBackgroundOpacity(), absoluteHeight, relativeHeight, isClose);
                            }

                        } else { // A drag
                            return true; // Consumed
                        }

                    } else {
                        //   printErrorLogs("CustomerGlu", "");
                    }

                    return false;
                }
            });
        }

// Adding full screen container
        MobileData mobileData = entryPointsDataList.get(i).getMobileData();
        boolean isUrlValiadte = false;

        if (image != null && !image.isEmpty()) {
            isUrlValiadte = Comman.isValidURL(image);
        }
        if (isUrlValiadte) {
            Map<String, Object> nudgeData = new HashMap<>();
            Map<String, Object> entry_point_content = new HashMap<>();
            entry_point_content.put("type", "STATIC");
            entry_point_content.put("campaign_id", "");
            entry_point_content.put("static_url", image);
            nudgeData.put("entry_point_id", entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id());
            nudgeData.put("entry_point_location", screenName);
            if (entryPointsDataList.get(i).getName() != null) {
                nudgeData.put("entry_point_name", entryPointsDataList.get(i).getName());
            } else {
                nudgeData.put("entry_point_name", "");
            }
            nudgeData.put("entry_point_content", entry_point_content);
            nudgeData.put("entry_point_container", entryPointsDataList.get(i).getMobileData().getContainer().getType());
            CustomerGlu.getInstance().cgAnalyticsEventManager(context, ENTRY_POINT_LOAD, nudgeData);
            //   CustomerGlu.getInstance().sendAnalyticsEvent(context, entryPointsDataList.get(i).getMobileData().getContent().get(0).get_id(), entryPointsDataList.get(i).getMobileData().getContainer().getType(), screenName, "LOADED", "CUSTOM_URL", entryPointsDataList.get(i).getMobileData().getContent().get(0).getOpenLayout());
        }
        return lContainerLayout;
    }

    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        // Rect constructor parameters: left, top, right, bottom
        Rect rectFirstView = new Rect(firstPosition[0], firstPosition[1],
                firstPosition[0] + firstView.getMeasuredWidth(), firstPosition[1] + firstView.getMeasuredHeight());
        Rect rectSecondView = new Rect(secondPosition[0], secondPosition[1],
                secondPosition[0] + secondView.getMeasuredWidth(), secondPosition[1] + secondView.getMeasuredHeight());
        return rectFirstView.intersect(rectSecondView);
    }

    @SuppressLint("ResourceAsColor")
    private void addCloseLayout() {
        closeLayout = new RelativeLayout(context);
        closeLayout.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
        closeLayout.setBackgroundColor(Color.TRANSPARENT);
        close_lyt = new RelativeLayout(context);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0x00f1f1f2, 0xFB000000});
        close_lyt.setBackground(gd);
        RelativeLayout.LayoutParams closeLayoutParams = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, screenHeight / 5));
        closeLayoutParams.addRule(ALIGN_PARENT_BOTTOM);
        close_lyt.setLayoutParams(closeLayoutParams);

        closeLayout.addView(close_lyt);
        ((Activity) context).addContentView(closeLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        closeLayout.setVisibility(GONE);
    }

    @SuppressLint("ResourceAsColor")
    private void addCloseButton() {
        closeButtonLayout = new RelativeLayout(context);
        closeButtonLayout.setLayoutParams(new RelativeLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));


        TextView textView = new TextView(context);
        textView.setText("Drag here to dismiss");
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(12.0f);
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        textLayoutParams.addRule(ALIGN_PARENT_BOTTOM);
        textLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLayoutParams.setMargins(0, 0, 0, (screenHeight / 10) - 50);
        textView.setLayoutParams(textLayoutParams);
        closeButton = new ImageView(context);
        closeButton.setBackgroundResource(R.drawable.ic_delete_bin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(130, 130);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, 0, 0, screenHeight / 10);
        closeButton.setLayoutParams(layoutParams);
        closeButtonLayout.addView(closeButton);
        closeButtonLayout.addView(textView);
        ((Activity) context).addContentView(closeButtonLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        closeButtonLayout.setVisibility(GONE);

    }

}

