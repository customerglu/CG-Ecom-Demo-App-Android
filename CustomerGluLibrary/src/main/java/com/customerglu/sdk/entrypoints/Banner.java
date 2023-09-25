package com.customerglu.sdk.entrypoints;

import static com.customerglu.sdk.Utils.Comman.printDebugLogs;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customerglu.sdk.Adapters.ImageBannerAdapter;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.EntryPointsData;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.MobileData;
import com.customerglu.sdk.Modal.ScreenListModal;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CirclePagerIndicatorDecoration;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.cgRxBus.event.CGBannerDismissShimmerEvent;
import com.customerglu.sdk.custom.base.BaseRelativeLayout;
import com.customerglu.sdk.custom.views.ProgressLottieView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class Banner extends BaseRelativeLayout {

    public List<MobileData.Content> contentList = new ArrayList<>();
    int position = -1;
    RecyclerView recyclerView;
    ImageBannerAdapter bannerAdapter;
    EntryPointsModel entryPointsModel;
    EntryPointsData entryPointsData;
    RelativeLayout main;
    int height = 0, id;
    Context context;
    String elementId, opacity = "0.5";
    int scrollSpeed = 1;
    boolean autoScroll = true;
    int screenHeight, screenWidth;
    ProgressLottieView progressLottieView;
    BroadcastReceiver broadcastReceiver;
    boolean isLoaded = false;
    boolean isPresent = false;

    public Banner(Context context, String id) {
        super(context);
        this.context = context;
        System.out.println("------------");
        elementId = id;
        printDebugLogs(elementId);
        LayoutInflater.from(context).inflate(R.layout.image_banner, this, true);


        init();
    }


    public Banner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        id = this.getId();
        main = findViewById(id);

        //    main.setVisibility(GONE);
        elementId = getResources().getResourceEntryName(this.getId());
        printDebugLogs(elementId);
        LayoutInflater.from(context).inflate(R.layout.image_banner, this, true);


        init();
    }

    private void init() {
        if (!CustomerGlu.bannerIdList.contains(elementId)) {
            CustomerGlu.bannerIdList.add(elementId);
            ScreenListModal screenListModal = new ScreenListModal();
            screenListModal.setBannerIds(CustomerGlu.bannerIdList);
            CustomerGlu.getInstance().sendActivities(context, screenListModal);
        }
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        printDebugLogs("screenHeight" + screenHeight);
        printDebugLogs("screenWidth" + screenWidth);
        if (CustomerGlu.isBannerEntryPointsEnabled && CustomerGlu.isBannerEntryPointsHasData) {

            getEntryPointData();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        CustomerGlu.cgrxBus.getEvent().debounce(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof CGBannerDismissShimmerEvent) {
                    stopLottieProgressView();
                }
            }
        });
        if (broadcastReceiver == null) {
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
        }
        if (context != null) {
            context.registerReceiver(broadcastReceiver, new IntentFilter("CUSTOMERGLU_ENTRY_POINT_DATA"));
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (broadcastReceiver != null && context != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void getEntryPointData() {
        try {

            entryPointsModel = CustomerGlu.entryPointsModel;
            Gson gson = new Gson();
//            entryPointsModel = gson.fromJson(Constants.CGEntryPointData, EntryPointsModel.class);
            if (entryPointsModel == null || entryPointsModel.getEntryPointsData() == null) {
                Comman.printErrorLogs("EntryPoint Data Null");

            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (entryPointsModel.getSuccess()) {
                            isLoaded = true;
                            String bannerData = CustomerGlu.bannerCountData.toString();

                            if (entryPointsModel.getEntryPointsData().size() > 0) {
                                for (int i = 0; i < entryPointsModel.getEntryPointsData().size(); i++) {
                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData() != null) {
                                        if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                                            if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("BANNER")) {
                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getBannerId().equalsIgnoreCase(elementId)) {
                                                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations() != null) {
                                                        if (!entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackendValidations().equalsIgnoreCase("INVALID")) {
                                                            entryPointsData = entryPointsModel.getEntryPointsData().get(i);
                                                            if (entryPointsModel.getEntryPointsData().get(i).getVisible()) {
                                                                //Banner container
                                                                height = (screenHeight / 100) * Integer.parseInt(entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getHeight());

                                                                //  Banner  condition

                                                                autoScroll = entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getAutoScroll();
                                                                scrollSpeed = entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getAutoScrollSpeed();
                                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackgroundOpacity() != null) {
                                                                    opacity = entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions().getBackgroundOpacity();
                                                                }

                                                                // Banner content
                                                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {
                                                                    contentList.clear();
                                                                    contentList.addAll(entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent());
                                                                    findViews();
                                                                    bannerAdapter.notifyDataSetChanged();
                                                                    Intent intent = new Intent("CUSTOMERGLU_BANNER_LOADED");
                                                                    intent.putExtra("data", bannerData);
                                                                    context.sendBroadcast(intent);
                                                                    isPresent = true;
                                                                } else {
                                                                    Intent intent = new Intent("CUSTOMERGLU_BANNER_LOADED");
                                                                    intent.putExtra("data", bannerData);
                                                                    context.sendBroadcast(intent);
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

                        }

                        if (!isPresent) {
                            height = 0;
                            findViews();
                            bannerAdapter.notifyDataSetChanged();
                        }
                        isLoaded = false;
                        isPresent = false;

                    }

                });
            }


        } catch (Exception e) {
            CustomerGlu.getInstance().sendCrashAnalytics(context, "Registration Api Fails" + e);

        }

    }

    private void findViews() {

        //   main.setVisibility(VISIBLE);
        int duration = scrollSpeed * 1000;
        //  card = findViewById(R.id.card);
        recyclerView = findViewById(R.id.recycler);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = height;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        progressLottieView = findViewById(R.id.lottie_view);
        progressLottieView.setScaleType(ImageView.ScaleType.FIT_XY);
        progressLottieView.setLayoutParams(params);
        setupProgressView(progressLottieView);
        startLottieProgressView();
        bannerAdapter = new ImageBannerAdapter(context, contentList, entryPointsData, opacity);
//        recyclerView.setOnFlingListener(null);


        if (contentList.size() > 1) {
            recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
        }
        recyclerView.setLayoutParams(params);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bannerAdapter);

        if (autoScroll) {
            final Handler mHandler = new Handler(Looper.getMainLooper());
            final Runnable SCROLLING_RUNNABLE = new Runnable() {

                @Override
                public void run() {

                    position++;

                    if (position < contentList.size()) {
                        recyclerView.smoothScrollToPosition(position);

                    } else if (position == contentList.size()) {
                        position = 0;
                        recyclerView.smoothScrollToPosition(0);

                    }

                    mHandler.postDelayed(this, duration);
                }
            };
            mHandler.postDelayed(SCROLLING_RUNNABLE, 500);
        }


    }


}
