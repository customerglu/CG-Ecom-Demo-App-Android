package com.example.customerglu.Fragment


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.CustomerGlu.isDarkMode
import com.example.customerglu.Adapter.CoverProductAdapter
import com.example.customerglu.Adapter.ProductAdapter
import com.example.customerglu.Adapter.SaleProductAdapter
import com.example.customerglu.Model.Product
import com.example.customerglu.QRCodeScanner
import com.example.customerglu.R
import com.example.customerglu.Utils.Prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class HomeFragment : Fragment() {

//    NewProducts.json

    lateinit var coverRecView:RecyclerView
    lateinit var newRecView:RecyclerView
    lateinit var saleRecView:RecyclerView
    lateinit var coverProduct:ArrayList<Product>
    lateinit var newProduct:ArrayList<Product>
    lateinit var saleProduct:ArrayList<Product>

    lateinit var coverProductAdapter: CoverProductAdapter
    lateinit var newProductAdapter: ProductAdapter
    lateinit var saleProductAdapter: SaleProductAdapter
    lateinit var customerGlu: CustomerGlu

    lateinit var animationView: LottieAnimationView

    lateinit var newLayout:LinearLayout
    lateinit var saleLayout:LinearLayout
    var isAppearanceToggled:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

       // activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        customerGlu = CustomerGlu.getInstance();
        customerGlu.enableEntryPoints(context, true)
        coverProduct = arrayListOf()
        newProduct = arrayListOf()
        saleProduct = arrayListOf()

        customerGlu.enableEntryPoints(context,true)

        coverRecView = view.findViewById(R.id.coverRecView)
        newRecView = view.findViewById(R.id.newRecView)
        saleRecView = view.findViewById(R.id.saleRecView)
        newLayout = view.findViewById(R.id.newLayout)
        saleLayout = view.findViewById(R.id.saleLayout)
        animationView = view.findViewById(R.id.animationView)



        val visualSearchBtn_homePage:Switch = view.findViewById(R.id.visualSearchBt_homePage)

        hideLayout()

        setCoverData()
        setNewProductData()

        coverRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        coverRecView.setHasFixedSize(true)
        coverProductAdapter = CoverProductAdapter(activity as Context, coverProduct )
        coverRecView.adapter = coverProductAdapter



        newRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        newRecView.setHasFixedSize(true)
        newProductAdapter = ProductAdapter(newProduct, activity as Context )
        newRecView.adapter = newProductAdapter


        saleRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        saleRecView.setHasFixedSize(true)
        saleProductAdapter = SaleProductAdapter(saleProduct, activity as Context )
        saleRecView.adapter = saleProductAdapter



//        visualSearchBtn_homePage.setOnClickListener {
////            if (ContextCompat.checkSelfPermission(activity as Context, Manifest.permission.CAMERA)
////                == PackageManager.PERMISSION_DENIED)
////            {
////                onClickRequestPermission()
////            }else{
////                startActivity(Intent(context, QRCodeScanner::class.java))
////
////            }
//
//         //   isAppearanceToggled = !isAppearanceToggled;
//            if (isAppearanceToggled) {
//                AppCompatDelegate.setDefaultNightMode(
//                        AppCompatDelegate.MODE_NIGHT_YES);
//              //  requireActivity().recreate()
//           //     setUpCustomerGlu();
//
//            }else {
//             //   setUpCustomerGlu();
//                AppCompatDelegate.setDefaultNightMode(
//                        AppCompatDelegate.MODE_NIGHT_NO);
//                requireActivity().recreate()
////                setUpCustomerGlu();
//
//            }
//            CustomerGlu.getInstance().setDarkMode(context,isAppearanceToggled);
//
//        }
        customerGlu.listenToSystemDarkMode(false)
        if (Prefs.getKey(context,"isDarkMode").equals("true"))
        {
            visualSearchBtn_homePage.isChecked = true
        }
        visualSearchBtn_homePage.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                isDarkMode = false
                Prefs.putKey(context,"isDarkMode","false");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                customerGlu.setDarkMode(context, false)
            } else {
                isDarkMode = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Prefs.putKey(context,"isDarkMode","true");
                customerGlu.setDarkMode(context, true)
            }
        })

        showLayout()



        return view
    }

//    private fun setUpCustomerGlu() {
//
//    //    CustomerGlu.initializeSdk(context)
//        customerGlu = CustomerGlu.getInstance()
//        customerGlu.listenToSystemDarkMode(false)
//        customerGlu.enableAnalyticsEvent(true)
//        customerGlu.enableEntryPoints(context, true)
//        if (isAppearanceToggled) {
//            Log.e("DarkMode ", "true")
//            customerGlu.setDarkMode(context, false)
//        } else {
//            Log.e("DarkMode ", "false" )
//            customerGlu.setDarkMode(context, true)
//            customerGlu.enableDarkMode(true)
//        }
//    }

    override fun onResume() {
        super.onResume()
        customerGlu.showEntryPoint(activity,"Home")

    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(context, QRCodeScanner::class.java)
                startActivity(intent)
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }
    fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                activity as Context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(context, QRCodeScanner::class.java)
                startActivity(intent)
              //  toast("Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {

                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }

        }


    }



    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        coverRecView.visibility = View.GONE
        newLayout.visibility = View.GONE
        saleLayout.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        animationView.visibility = View.GONE
        coverRecView.visibility = View.VISIBLE
        newLayout.visibility = View.VISIBLE
        saleLayout.visibility = View.VISIBLE
    }

    fun getJsonData(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun setCoverData() {

        val jsonFileString = context?.let {

            getJsonData(it, "CoverProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->

            coverProduct.add(person)
            saleProduct.add(person)

        }


    }

    private fun setNewProductData() {

        val jsonFileString = context?.let {

            getJsonData(it, "NewProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->


            newProduct.add(person)


        }


    }

}


