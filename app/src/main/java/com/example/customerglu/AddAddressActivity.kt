package com.example.customerglu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProviders
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.R
import com.example.customerglu.db.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddAddressActivity : AppCompatActivity() {

    lateinit var purchase:Button
    lateinit var cashLyt:RelativeLayout
    lateinit var cardLyt:RelativeLayout
    private lateinit var cartViewModel: CartViewModel

    override fun onResume() {
        super.onResume()
        CustomerGlu.getInstance().showEntryPoint(this,"Address")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        purchase = findViewById(R.id.purchase)

        purchase.setOnClickListener {
            val dialog = BottomSheetDialog(this)

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            cashLyt = view.findViewById(R.id.cash_lyt)
            cardLyt = view.findViewById(R.id.card_lyt)

            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            cashLyt.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                cartViewModel.deleteAll()

                startActivity(Intent(applicationContext,PaymentSuccessActivity::class.java))

                dialog.dismiss()
            }
            cardLyt.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                startActivity(Intent(applicationContext,PaymentActivity::class.java))
                dialog.dismiss()
            }
            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(false)

            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)

            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
    }
}