package com.customerglu.sdk.Web;


import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customerglu.sdk.Adapters.RewardsAdapter;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.Campaigns;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterReward extends Activity {
    RecyclerView recyclerView;
    RewardsAdapter rewardsAdapter;
    ImageView back;
    HashMap<String, Object> params;
    TextView label;
    ProgressBar pg;
    public List<Campaigns> rewardlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);

                String s = "Uncaught Exception" + e;

                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);
            }
        });
        findViews();
    }


    private void getDataByFilter() {

        CustomerGlu.getInstance().retrieveDataByFilter(getApplicationContext(), params, new RewardInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(RewardModel rewardModel) {
                pg.setVisibility(View.GONE);
                if (rewardModel.campaigns.size() == 0) {
                    label.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    label.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    rewardlist.addAll(rewardModel.campaigns);
                    rewardsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String message) {
                printErrorLogs("Reward Filter " + message);

                finish();
            }
        });
    }

    private void findViews() {

        params = (HashMap<String, Object>) getIntent().getSerializableExtra("params");
        back = findViewById(R.id.back);
        label = findViewById(R.id.label);
        pg = findViewById(R.id.pg);
        String color;
        color = CustomerGlu.configure_loader_color;
        if (color.isEmpty()) {
            color = "#FF000000";
        }
        pg.getIndeterminateDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.rewards);
        rewardsAdapter = new RewardsAdapter(getApplicationContext(), rewardlist);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rewardsAdapter);


        getDataByFilter();


    }
}

