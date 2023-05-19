package com.customerglu.sdk.Adapters;

import static android.view.View.GONE;
import static com.customerglu.sdk.Utils.CGConstants.CG_OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_CLICK;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINT_LOAD;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_DEEPLINK;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WALLET;
import static com.customerglu.sdk.Utils.CGConstants.OPEN_WEBLINK;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.EntryPointsData;
import com.customerglu.sdk.Modal.MobileData;
import com.customerglu.sdk.Modal.NudgeConfiguration;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.cgRxBus.event.CGBannerDismissShimmerEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageBannerAdapter extends RecyclerView.Adapter<ImageBannerAdapter.UnderShop> {
    private final Context mContext;
    public List<MobileData.Content> contentList = new ArrayList<>();
    View v;
    private int lastPosition = -1;

    String url = "", opacity, open_layout = "", screenName = "";
    private final EntryPointsData entryPointsModel;
    private double relativeHeight = 0;
    private double absoluteHeight = 0;
    boolean isClosed = true;

    public ImageBannerAdapter(Context mContext, List<MobileData.Content> contentList, EntryPointsData entryPointsData, String opacity) {
        this.mContext = mContext;
        this.contentList = contentList;
        this.opacity = opacity;
        this.entryPointsModel = entryPointsData;
    }

    @NonNull
    @Override
    public ImageBannerAdapter.UnderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_adapter, parent, false);
        UnderShop vh = new UnderShop(v);
        if (!CustomerGlu.lastScreenName.equalsIgnoreCase("")) {
            screenName = CustomerGlu.lastScreenName;
        } else {
            screenName = mContext.getClass().getCanonicalName();
        }
        return vh;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ImageBannerAdapter.UnderShop holder, @SuppressLint("RecyclerView") int position) {
        try {
            String bannerImageUrl = "";
            final MobileData.Content data = contentList.get(position);
            // Dark Mode Light Mode Checking
            if (CustomerGlu.isDarkModeEnabled(CustomerGlu.globalContext)) {
                if (data != null && data.getDarkUrl() != null && !data.getDarkUrl().isEmpty()) {
                    bannerImageUrl = data.getDarkUrl();
                }
            } else {
                if (data != null && data.getLightUrl() != null && !data.getLightUrl().isEmpty()) {
                    bannerImageUrl = data.getLightUrl();
                }
            }
            if (bannerImageUrl.isEmpty()) {
                if (data != null && data.getUrl() != null && !data.getUrl().isEmpty()) {
                    bannerImageUrl = data.getUrl();
                }
            }

            if (!bannerImageUrl.isEmpty()) {
                if (data.getType().equalsIgnoreCase("IMAGE")) {
                    holder.webView.setVisibility(GONE);
                    holder.mImage.setVisibility(View.VISIBLE);
                    if (bannerImageUrl.contains(".gif") || bannerImageUrl.contains(".Gif")) {

                        Glide.with(mContext)
                                .asGif()
                                .load(bannerImageUrl)
                                .into(new CustomTarget<GifDrawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                                        holder.mImage.setImageDrawable(resource);
                                        CustomerGlu.cgrxBus.postEvent(new CGBannerDismissShimmerEvent(true));
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    } else {


                        Glide.with(mContext).asBitmap().load(bannerImageUrl).into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                CustomerGlu.cgrxBus.postEvent(new CGBannerDismissShimmerEvent(true));
                                holder.mImage.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
                    }
                    boolean isUrlValiadte = false;

                    if (contentList.get(position).getCampaignId() != null && !contentList.get(position).getCampaignId().isEmpty()) {
                        isUrlValiadte = Comman.isValidURL(contentList.get(position).getCampaignId());
                    }

                    Map<String, Object> nudgeData = new HashMap<>();
                    Map<String, Object> entry_point_content = new HashMap<>();
                    entry_point_content.put("type", "STATIC");
                    entry_point_content.put("campaign_id", "");
                    if (entryPointsModel.getName() != null) {
                        nudgeData.put("entry_point_name", entryPointsModel.getName());

                    } else {
                        nudgeData.put("entry_point_name", "");
                    }
                    entry_point_content.put("static_url", bannerImageUrl);
                    nudgeData.put("entry_point_id", contentList.get(position).get_id());
                    nudgeData.put("entry_point_location", screenName);
                    nudgeData.put("entry_point_content", entry_point_content);
                    nudgeData.put("entry_point_container", "BANNER");
                    CustomerGlu.getInstance().cgAnalyticsEventManager(mContext, ENTRY_POINT_LOAD, nudgeData);
                    //  CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "LOADED", "CUSTOM_URL", contentList.get(position).getOpenLayout());

                } else {
                    holder.mImage.setVisibility(GONE);
                    holder.my_button.setVisibility(View.VISIBLE);
                    holder.webView.setVisibility(View.VISIBLE);

                    holder.webView.getSettings().setJavaScriptEnabled(true);
                    holder.webView.setInitialScale(1);
                    holder.webView.getSettings().setLoadWithOverviewMode(true);
                    holder.webView.getSettings().setUseWideViewPort(true);
                    holder.webView.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return (event.getAction() == MotionEvent.ACTION_MOVE);
                        }
                    });
                    holder.webView.loadUrl(bannerImageUrl);
                    Map<String, Object> nudgeData = new HashMap<>();
                    Map<String, Object> entry_point_content = new HashMap<>();
                    entry_point_content.put("type", "STATIC");
                    entry_point_content.put("campaign_id", "");
                    if (entryPointsModel.getName() != null) {
                        nudgeData.put("entry_point_name", entryPointsModel.getName());

                    } else {
                        nudgeData.put("entry_point_name", "");
                    }
                    entry_point_content.put("static_url", bannerImageUrl);
                    nudgeData.put("entry_point_id", contentList.get(position).get_id());
                    nudgeData.put("entry_point_location", screenName);
                    nudgeData.put("entry_point_content", entry_point_content);
                    nudgeData.put("entry_point_container", "BANNER");
                    CustomerGlu.getInstance().cgAnalyticsEventManager(mContext, ENTRY_POINT_LOAD, nudgeData);
                    //  CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "LOADED", contentList.get(position).getCampaignId(), contentList.get(position).getOpenLayout());


                }
            }
            holder.cardView.getBackground().setAlpha(0);

            holder.mImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (data != null && data.getCampaignId() != null) {
                        Prefs.putEncKey(mContext, "OPEN_LAYOUT", data.getOpenLayout());
                        if (data.getOpenLayout() != null) {
                            open_layout = data.getOpenLayout();
                        }
                        boolean isUrlValiadte = false;

                        if (contentList.get(position).getCampaignId() != null && !contentList.get(position).getCampaignId().isEmpty()) {
                            isUrlValiadte = Comman.isValidURL(contentList.get(position).getCampaignId());
                        }
                        if (contentList.get(position).getAbsoluteHeight() != null) {
                            absoluteHeight = contentList.get(position).getAbsoluteHeight();
                        } else {
                            absoluteHeight = 0;
                        }
                        if (contentList.get(position).getRelativeHeight() != null) {
                            relativeHeight = contentList.get(position).getRelativeHeight();
                        } else {
                            relativeHeight = 0;
                        }
                        if (isUrlValiadte) {
                            Map<String, Object> nudgeData = new HashMap<>();
                            Map<String, Object> entry_point_content = new HashMap<>();
                            Map<String, Object> entry_point_action = new HashMap<>();
                            Map<String, Object> open_content = new HashMap<>();
                            open_content.put("type", "STATIC");
                            open_content.put("campaign_id", "");
                            if (entryPointsModel.getName() != null) {
                                nudgeData.put("entry_point_name", entryPointsModel.getName());

                            } else {
                                nudgeData.put("entry_point_name", "");
                            }
                            open_content.put("static_url", contentList.get(position).getCampaignId());
                            entry_point_action.put("action_type", "open");
                            entry_point_action.put("open_container", contentList.get(position).getOpenLayout());
                            entry_point_action.put("open_content", open_content);
                            if (contentList.get(position).getType().equalsIgnoreCase("IMAGE")) {
                                entry_point_content.put("type", "STATIC");
                                entry_point_content.put("campaign_id", "");
                                entry_point_content.put("static_url", contentList.get(position).getUrl());
                            } else {
                                if (contentList.get(position).getCampaignId().isEmpty()) {
                                    entry_point_content.put("type", "WALLET");
                                } else {
                                    entry_point_content.put("type", "CAMPAIGN");
                                }
                                entry_point_content.put("campaign_id", contentList.get(position).getCampaignId());
                                entry_point_content.put("static_url", "");
                            }
//                            entry_point_content.put("campaign_id", "");
//                            entry_point_content.put("static_url", contentList.get(position).getUrl());
                            nudgeData.put("entry_point_id", contentList.get(position).get_id());
                            nudgeData.put("entry_point_location", screenName);
                            nudgeData.put("entry_point_content", entry_point_content);
                            nudgeData.put("entry_point_action", entry_point_action);
                            nudgeData.put("entry_point_container", "BANNER");
                            CustomerGlu.getInstance().cgAnalyticsEventManager(mContext, ENTRY_POINT_CLICK, nudgeData);

                            //   CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "OPEN", "CUSTOM_URL", contentList.get(position).getOpenLayout());

                        } else {
                            Map<String, Object> nudgeData = new HashMap<>();
                            Map<String, Object> entry_point_content = new HashMap<>();
                            Map<String, Object> entry_point_action = new HashMap<>();
                            Map<String, Object> open_content = new HashMap<>();
                            if (contentList.get(position).getCampaignId().isEmpty()) {
                                open_content.put("type", "WALLET");
                            } else {
                                open_content.put("type", "CAMPAIGN");
                            }
                            if (entryPointsModel.getName() != null) {
                                nudgeData.put("entry_point_name", entryPointsModel.getName());

                            } else {
                                nudgeData.put("entry_point_name", "");
                            }
                            open_content.put("campaign_id", contentList.get(position).getCampaignId());
                            open_content.put("static_url", "");
                            entry_point_action.put("action_type", "open");
                            entry_point_action.put("open_container", contentList.get(position).getOpenLayout());
                            entry_point_action.put("open_content", open_content);
                            if (contentList.get(position).getUrl() != null && !contentList.get(position).getUrl().isEmpty()) {
                                entry_point_content.put("type", "STATIC");
                                entry_point_content.put("campaign_id", "");
                                entry_point_content.put("static_url", contentList.get(position).getUrl());
                            } else {
                                if (contentList.get(position).getCampaignId().isEmpty()) {
                                    entry_point_content.put("type", "WALLET");
                                } else {
                                    entry_point_content.put("type", "CAMPAIGN");
                                }
                                entry_point_content.put("campaign_id", contentList.get(position).getCampaignId());
                                entry_point_content.put("static_url", "");
                            }
//                            entry_point_content.put("campaign_id", contentList.get(position).getCampaignId());
//                            entry_point_content.put("static_url", "");
                            nudgeData.put("entry_point_id", contentList.get(position).get_id());
                            nudgeData.put("entry_point_location", screenName);
                            nudgeData.put("entry_point_content", entry_point_content);
                            nudgeData.put("entry_point_action", entry_point_action);
                            nudgeData.put("entry_point_container", "BANNER");
                            CustomerGlu.getInstance().cgAnalyticsEventManager(mContext, ENTRY_POINT_CLICK, nudgeData);
                            //  CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "OPEN", contentList.get(position).getCampaignId(), contentList.get(position).getOpenLayout());
                        }
                        if (contentList.get(position).isCloseOnDeepLink() != null) {
                            isClosed = contentList.get(position).isCloseOnDeepLink();
                        } else {
                            isClosed = false;
                        }

                        if (contentList.get(position).getAction() != null) {
                            NudgeConfiguration nudgeConfiguration = new NudgeConfiguration();
                            nudgeConfiguration.setRelativeHeight(relativeHeight);
                            nudgeConfiguration.setAbsoluteHeight(absoluteHeight);
                            nudgeConfiguration.setLayout(data.getOpenLayout());
                            nudgeConfiguration.setCloseOnDeepLink(isClosed);
                            nudgeConfiguration.setOpacity(Double.parseDouble(opacity));
                            nudgeConfiguration.setHyperlink(true);
                            switch (contentList.get(position).getAction().getType()) {

                                case OPEN_DEEPLINK:
                                    if (contentList.get(position).getAction().getUrl() != null && !contentList.get(position).getAction().getUrl().isEmpty()) {
                                        try {
                                            JSONObject dataObject = new JSONObject();
                                            dataObject.put("deepLink", contentList.get(position).getAction().getUrl());

                                            Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                                            intent.putExtra("data", dataObject.toString());
                                            mContext.sendBroadcast(intent);

                                            if (contentList.get(position).getAction().isHandledBySDK() != null && contentList.get(position).getAction().isHandledBySDK()) {
                                                Uri uri = Uri.parse(contentList.get(position).getAction().getUrl());
                                                Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                                                actionIntent.setData(uri);
                                                mContext.startActivity(intent);
                                            }
                                        } catch (Exception e) {
                                            Log.e("CUSTOMERGLU", "" + e);
                                        }
                                    }
                                    break;
                                case OPEN_WEBLINK:
                                    if (contentList.get(position).getAction().getUrl() != null && !contentList.get(position).getAction().getUrl().isEmpty()) {
                                        CustomerGlu.getInstance().displayCGNudge(mContext, contentList.get(position).getAction().getUrl(), nudgeConfiguration);
                                    }
                                    break;
                                case OPEN_WALLET:
                                    CustomerGlu.getInstance().loadPopUpBanner(mContext, CG_OPEN_WALLET, data.getOpenLayout(), opacity, absoluteHeight, relativeHeight, isClosed);
                                    break;
                                default:
                                    CustomerGlu.getInstance().loadPopUpBanner(mContext, data.getCampaignId(), data.getOpenLayout(), opacity, absoluteHeight, relativeHeight, isClosed);
                            }
                        } else {
                            CustomerGlu.getInstance().loadPopUpBanner(mContext, data.getCampaignId(), data.getOpenLayout(), opacity, absoluteHeight, relativeHeight, isClosed);
                        }

                    }
                }
            });
            holder.my_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (data != null && data.getCampaignId() != null) {
                        Prefs.putEncKey(mContext, "OPEN_LAYOUT", data.getOpenLayout());
                        if (data.getOpenLayout() != null) {
                            open_layout = data.getOpenLayout();
                        }
                        boolean isUrlValiadte = false;

                        if (contentList.get(position).getCampaignId() != null && !contentList.get(position).getCampaignId().isEmpty()) {
                            isUrlValiadte = Comman.isValidURL(contentList.get(position).getCampaignId());
                        }
                        if (contentList.get(position).getAbsoluteHeight() != null) {
                            absoluteHeight = contentList.get(position).getAbsoluteHeight();
                        } else {
                            absoluteHeight = 0;
                        }
                        if (contentList.get(position).getRelativeHeight() != null) {
                            relativeHeight = contentList.get(position).getRelativeHeight();
                        } else {
                            relativeHeight = 0;
                        }
                        if (isUrlValiadte) {
                            //      CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "OPEN", "CUSTOM_URL", contentList.get(position).getOpenLayout());
                        } else {
                            //    CustomerGlu.getInstance().sendAnalyticsEvent(mContext, contentList.get(position).get_id(), "BANNER", screenName, "OPEN", contentList.get(position).getCampaignId(), contentList.get(position).getOpenLayout());
                        }
                        if (contentList.get(position).isCloseOnDeepLink() != null) {
                            isClosed = contentList.get(position).isCloseOnDeepLink();
                        } else {
                            isClosed = false;
                        }
                        CustomerGlu.getInstance().loadPopUpBanner(mContext, data.getCampaignId(), data.getOpenLayout(), opacity, absoluteHeight, relativeHeight, isClosed);
                    }
                }

            });

        } catch (Exception e) {
            printErrorLogs("ImageBanner Adapter " + e);


        }

    }

    private void openWeblink(Context mContext, NudgeConfiguration nudgeConfiguration) {


    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class UnderShop extends RecyclerView.ViewHolder {

        ImageView mImage;
        WebView webView;
        RelativeLayout cardView;
        Button my_button;


        public UnderShop(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.Rcard);
            webView = itemView.findViewById(R.id.web);
            my_button = itemView.findViewById(R.id.my_button);
            //  Log.e("CustomerGlu", "height " + ViewGroup.LayoutParams.MATCH_PARENT);
            cardView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mImage = itemView.findViewById(R.id.img);

        }
    }

}

