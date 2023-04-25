package com.example.customerglu.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.airbnb.lottie.LottieAnimationView
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.Adapter.AddToCartAdapter
import com.example.customerglu.R

import com.example.customerglu.AddAddressActivity
import com.example.customerglu.PaymentActivity
import com.example.customerglu.Utils.CustomerGluManager
import com.example.customerglu.db.CartViewModel
import com.example.customerglu.db.ProductEntity
import com.google.android.material.bottomsheet.BottomSheetDialog


class BagFragment : Fragment(), AddToCartAdapter.CartItemClickAdapter {

    lateinit var cartRecView:RecyclerView
    lateinit var cartAdapter: AddToCartAdapter
    lateinit var animationView: LottieAnimationView
    lateinit var totalPriceBagFrag:TextView
    lateinit var checkOut_BagPage: Button
    lateinit var Item: ArrayList<ProductEntity>
     var sum:Int = 0

    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bag, container, false)

        cartRecView = view.findViewById(R.id.cartRecView)
        animationView = view.findViewById(R.id.animationViewCartPage)
        totalPriceBagFrag = view.findViewById(R.id.totalPriceBagFrag)
        checkOut_BagPage = view.findViewById(R.id.checkOut_BagPage)
        val bottomCartLayout:LinearLayout = view.findViewById(R.id.bottomCartLayout)
        val emptyBagMsgLayout:LinearLayout = view.findViewById(R.id.emptyBagMsgLayout)
        val MybagText:TextView = view.findViewById(R.id.MybagText)
        Item = arrayListOf()

        checkOut_BagPage.setOnClickListener {
          //  Toast.makeText(context, "Checkout", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, AddAddressActivity::class.java)

// To pass any data to next activity
          //  intent.putExtra("keyIdentifier", valxue)
// start your next activity
            startActivity(intent)
        }


        animationView.playAnimation()
        animationView.loop(true)
        bottomCartLayout.visibility = View.GONE
        MybagText.visibility = View.GONE
        emptyBagMsgLayout.visibility = View.VISIBLE

        cartRecView.itemAnimator = DefaultItemAnimator()
        cartRecView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        cartAdapter = AddToCartAdapter(activity as Context, this )
        cartRecView.setAdapter(cartAdapter)


        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        cartViewModel.allproducts.observe(viewLifecycleOwner, Observer {List ->
            List?.let {
                cartAdapter.updateList(it)
                Item.clear()
                sum = 0
                Item.addAll(it)
            }

            if (List.size == 0){
                animationView.playAnimation()
                animationView.loop(true)
                bottomCartLayout.visibility = View.GONE
                MybagText.visibility = View.GONE
                emptyBagMsgLayout.visibility = View.VISIBLE

            }
            else{
                emptyBagMsgLayout.visibility = View.GONE
                bottomCartLayout.visibility = View.VISIBLE
                MybagText.visibility = View.VISIBLE
                animationView.pauseAnimation()
            }

            Item.forEach {
                sum += it.price
            }
            totalPriceBagFrag.text = "$" + sum
        })




        return view
    }

    override fun onResume() {
        super.onResume()
        CustomerGluManager.setClassNameForCG(requireActivity(),"Cart");

    }

    override fun onItemDeleteClick(product: ProductEntity) {
        cartViewModel.deleteCart(product)
        Toast.makeText(context,"Removed From Bag",Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        cartViewModel.updateCart(product)
    }


}