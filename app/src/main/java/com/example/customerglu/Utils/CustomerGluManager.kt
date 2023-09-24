package com.example.customerglu.Utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.Interface.CGDeepLinkListener
import com.customerglu.sdk.Interface.DataListner
import com.customerglu.sdk.Modal.DeepLinkWormholeModel.DeepLinkData
import com.customerglu.sdk.Modal.NudgeConfiguration
import com.customerglu.sdk.Modal.RegisterModal
import com.customerglu.sdk.Utils.CGConstants.CGSTATE
import com.example.customerglu.HomeActivity
import com.example.customerglu.Utils.CommonMethods.Companion.navigateToActivity
import org.json.JSONException
import org.json.JSONObject


object CustomerGluManager {

    lateinit var mMessageReceiver: BroadcastReceiver

    fun initializeSDK(context: Context,debugMode: Boolean = false)
    {
        CustomerGlu.getInstance().initializeSdk(context)
        CustomerGlu.getInstance().gluSDKDebuggingMode(context,debugMode)
        setUpCTACallback(context)

    }

    private fun setUpCTACallback(context: Context) {

        mMessageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Extract data included in the Intent
                try {

                    Handler().postDelayed({
                        if (intent.action.equals("CUSTOMERGLU_DEEPLINK_EVENT", ignoreCase = true)) {
                            val data = intent.getStringExtra("data")
                            val jsonObject = JSONObject(data)
                            if (jsonObject.has("deepLink")) {

                                var deeplink = jsonObject.get("deepLink")
                              //  Toast.makeText(context, ""+deeplink, Toast.LENGTH_SHORT).show()
                                if (deeplink.equals("customerglu://profile")) {
                                    navigateToActivity(context,Constants.Profile)

                                } else if (deeplink.equals("customerglu://cart")) {
                                    navigateToActivity(context,Constants.Cart)

                                } else if(deeplink.equals("customerglu://wishlist"))
                                {
                                    navigateToActivity(context,Constants.Wishlist)

                                }else if(deeplink.equals("customerglu://categories"))
                                {
                                    navigateToActivity(context,Constants.Categories)
                                }

                                else {
                                    navigateToActivity(context,Constants.Home)

                                }
                            }
                        }

                        if (intent.action.equals(
                                "CUSTOMERGLU_ANALYTICS_EVENT",
                                ignoreCase = true
                            )
                        ) {
                            val data = intent.getStringExtra("data")
                            val jsonObject = JSONObject(data)
                               Log.e("Analytics ", "" + jsonObject);
                            //  Toast.makeText(context, "Analytics " + jsonObject, Toast.LENGTH_SHORT).show();
                        }
                        if (intent.action.equals("CUSTOMERGLU_BANNER_LOADED", ignoreCase = true)) {
                            val data = intent.getStringExtra("data")

                            //   Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                            // banner.setVisibility(GONE);
                        }

                        if (intent.action.equals("CG_INVALID_CAMPAIGN_ID", ignoreCase = true)) {
                            val data = intent.getStringExtra("data")
                            Log.e("WebAnalytics INVALID", data.toString());
                        }

                            // Add the logic to redirect to appropriate page

                    }, 500)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }

        context.registerReceiver(mMessageReceiver, IntentFilter("CUSTOMERGLU_DEEPLINK_EVENT"))
        context.registerReceiver(mMessageReceiver, IntentFilter("CUSTOMERGLU_ANALYTICS_EVENT"))
        context.registerReceiver(mMessageReceiver, IntentFilter("CUSTOMERGLU_BANNER_LOADED"))
        context.registerReceiver(mMessageReceiver, IntentFilter("CG_INVALID_CAMPAIGN_ID"))

    }

    fun enableEntryPoints(context: Context){
        CustomerGlu.getInstance().enableEntryPoints(context,true)
    }

