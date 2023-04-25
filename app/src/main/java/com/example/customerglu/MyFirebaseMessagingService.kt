package com.example.customerglu

import android.annotation.SuppressLint
import android.util.Log
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Utils.CustomerGluManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FirebaseMessage", "Message data payload: ${remoteMessage.data}")
//        val body: String = remoteMessage.data.toString()
//        val json = JSONObject(body)
//        Log.d("json", json.toString())
        val jsonObject = JSONObject(remoteMessage.data as Map<*, *>?)
        Log.d("Cg",jsonObject.toString())
//        if (str.has("type")) {
            CustomerGluManager.displayCGNotifications(applicationContext,jsonObject,R.drawable.bags)



   //     }


//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d("FirebaseMessage", "Message data payload: ${remoteMessage.data}")
//
//        }
    }
}