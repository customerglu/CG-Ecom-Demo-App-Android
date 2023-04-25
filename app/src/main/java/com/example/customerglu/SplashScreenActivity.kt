package com.example.customerglu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Utils.Constants
import com.example.customerglu.Utils.CustomerGluManager
import com.example.customerglu.Utils.Extensions.toast
import com.example.customerglu.Utils.Prefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONException
import org.json.JSONObject


class SplashScreenActivity : AppCompatActivity() {
    var userId:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
         userId =  Prefs.getKey(applicationContext,"userId");
        getFcmToken()
        if(userId!= null && !userId!!.isEmpty()) {
            CustomerGluManager.initializeSDK(applicationContext, debugMode = true)

//            CustomerGlu.getInstance().initializeSdk(applicationContext)
        }
        Handler().postDelayed({

            checkUser()


        }, 3000)

    }

    override fun onNewIntent(intent: Intent?) {

        super.onNewIntent(intent)
    }
    private fun getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(OnCompleteListener<String?> { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
               var fcmToken = token
                println("Fcm token")
                println(token)

                // Log and toast
            })
    }

    private fun checkUser() {
        /*
        if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }



*/

//        val parent_intent = intent
//        var intentSource = "none"
//        if (parent_intent != null) {
//            if (parent_intent.extras != null) {
//                intentSource = parent_intent.extras!!.getString("type", "none")
//                val myType = parent_intent.extras!!.getString("from", "none")
//                if (intentSource.equals("CustomerGlu", ignoreCase = true)) {
//                    val data = parent_intent.extras
//                    val json = JSONObject()
//                    val keys = data!!.keySet()
//                    for (key in keys) {
//                        try {
//                            json.put(key, JSONObject.wrap(data!![key]))
//                        } catch (e: JSONException) {
//                            //Handle exception here
//                        }
//                    }
//                    Handler().postDelayed({
//                        CustomerGlu.getInstance()
//                            .displayCustomerGluBackgroundNotification(applicationContext, json)
//                    },2000)
//
//                }
//            }
//        }

        CustomerGluManager.displayCGBackgroundNotification(this)
        if(userId!= null && !userId!!.isEmpty())
        {
             var isDemoApp = Prefs.getKey(applicationContext,"demoApp")
             var clientWriteKey =  Prefs.getKey(applicationContext,"writeKey")
                  if (isDemoApp.equals("true") )
                 {
                    CustomerGlu.setWriteKey(Constants.sandbox_key)
                 }else {
                    CustomerGlu.setWriteKey(clientWriteKey)
                 }
            CustomerGluManager.initializeSDK(applicationContext, debugMode = true)
            //  CustomerGlu.getInstance().initializeSdk(applicationContext)
            CustomerGlu.getInstance().enableEntryPoints(applicationContext, true)
           val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()

        }else {
            val intent = Intent(this, LoginOptionActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}