package com.example.customerglu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.Interface.DataListner
import com.customerglu.sdk.Modal.RegisterModal
import com.example.customerglu.Utils.Prefs


class SplashScreenActivity : AppCompatActivity() {
    var userId:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
         userId =  Prefs.getKey(applicationContext,"userId");

        if(userId!= null && !userId!!.isEmpty()) {
            CustomerGlu.getInstance().initializeSdk(applicationContext)
        }
        Handler().postDelayed({

            checkUser()

        }, 3000)

    }

    private fun checkUser() {
        /*
        if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
*/
        if(userId!= null && !userId!!.isEmpty()){
          var clientWriteKey =  Prefs.getKey(applicationContext,"writeKey")

            CustomerGlu.setWriteKey(clientWriteKey)
           CustomerGlu.getInstance().initializeSdk(applicationContext)

           val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
//            var userData:HashMap<String,Any> = HashMap<String,Any>()
//            userData.put("userId", userId!!)
//            CustomerGlu.getInstance()
//                .registerDevice(applicationContext, userData, object : DataListner {
//                    override fun onSuccess(registerModal: RegisterModal) {
//                        intent = Intent(applicationContext, HomeActivity::class.java)
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                        // toast("Registered")
//                        Prefs.putKey(
//                            applicationContext,
//                            "userId",
//                            registerModal.data.getUser().userId
//                        )
//
//                        startActivity(intent)
//                        finish()
//                    }
//
//                    override fun onFail(message: String) {}
//                })

        }else {
            val intent = Intent(this, LoginOptionActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}