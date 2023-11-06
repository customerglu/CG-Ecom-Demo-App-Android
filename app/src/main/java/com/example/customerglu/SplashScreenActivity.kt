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
    var userId:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
         userId =  Prefs.getKey(applicationContext,Constants.userId);
        getFcmToken()

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


        CustomerGluManager.displayCGBackgroundNotification(this)
        CustomerGluManager.enableEntryPoints(applicationContext)
        if(userId.isNotEmpty())
        {
            // Register the USer with CG on Every App Launch
            var userData:HashMap<String,Any> = HashMap<String,Any>()
            userData.put("userId", userId)
            CustomerGluManager.registerUser(applicationContext,userData)
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