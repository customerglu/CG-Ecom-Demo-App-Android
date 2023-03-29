package com.example.customerglu

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.customerglu.R
import com.example.customerglu.db.CartViewModel

class PaymentActivity:AppCompatActivity() {
    lateinit var purchase:Button
    private lateinit var cartViewModel: CartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)


        purchase = findViewById(R.id.purchase);
        purchase.setOnClickListener {
            cartViewModel.deleteAll()
            val intent = Intent(applicationContext,PaymentSuccessActivity::class.java)
            startActivity(intent)
        }
    }


}