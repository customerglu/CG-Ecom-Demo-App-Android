package com.example.customerglu

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Fragment.*
import com.example.customerglu.db.FavItemViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView:BottomNavigationView
    private lateinit var cartViewModel: FavItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        CustomerGlu.getInstance().gluSDKDebuggingMode(applicationContext,true)
        CustomerGlu.getInstance().enableEntryPoints(applicationContext, true)
        getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, HomeFragment() ).commit()
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
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.shopMenu -> {
                val fragment = ShopFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.bagMenu -> {
                val fragment = BagFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.favMenu -> {
                val fragment = FavFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.profileMenu -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }

     fun addItemToFav(pName:String,qua:Int,pPrice:String,pPid:String,pImage:String)
    {
     //   val cartViewModel = ViewModelProvider(this).get(FavItemViewModel::class.java)


    }
}


