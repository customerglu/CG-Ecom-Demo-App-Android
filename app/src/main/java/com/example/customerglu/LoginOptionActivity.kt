package com.example.customerglu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.customerglu.Utils.Extensions.toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.branch.referral.Branch


class LoginOptionActivity :AppCompatActivity() {

    lateinit var test_env:Button
    lateinit var exp_demo_app:Button
    lateinit var visualSearchBtn_homePage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_option)
        test_env = findViewById(R.id.test_env)
        exp_demo_app = findViewById(R.id.exp_demo_app)
        visualSearchBtn_homePage = findViewById(R.id.visualSearchBtn_homePage)
        getDeepLink()
        test_env.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("demoApp",false)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        exp_demo_app.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("demoApp",true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        visualSearchBtn_homePage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            {
                onClickRequestPermission()
            }else{
                val intent = Intent(this, QRCodeScanner::class.java)
                startActivity(intent)
            }
        }

    }
    private fun getDeepLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                 //   toast(deepLink.toString())
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

            }
            .addOnFailureListener(this) { e -> Log.w("CustomerGlu", "getDynamicLink:onFailure", e) }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(this, QRCodeScanner::class.java)
                startActivity(intent)
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(this, QRCodeScanner::class.java)
                startActivity(intent)
              //  toast("Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
               // toast("Granted1")

                requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
            else -> {
               // toast("Granted2")
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }

            }


        }
    override fun onStart() {
        super.onStart()
//        Branch.sessionBuilder(this).withCallback(branchReferralInitListener)
//            .withData(if (intent != null) intent.data else null).init()
    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        setIntent(intent)
//        // if activity is in foreground (or in backstack but partially visible) launching the same
//        // activity will skip onStart, handle this case with reInitSession
//        if (intent != null &&
//            intent.hasExtra("branch_force_new_session") &&
//            intent.getBooleanExtra("branch_force_new_session", false)
//        ) {
//            Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit()
//        }
//    }

//    private val branchReferralInitListener: Branch.BranchReferralInitListener =
//        Branch.BranchReferralInitListener { linkProperties, error ->
//            // do stuff with deep link data (nav to page, display content, etc)
//        }

    }
