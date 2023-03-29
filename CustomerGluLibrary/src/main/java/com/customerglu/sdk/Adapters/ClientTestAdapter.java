package com.customerglu.sdk.Adapters;

import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.clienttesting.ClientTestingModel;
import com.customerglu.sdk.clienttesting.ClientTestingPage;

import java.util.ArrayList;
import java.util.List;

public class ClientTestAdapter extends RecyclerView.Adapter<ClientTestAdapter.UnderShop> {
    View v;
    private final Context context;
    String url = "";
    ClientTestingPage activity;
    public List<ClientTestingModel> clientTestList = new ArrayList<>();

    public ClientTestAdapter(Context context, ClientTestingPage activity, List<ClientTestingModel> clientTestList) {
        this.context = context;
        this.activity = activity;
        this.clientTestList = clientTestList;
    }

    @NonNull
    @Override
    public ClientTestAdapter.UnderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_client_test_adapter, parent, false);
        UnderShop vh = new UnderShop(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientTestAdapter.UnderShop holder, int position) {
        try {
            ClientTestingModel data = null;
            if (clientTestList != null) {
                data = clientTestList.get(position);

            }
//            RequestOptions request = new RequestOptions();
//            request.error(R.drawable.bannerback);
            if (data != null) {

                if (data.getTestName().equalsIgnoreCase(CGConstants.ENTRY_POINTS_SCREEN_SET_UP) || data.getTestName().equalsIgnoreCase(CGConstants.ENTRY_POINTS_BANNER_ID_SET_UP) || data.getTestName().equalsIgnoreCase(CGConstants.ENTRY_POINTS_EMBED_ID_SET_UP)) {
                    RelativeLayout.LayoutParams newLayoutParams = (RelativeLayout.LayoutParams) holder.main_lyt.getLayoutParams();
                    newLayoutParams.leftMargin = 40;
                    holder.main_lyt.setLayoutParams(newLayoutParams);
                    holder.testNameTextView.setTextSize(14);
                    holder.testNameTextView.setTextColor(Color.parseColor("#000000"));

                }

                if (data.getTestName().equalsIgnoreCase(CGConstants.ONE_LINK_HANDLING) || data.getTestName().equalsIgnoreCase(CGConstants.ENTRY_POINTS_SET_UP)) {

                    RelativeLayout.LayoutParams newLayoutParams = (RelativeLayout.LayoutParams) holder.main_lyt.getLayoutParams();
                    newLayoutParams.leftMargin = 0;
                    holder.main_lyt.setLayoutParams(newLayoutParams);
                    holder.testNameTextView.setTextSize(16);
                    holder.testNameTextView.setTextColor(Color.parseColor("#000000"));
                }

                holder.testNameTextView.setText(data.getTestName());

                switch (data.getTestState()) {
                    case SUCCESS:
                        holder.pg.setVisibility(View.GONE);
                        holder.progress_image.setVisibility(View.VISIBLE);
                        holder.retry.setVisibility(View.GONE);
                        holder.check_docs.setVisibility(View.GONE);
                        holder.progress_image.setImageResource(R.drawable.checked);
                        break;
                    case FAILURE:
                        holder.pg.setVisibility(View.GONE);
                        holder.progress_image.setVisibility(View.VISIBLE);
                        holder.retry.setVisibility(View.VISIBLE);
                        holder.check_docs.setVisibility(View.VISIBLE);
                        holder.progress_image.setImageResource(R.drawable.failure);
                        break;
                }


                holder.retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.pg.setVisibility(View.VISIBLE);
                        holder.progress_image.setVisibility(View.GONE);
                        activity.runTestCases(true, clientTestList.get(holder.getAdapterPosition()).getTestName());
                    }
                });

                holder.check_docs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!clientTestList.get(holder.getAdapterPosition()).getUrl().isEmpty()) {
                            Uri uri = Uri.parse(clientTestList.get(holder.getAdapterPosition()).getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(uri);
                            activity.startActivity(intent);
                        }
                    }
                });

            }


        } catch (Exception e) {
            printErrorLogs("Reward Adapter " + e);

        }

    }


    @Override
    public int getItemCount() {
        return clientTestList.size();
    }

    public class UnderShop extends RecyclerView.ViewHolder {

        ProgressBar pg;
        TextView testNameTextView;
        ImageView progress_image;
        TextView retry;
        TextView check_docs;
        RelativeLayout main_lyt;

        public UnderShop(@NonNull View itemView) {
            super(itemView);
            pg = itemView.findViewById(R.id.pg);
            retry = itemView.findViewById(R.id.retry);
            check_docs = itemView.findViewById(R.id.check_docs);

            testNameTextView = itemView.findViewById(R.id.testName);
            progress_image = itemView.findViewById(R.id.progress_image);
            main_lyt = itemView.findViewById(R.id.main_lyt);


        }
    }

}
