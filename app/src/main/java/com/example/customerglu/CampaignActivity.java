package com.example.customerglu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.DataListner;
import com.customerglu.sdk.Modal.RegisterModal;
import com.customerglu.sdk.Utils.Prefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class CampaignActivity extends AppCompatActivity {
    String writeKey=null;
    String campaignId="";
    String fromLogin="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
//        fromLogin = getIntent().getStringExtra("fromLogin");
//        Toast.makeText(this, "From Login "+fromLogin, Toast.LENGTH_SHORT).show();
        getDeepLink();

    }

    private void getDeepLink() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            if (deepLink != null && deepLink.getQueryParameter("writeKey") != null) {
                                writeKey = deepLink.getQueryParameter("writeKey");
                            }
                            if (deepLink != null && deepLink.getQueryParameter("campaignId") != null) {
                                campaignId = deepLink.getQueryParameter("campaignId");
                            }

                          if (writeKey!=null)
                          {


                               HashMap<String,Object> data = new HashMap<>();
                               CustomerGlu.setWriteKey(writeKey);

                               CustomerGlu.getInstance().registerDevice(getApplicationContext(), data,new DataListner() {
                                   @Override
                                   public void onSuccess(RegisterModal registerModal) {
                                       CustomerGlu.getInstance().loadCampaignById(getApplicationContext(),campaignId);
                                       finish();

                                   }

                                   @Override
                                   public void onFail(String message) {
                                       Toast.makeText(CampaignActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }


                            //  Toast.makeText(MainActivity.this, ""+refer_id, Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("k", "getDynamicLink:onFailure", e);
                    }
                });
    }
}
