package com.example.customerglu;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.DataListner;
import com.customerglu.sdk.Modal.RegisterModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.branch.referral.Branch;

public class
MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
//        Branch.enableLogging();

        // Branch object initialization
//        Branch.getAutoInstance(this);


    }
}
