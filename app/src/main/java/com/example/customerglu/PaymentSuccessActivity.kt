package com.example.customerglu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.customerglu.sdk.CustomerGlu

class PaymentSuccessActivity:AppCompatActivity() {
    lateinit var imageView4:ImageView
    lateinit var textView4:TextView
    lateinit var checkOut_BagPage:Button
    lateinit var animationView: LottieAnimationView

    override fun onResume() {
        super.onResume()
        CustomerGlu.getInstance().showEntryPoint(this,"SuccessPage");

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucess_payment)
        imageView4 = findViewById(R.id.imageView4)
        textView4 = findViewById(R.id.textView4)
        checkOut_BagPage = findViewById(R.id.checkOut_BagPage)
        animationView = findViewById(R.id.animationView)
        checkOut_BagPage.setOnClickListener {
            val intent = Intent(applicationContext,HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        hideLayouts()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            showLayout()
            // do something after 1000ms
        }, 3000)
    }

    private fun showLayout() {
        animationView.pauseAnimation()
        imageView4.visibility = View.VISIBLE
        textView4.visibility = View.VISIBLE
        checkOut_BagPage.visibility = View.VISIBLE
        animationView.visibility = View.GONE
        val hashMap:HashMap<String,Any> = HashMap<String,Any> ()
        CustomerGlu.getInstance().sendEvent(applicationContext,"orderPlaced",hashMap)
    }

    private fun hideLayouts() {
        animationView.playAnimation()
        animationView.loop(true)
        imageView4.visibility = View.GONE
        textView4.visibility = View.GONE
        checkOut_BagPage.visibility = View.GONE
        animationView.visibility = View.VISIBLE

    }

    override fun onBackPressed() {
     //   super.onBackPressed()
        val intent = Intent(applicationContext,HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}