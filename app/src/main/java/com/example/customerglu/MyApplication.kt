package com.example.customerglu

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.os.Handler
import com.example.customerglu.Utils.Constants
import org.json.JSONObject

class MyApplication: Application()
{
    lateinit var mMessageReceiver:BroadcastReceiver
    override fun onCreate() {
        super.onCreate()

 /*       mMessageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Extract data included in the Intent
                try {

                    Handler().postDelayed({
                        if (intent.action.equals("CUSTOMERGLU_DEEPLINK_EVENT", ignoreCase = true)) {
                            val data = intent.getStringExtra("data")
                            val jsonObject = JSONObject(data)
                            if (jsonObject.has("deepLink")) {

                                var deeplink = jsonObject.get("deepLink")

                                if (deeplink.equals("customerglu://profile")) {
                                    navigateToActivity(Constants.Profile)

                                } else if (deeplink.equals("customerglu://cart")) {
                                    navigateToActivity(Constants.Cart)

                                } else if(deeplink.equals("customerglu://wishlist"))
                                {
                                    navigateToActivity(Constants.Wishlist)

                                }else if(deeplink.equals("customerglu://categories"))
                                {
                                    navigateToActivity(Constants.Categories)
                                }

                                else {
                                    navigateToActivity(Constants.Home)

                                }

                            }
                            // Add the logic to redirect to appropriate page
                        }
                    }, 500)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }

        registerReceiver(mMessageReceiver, IntentFilter("CUSTOMERGLU_DEEPLINK_EVENT"))

*/
    }


}