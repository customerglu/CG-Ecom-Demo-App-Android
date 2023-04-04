package com.example.customerglu

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.Interface.DataListner
import com.customerglu.sdk.Modal.RegisterModal
import com.example.customerglu.Utils.Prefs

class ChangeLanguageActivity: AppCompatActivity() {

  lateinit var english_lyt:RelativeLayout
  lateinit var arabic_lyt:RelativeLayout
  lateinit var arabic_check:ImageView
  lateinit var english_check:ImageView
  lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)
        findViews()
        setLanguage()



    }

    private fun setLanguage() {
        if (Prefs.getKey(applicationContext,"language").isEmpty())
        {
            Prefs.putKey(applicationContext,"language","english")
        }
        var language = Prefs.getKey(applicationContext,"language")
        if (language.equals("english"))
        {
            english_check.visibility = VISIBLE
        }else{
            arabic_check.visibility = VISIBLE
        }
    }

    private fun findViews() {
        english_lyt = findViewById(R.id.english_lyt)
        arabic_lyt = findViewById(R.id.arabic_lyt)
        arabic_check = findViewById(R.id.arabic_check)
        english_check = findViewById(R.id.english_check)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            finish()
        }

        english_lyt.setOnClickListener {
            Prefs.putKey(applicationContext,"language","english")
            english_check.visibility = VISIBLE
            arabic_check.visibility = GONE
            registerUserWithCustomAttributes("english")

        }
        arabic_lyt.setOnClickListener {
            Prefs.putKey(applicationContext,"language","arabic")
            arabic_check.visibility = VISIBLE
            english_check.visibility = GONE
            registerUserWithCustomAttributes("arabic")

        }

    }

    private fun registerUserWithCustomAttributes(language:String)
    {
       var userId =  Prefs.getKey(applicationContext,"userId");
        var userData:HashMap<String,Any> = HashMap<String,Any>()
        var customAttributes:HashMap<String,Any> = HashMap<String,Any>()
        customAttributes.put("language",language)
        userData.put("userId", userId)
        userData.put("customAttributes", customAttributes)

        CustomerGlu.getInstance()
            .registerDevice(applicationContext, userData, object : DataListner {
                override fun onSuccess(registerModal: RegisterModal) {

                }

                override fun onFail(message: String) {}
            })
    }
}