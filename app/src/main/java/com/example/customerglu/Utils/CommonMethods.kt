package com.example.customerglu.Utils

import android.content.Context
import android.content.Intent
import com.example.customerglu.HomeActivity

class CommonMethods {
    companion object {
         fun navigateToActivity(context: Context, screen: String?) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.NavigateTo, screen)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

}