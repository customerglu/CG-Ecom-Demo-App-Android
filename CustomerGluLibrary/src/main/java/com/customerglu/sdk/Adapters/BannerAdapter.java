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

import com.bumptech.glide.Glide;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;
import com.customerglu.sdk.notification.MiddleDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.UnderShop> {
    View v;
    private final Context mContext;
    String url = "";
    public List<RewardModel.Campaigns> rewardlist = new ArrayList<>();

    public BannerAdapter(Context mContext, List<RewardModel.Campaigns> rewardlist) {
        this.mContext = mContext;
        this.rewardlist = rewardlist;
    }

    @NonNull
    @Override
    public BannerAdapter.UnderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_adapter, parent, false);
        UnderShop vh = new UnderShop(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.UnderShop holder, int position) {
        try {
            final RewardModel.Campaigns data = rewardlist.get(position);


            if (data != null) {
                if (data.getBanner() != null) {
                    if (data.getBanner().getImageUrl() != null) {
                        String url = data.getBanner().getImageUrl();
                        Picasso.with(mContext).load(url).into(holder.mImage);

                    } else {
                        String image = "https://i.gifer.com/origin/41/41297901c13bc7325dc7a17bba585ff9_w200.gif";

                        Glide.with(mContext).load(image).into(holder.mImage);

//                        String s = Prefs.getEncKey(mContext, "default_image");
//                        Picasso.with(mContext).load(s).into(holder.mImage);
                    }

                } else {
                    String image = "https://i.gifer.com/origin/41/41297901c13bc7325dc7a17bba585ff9_w200.gif";

                    Glide.with(mContext).load(image).into(holder.mImage);
                }
            }

            holder.mImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MiddleDialog.class);
                    intent.putExtra("nudge_url", "https://google.com");
                    intent.putExtra("opacity", "0.5");
                    mContext.startActivity(intent);
                }
            });


        } catch (Exception e) {
            printErrorLogs("" + e);

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
//            mCardView = itemView.findViewById(R.id.imageCard);
//            save = itemView.findViewById(R.id.save);
        }
    }

}
