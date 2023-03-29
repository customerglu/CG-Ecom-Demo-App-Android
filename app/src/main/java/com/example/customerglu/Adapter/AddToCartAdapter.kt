package com.example.customerglu.Adapter



import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.customerglu.R
import com.example.customerglu.R.drawable.*
import com.example.customerglu.ProductDetailsActivity
import com.example.customerglu.db.LikeProductEntity
import com.example.customerglu.db.ProductEntity


class AddToCartAdapter( context: Context,val listener:CartItemClickAdapter ): RecyclerView.Adapter<AddToCartAdapter.CartViewHolder>()  {

    val ctx: Context = context
    private val cartList: ArrayList<ProductEntity> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddToCartAdapter.CartViewHolder {

        val cartView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_single,parent,false)

        return AddToCartAdapter.CartViewHolder(cartView)

    }




    override fun getItemCount(): Int {
        return cartList.size
    }

    class CartViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val cartMore: ImageView = itemView.findViewById(R.id.cartMore)
        val cartName: TextView = itemView.findViewById(R.id.cartName)
        val cartPrice: TextView = itemView.findViewById(R.id.cartPrice)
        val quantityTvCart: TextView = itemView.findViewById(R.id.quantityTvCart)



    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ProductEntity>){
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }
    interface CartItemClickAdapter{
        fun onItemDeleteClick(product: ProductEntity)
        fun onItemUpdateClick(product: ProductEntity)


    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem:ProductEntity = cartList[position]

        holder.cartName.text = cartItem.name
        holder.cartPrice.text = "$"+ cartItem.price
        holder.quantityTvCart.text = cartItem.qua.toString()
        holder.cartMore.setOnClickListener {

        }

        Glide.with(ctx)
            .load(cartItem.Image)
            .into(holder.cartImage)

        holder.cartMore.setOnClickListener {
            listener.onItemDeleteClick(cartItem)
        }
    }
}