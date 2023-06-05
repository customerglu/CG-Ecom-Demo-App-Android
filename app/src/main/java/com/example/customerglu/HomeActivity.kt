package com.example.customerglu

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Fragment.*
import com.example.customerglu.Utils.Constants
import com.example.customerglu.Utils.Prefs
import com.example.customerglu.db.FavItemViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cartViewModel: FavItemViewModel
    private lateinit var mMessageReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        onClickRequestPermission()

        getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        var type =  intent.getStringExtra(Constants.NavigateTo)

        CustomerGlu.getInstance().openWalletAsFallback(false)
        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, HomeFragment())
            .commit()
        if (type!= null)
        {
            when (type) {
                Constants.Cart -> bottomNavigationView.selectedItemId = R.id.bagMenu
                Constants.Profile  -> bottomNavigationView.selectedItemId = R.id.profileMenu
                Constants.Categories  -> bottomNavigationView.selectedItemId = R.id.shopMenu
                Constants.Wishlist  -> bottomNavigationView.selectedItemId = R.id.favMenu
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
        }
        var key = Prefs.getKey(applicationContext,"writeKey")
        Log.e("CG","key "+key);
        if (!key.isEmpty())
        {
            Log.e("CG","has key "+key);
            CustomerGlu.setWriteKey(key)
        }else{
            Log.e("CG","has key "+Constants.sandbox_key);
            CustomerGlu.setWriteKey(Constants.sandbox_key)
        }
        CustomerGlu.getInstance().initializeSdk(applicationContext)
        CustomerGlu.getInstance().setupCGDeepLinkIntentData(this)
        CustomerGlu.getInstance().gluSDKDebuggingMode(applicationContext, true)
        CustomerGlu.getInstance().enableEntryPoints(applicationContext, true)
    }

    override fun onResume() {
        super.onResume()
      //  addDebugBanner()
    }



    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
//                val intent = Intent(this, QRCodeScanner::class.java)
//                startActivity(intent)
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
//                val intent = Intent(this, QRCodeScanner::class.java)
//                startActivity(intent)
                //  toast("Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                // toast("Granted1")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
            else -> {
                // toast("Granted2")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }

        }


    }

//    override fun recreate() {
//        finish()
//        overridePendingTransition(
//            0,
//            0
//        )
//        startActivity(intent)
//        overridePendingTransition(
//            0,
//            0
//        )
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//           setTheme(R.style.Theme_LightBuyNow)
//        }
//        else{
//            setTheme(R.style.Theme_DarkBuyNow)
//        }
//        }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        super.onNewIntent(intent)
        var key = Prefs.getKey(applicationContext,"writeKey")

        if (!key.isEmpty())
        {
            CustomerGlu.setWriteKey(key)
        }else{
            CustomerGlu.setWriteKey(Constants.sandbox_key)
        }
        var type = intent?.getStringExtra(Constants.NavigateTo)
        if (type!= null)
        {
            when (type) {
                Constants.Cart -> bottomNavigationView.selectedItemId = R.id.bagMenu
                Constants.Profile  -> bottomNavigationView.selectedItemId = R.id.profileMenu
                Constants.Categories  -> bottomNavigationView.selectedItemId = R.id.shopMenu
                Constants.Wishlist  -> bottomNavigationView.selectedItemId = R.id.favMenu
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
        }
        CustomerGlu.getInstance().initializeSdk(applicationContext)
        CustomerGlu.getInstance().setupCGDeepLinkIntentData(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeMenu -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.shopMenu -> {
                val fragment = ShopFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.bagMenu -> {
                val fragment = BagFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.favMenu -> {
                val fragment = FavFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.profileMenu -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }

    fun addItemToFav(pName: String, qua: Int, pPrice: String, pPid: String, pImage: String) {
        //   val cartViewModel = ViewModelProvider(this).get(FavItemViewModel::class.java)


    }
}


