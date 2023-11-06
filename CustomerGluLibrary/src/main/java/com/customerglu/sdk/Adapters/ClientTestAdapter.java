package com.customerglu.sdk.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.cgRxBus.event.ClientTestPageTestCaseEvent;
import com.customerglu.sdk.clienttesting.ClientTestingModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

public class ClientTestAdapter extends RecyclerView.Adapter<ClientTestAdapter.UnderShop> {

    private final Context context;
    String url = "";
    public List<ClientTestingModel> clientTestList = new ArrayList<>();

    public ClientTestAdapter(Context context, List<ClientTestingModel> clientTestList) {
        this.context = context;
        this.clientTestList = clientTestList;
    }

    @NonNull
    @Override
    public ClientTestAdapter.UnderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case CGConstants.headingViewType:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_client_test_heading__adapter, parent, false);
                return new UnderShop(v);
            case CGConstants.subHeadingViewType:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_client_test_sub_heading_adapter, parent, false);
                return new UnderShop(v1);
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_client_test_adapter, parent, false);
                return new UnderShop(v2);
        }

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


                holder.testNameTextView.setText(data.getTestName());
                switch (data.getTestState()) {
                    case SUCCESS:
                        if (holder.pg != null) {
                            holder.pg.setVisibility(View.GONE);
                        }
                        if (holder.check_docs != null) {
                            holder.check_docs.setVisibility(View.GONE);
                        }
                        holder.progress_image.setVisibility(View.VISIBLE);
                        if (holder.retry != null) {
                            holder.retry.setVisibility(View.GONE);
                        }
                        holder.progress_image.setImageResource(R.drawable.checked);
                        break;
                    case FAILURE:
                        if (holder.pg != null) {
                            holder.pg.setVisibility(View.GONE);
                        }
                        if (holder.check_docs != null) {
                            holder.check_docs.setVisibility(View.VISIBLE);
                        }
                        holder.progress_image.setVisibility(View.VISIBLE);
                        if (holder.retry != null) {
                            holder.retry.setVisibility(View.VISIBLE);
                        }
                        holder.progress_image.setImageResource(R.drawable.failure);
                        break;
                }


            }


        } catch (Exception e) {
            printErrorLogs("Client Testing Adapter " + e);

        }

    }

    @Override
    public int getItemViewType(int position) {
        return clientTestList.get(position).getHeadingType();
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
        ConstraintLayout main_lyt;

        public UnderShop(@NonNull View itemView) {
            super(itemView);


            pg = itemView.findViewById(R.id.pg);
            retry = itemView.findViewById(R.id.retry);
            main_lyt = itemView.findViewById(R.id.main_lyt);
            check_docs = itemView.findViewById(R.id.check_docs);
            testNameTextView = itemView.findViewById(R.id.testName);
            progress_image = itemView.findViewById(R.id.progress_image);
            if (retry != null) {
                retry.setOnClickListener(view -> {
                    pg.setVisibility(View.VISIBLE);
                    progress_image.setVisibility(View.GONE);
                    CustomerGlu.cgrxBus.postEvent(new ClientTestPageTestCaseEvent(true, clientTestList.get(getAdapterPosition()).getTestName()));
                    //     activity.runTestCases(true, clientTestList.get(getAdapterPosition()).getTestName());
                });
            }
            if (check_docs != null) {
                check_docs.setOnClickListener(view -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(clientTestList.get(getAdapterPosition()).getUrl()));
                    browserIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                });
            }
        }

    }

}
