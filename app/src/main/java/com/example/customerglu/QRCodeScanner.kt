package com.example.customerglu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.example.customerglu.Utils.Extensions.toast
import com.example.customerglu.Utils.Prefs
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import java.net.URL


class QRCodeScanner : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    var fromLogin:Boolean = false;
    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        val webView = findViewById<WebView>(R.id.webView)

        codeScanner = CodeScanner(this, scannerView)

        fromLogin = intent.getBooleanExtra("fromLogin",false)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                if (fromLogin)
                {
                    val url = it.text
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.putExtra("fromLogin","true")
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    try {
                        startActivity(i)
                        finish()
                    } catch (e: ActivityNotFoundException) {
                        // Chrome is probably not installed
                        // Try with the default browser
                        i.setPackage(null)
                        startActivity(i)
                    }
//                    val intent = Intent(this,CampaignActivity::class.java)
//                    intent.putExtra("fromLogin","true")
//                    startActivity(intent)
//                    finish()
                }else {
                    val url = it.text
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    try {
                        startActivity(i)
                        finish()
                    } catch (e: ActivityNotFoundException) {
                        // Chrome is probably not installed
                        // Try with the default browser
                        i.setPackage(null)
                        startActivity(i)
                    }

                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


}