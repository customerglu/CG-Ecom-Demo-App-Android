package com.example.customerglu

import android.app.Activity
import android.app.AlertDialog
import com.example.customerglu.R

class loadingDialog(private val activity: Activity) {

    private var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog!!.dismiss()
    }
}