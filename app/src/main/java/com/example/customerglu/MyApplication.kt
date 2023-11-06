package com.example.customerglu

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.os.Handler
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Utils.Constants
import com.example.customerglu.Utils.CustomerGluManager
import com.example.customerglu.demoBanner.Banner
import com.example.customerglu.demoBanner.BannerGravity
import com.example.customerglu.demoBanner.DebugBanner
import org.json.JSONObject

class MyApplication: Application()
{
    lateinit var mMessageReceiver:BroadcastReceiver
    override fun onCreate() {
        super.onCreate()
        DebugBanner.init(application = this,
            banner = Banner(bannerText = "DEMO APP", bannerGravity = BannerGravity.END)
        )
        CustomerGluManager.initializeSDK(applicationContext, debugMode = true)


    }





}