    fun registerUser(context: Context,userData:HashMap<String,Any>)
    {
        CustomerGlu.getInstance().registerDevice(context,userData,object : DataListner {

            override fun onSuccess(registerModal: RegisterModal?) {
                val intent = Intent(context, HomeActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                // toast("Registered")
                if (registerModal != null) {
                    Prefs.putKey(
                        context,
                        "userId",
                        registerModal.data.getUser().userId
                    )
                }

               context.startActivity(intent)            }

            override fun onFail(message: String?) {
                 println(message)
            }
        })
    }



    fun logoutFromCG(context: Context)
    {
        CustomerGlu.getInstance().clearGluData(context)
    }

    fun enableAnalytics()
    {
        CustomerGlu.getInstance().enableAnalyticsEvent(true);
    }


    fun openWallet(context: Context,nudgeConfiguration: NudgeConfiguration = NudgeConfiguration())
    {
        CustomerGlu.getInstance().openWallet(context,nudgeConfiguration)
    }

    fun loadCampaignById(context: Context,campaignId:String,nudgeConfiguration: NudgeConfiguration = NudgeConfiguration())
    {
        CustomerGlu.getInstance().loadCampaignById(context,campaignId,nudgeConfiguration)
    }

    fun openNudge(context: Context,nudgeId:String,nudgeConfiguration: NudgeConfiguration = NudgeConfiguration())
    {
        CustomerGlu.getInstance().openNudge(context,nudgeId,nudgeConfiguration)
    }


    fun setClassNameForCG(activity: Activity,screenName:String)
    {
        CustomerGlu.getInstance().showEntryPoint(activity,screenName)
    }


    fun displayCGNotifications(context: Context,jsonObject:JSONObject,icon:Int)
    {
        CustomerGlu.getInstance()
            .displayCustomerGluNotification(context, jsonObject, icon)
    }

    fun displayCGBackgroundNotification(activity: Activity,delayInSecond:Long = 2)
    {
        val parent_intent = activity.intent
        var intentSource = "none"
        if (parent_intent != null) {
            if (parent_intent.extras != null) {
                intentSource = parent_intent.extras!!.getString("type", "none")
                val myType = parent_intent.extras!!.getString("from", "none")
                if (intentSource.equals("CustomerGlu", ignoreCase = true)) {
                    val data = parent_intent.extras
                    val json = JSONObject()
                    val keys = data!!.keySet()
                    for (key in keys) {
                        try {
                            json.put(key, JSONObject.wrap(data!![key]))
                        } catch (e: JSONException) {
                            //Handle exception here
                        }
                    }
                    Handler().postDelayed({
                        CustomerGlu.getInstance()
                            .displayCustomerGluBackgroundNotification(activity.applicationContext, json)
                    },(delayInSecond * 100))

                }
            }
        }
    }

    fun setDarkTheme(context: Context,value:Boolean)
    {
        CustomerGlu.getInstance().setDarkMode(context,value)
    }

    fun listenToSystemDarkMode(value: Boolean)
    {
        CustomerGlu.getInstance().listenToSystemDarkMode(value)
    }

    fun  setCGDeeplinkData(activity: Activity)
    {
        CustomerGlu.getInstance().setupCGDeepLinkIntentData(activity)
    }

    fun setCgDeeplinkCallbackListener()
    {
        CustomerGlu.getInstance().setCgDeepLinkListener(object : CGDeepLinkListener {
            override fun onSuccess(message: CGSTATE, deepLinkData: DeepLinkData) {
                if (message == CGSTATE.DEEPLINK_URL) {
                    var url: String? = ""
                    if (deepLinkData.content.url != null) {
                        url = deepLinkData.content.url
                    }
                    // Add your logic
                }

                if (message == CGSTATE.SUCCESS)
                {
                    println("")

                }

            }

            override fun onFailure(exceptions: CGSTATE) {

                when (exceptions)
                {
                    CGSTATE.EXCEPTION ->  println("")
                    CGSTATE.NETWORK_EXCEPTION ->    println("")
                    CGSTATE.INVALID_CAMPAIGN ->    println("")
                    CGSTATE.CAMPAIGN_UNAVAILABLE ->    println("")
                    CGSTATE.INVALID_URL ->    println("")
                    CGSTATE.USER_NOT_SIGNED_IN ->    println("")

                    else -> {}
                }

            }
        })
    }

    fun configureLoaderColour(context: Context,color:String)
    {
        CustomerGlu.getInstance().configureLoaderColour(context,color)
    }

    fun sendEventsToCG(context: Context,eventName:String,eventProperties:HashMap<String,Any>)
    {
        CustomerGlu.getInstance().sendEvent(context,eventName,eventProperties)
    }

    fun allowAnonymousUserRegistration(value: Boolean)
    {
        CustomerGlu.getInstance().allowAnonymousRegistration(value)
    }

    fun openWalletAsFallback(value: Boolean)
    {
        CustomerGlu.getInstance().openWalletAsFallback(value)
    }

}