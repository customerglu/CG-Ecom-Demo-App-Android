package com.example.customerglu.Adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.customerglu.R
import com.example.customerglu.R.drawable.*
import com.example.customerglu.HomeActivity
import com.example.customerglu.Model.Product
import com.example.customerglu.ProductDetailsActivity
import com.example.customerglu.db.FavItemViewModel
import com.example.customerglu.db.LikeProductEntity
import com.example.customerglu.db.ProductEntity


class ProductAdapter(private val productList: ArrayList<Product>, context: Context,): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    val ctx: Context = context
    var homeActivity: HomeActivity = HomeActivity()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        var selected = false;
        val product: Product = productList[position]
        holder.productBrandName_singleProduct.text = product.productBrand
        holder.productName_singleProduct.text = product.productName
        holder.productPrice_singleProduct.text = "$"+product.productPrice
        holder.productRating_singleProduct.rating = product.productRating
        if(product.isFavourite == true) {
            selected = true;
            holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_myfav);
        }
        holder.productAddToFav_singleProduct.setOnClickListener {
            if(selected == false) {
                selected = true
                holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_myfav);
                val cartViewModel = ViewModelProvider(homeActivity).get(FavItemViewModel::class.java)

                //    cartViewModel = ViewModelProviders.of(this).get(FavItemViewModel::class.java)

                cartViewModel.insert(LikeProductEntity(product.productName,1,product.productPrice,product.productId,product.productImage,product.productFrom))
             //  homeActivity.addItemToFav();


            }
           else if(selected == true){
                selected = false
                holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav);
            }
        }


        Glide.with(ctx)
            .load(product.productImage)
            .placeholder(bn)
            .into(holder.productImage_singleProduct)


        if(product.productHave == true){
            holder.discountTv_singleProduct.text = product.productDisCount
            holder.discount_singleProduct.visibility = View.VISIBLE
        }

        if(product.productHave == false){

            holder.discount_singleProduct.visibility = View.VISIBLE
            holder.discountTv_singleProduct.text = "New"

        }

        holder.itemView.setOnClickListener {
            goDetailsPage(product.productId.toInt())
        }

    }

    override fun getItemCount(): Int {
         return productList.size
    }

    public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val productImage_singleProduct:ImageView = itemView.findViewById(R.id.productImage_singleProduct)
        val productAddToFav_singleProduct:ImageView = itemView.findViewById(R.id.productAddToFav_singleProduct)
        val productRating_singleProduct:RatingBar = itemView.findViewById(R.id.productRating_singleProduct)
        val productBrandName_singleProduct:TextView = itemView.findViewById(R.id.productBrandName_singleProduct)
        val discountTv_singleProduct:TextView = itemView.findViewById(R.id.discountTv_singleProduct)
        val productName_singleProduct:TextView = itemView.findViewById(R.id.productName_singleProduct)
        val productPrice_singleProduct:TextView = itemView.findViewById(R.id.productPrice_singleProduct)
        val discount_singleProduct = itemView.findViewById<LinearLayout>(R.id.discount_singleProduct)


    }

    private fun goDetailsPage(position: Int) {
        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductIndex", position)
        intent.putExtra("ProductFrom", "New")
        ctx.startActivity(intent)
    }
    interface FavItemClickAdapter{
        fun onItemDeleteClick(product: ProductEntity)
        fun onItemAddClick(product: ProductEntity)


    }
}
