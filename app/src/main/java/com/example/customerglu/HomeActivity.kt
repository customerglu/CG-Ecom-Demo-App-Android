package com.example.customerglu

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Fragment.*
import com.example.customerglu.db.FavItemViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import org.json.JSONObject

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cartViewModel: FavItemViewModel
    private lateinit var mMessageReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        onClickRequestPermission()



        CustomerGlu.getInstance().gluSDKDebuggingMode(applicationContext, true)
        CustomerGlu.getInstance().enableEntryPoints(applicationContext, true)
        getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mMessageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Extract data included in the Intent
                try {

                    Handler().postDelayed({
                        if (intent.action.equals("CUSTOMERGLU_DEEPLINK_EVENT", ignoreCase = true)) {
                            val data = intent.getStringExtra("data")
                            val jsonObject = JSONObject(data)
                            if (jsonObject.has("deepLink")) {

                                var deeplink = jsonObject.get("deepLink")

                                if (deeplink.equals("customerglu://profile")) {
                                    bottomNavigationView.selectedItemId = R.id.profileMenu
                                } else if (deeplink.equals("customerglu://cart")) {
                                    bottomNavigationView.selectedItemId = R.id.bagMenu
                                } else if(deeplink.equals("customerglu://wishlist"))
                                {
                                    bottomNavigationView.selectedItemId = R.id.favMenu
                                }else if(deeplink.equals("customerglu://categories"))
                                {
                                    bottomNavigationView.selectedItemId = R.id.shopMenu
                                }

                                else {
                                    bottomNavigationView.selectedItemId = R.id.homeMenu
                                }

                            }
                            // Add the logic to redirect to appropriate page
                        }
                    }, 500)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }

        registerReceiver(mMessageReceiver, IntentFilter("CUSTOMERGLU_DEEPLINK_EVENT"))
        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, HomeFragment())
            .commit()
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


