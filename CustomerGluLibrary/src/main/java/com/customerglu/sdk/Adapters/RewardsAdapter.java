package com.customerglu.sdk.Adapters;

import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.Campaigns;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.Web.RewardWeb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.UnderShop> {
    View v;
    private final Context mContext;
    String url = "";
    public List<Campaigns> rewardlist = new ArrayList<>();

    public RewardsAdapter(Context mContext, List<Campaigns> rewardlist) {
        this.mContext = mContext;
        this.rewardlist = rewardlist;
    }

    @NonNull
    @Override
    public RewardsAdapter.UnderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards, parent, false);
        UnderShop vh = new UnderShop(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsAdapter.UnderShop holder, int position) {
        try {
            Campaigns data = null;
            if (rewardlist != null) {
                data = rewardlist.get(position);

            }
//            RequestOptions request = new RequestOptions();
//            request.error(R.drawable.bannerback);
            if (data != null) {
                if (data.getBanner() != null) {
                    if (data.getBanner().getImageUrl() != null) {
                        String url = data.getBanner().getImageUrl();
                        Picasso.with(mContext).load(url).into(holder.mImage);

                    } else {
                        String s = Prefs.getEncKey(mContext, "default_image");
                        Picasso.with(mContext).load(s).into(holder.mImage);
                    }

                } else {
                    String s = Prefs.getEncKey(mContext, "default_image");
                    Picasso.with(mContext).load(s).into(holder.mImage);
                }
            }


            Campaigns finalData = data;
            holder.mImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (CustomerGlu.isPrecachingEnable) {
                        CustomerGlu.getInstance().enablePrecaching(mContext);
                    }
                    Intent intent = new Intent(mContext, RewardWeb.class);
                    intent.putExtra("url", finalData.getUrl());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

            //         holder.save.setOnClickListener(new View.OnClickListener() {
            //           @RequiresApi(api = Build.VERSION_CODES.O)
            //         @Override
            //    public void onClick(View view) {
//                    eventProperties = new EventData.EventProperties();
//                    eventProperties.setState("1");
//                    String user_id = Prefs.getEncKey(mContext,"userID");
//                    CustomerGlu.sendEventData(mContext,"G4VCVVAcLub8hx5SaeqH3pRqLBmDFrwy",user_id,"shop",eventProperties);
//
//                    Intent intent = new Intent(mContext, MyCart.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);
            //        }
            //   });

        } catch (Exception e) {
            printErrorLogs("Reward Adapter " + e);

        }

    }

    @Override
    public int getItemCount() {
        return rewardlist.size();
    }

    public class UnderShop extends RecyclerView.ViewHolder {

        ImageView mImage;
        CardView mCardView;
        Button save;

        public UnderShop(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img);

        }
    }

}
