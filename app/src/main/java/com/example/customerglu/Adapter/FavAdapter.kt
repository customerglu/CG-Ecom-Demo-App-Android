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


class FavAdapter( context: Context,val listener:FavItemClickAdapter ): RecyclerView.Adapter<FavAdapter.ViewHolder>()  {

    val ctx: Context = context
    private val favtList: ArrayList<LikeProductEntity> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: FavAdapter.ViewHolder, position: Int) {
        var selected = false;
        val product: LikeProductEntity = favtList[position]

     //   val product: Product = productList[position]
     //   holder.productBrandName_singleProduct.text = product.name
        holder.productName_singleProduct.text = product.name
        holder.productPrice_singleProduct.text = "$"+product.price
        holder.productRating_singleProduct.rating = 3.5f
        holder.productRating_singleProduct.rating = 3.5f
//        if(product.isFavourite == true) {
//            selected = true;
//            holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_myfav);
//        }
//        holder.productAddToFav_singleProduct.setOnClickListener {
//            if(selected == false) {
//                selected = true
//                holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_myfav);
//                val cartViewModel = ViewModelProvider(homeActivity).get(FavItemViewModel::class.java)
//
//                //    cartViewModel = ViewModelProviders.of(this).get(FavItemViewModel::class.java)
//
//                cartViewModel.insert(LikeProductEntity(product.productName,1,product.productPrice,product.productId,product.productImage))
//                //  homeActivity.addItemToFav();
//
//
//            }
//            else if(selected == true){
//                selected = false
//                holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav);
//            }
//        }


        Glide.with(ctx)
            .load(product.Image)
            .placeholder(bn)
            .into(holder.productImage_singleProduct)

//
//        if(product.productHave == true){
//            holder.discountTv_singleProduct.text = product.productDisCount
//            holder.discount_singleProduct.visibility = View.VISIBLE
//        }
//
//        if(product.productHave == false){
//
//            holder.discount_singleProduct.visibility = View.VISIBLE
//            holder.discountTv_singleProduct.text = "New"
//
//        }
        holder.removeItem.setOnClickListener {
            listener.onItemDeleteClick(product.pId)
        }

        holder.itemView.setOnClickListener {
            goDetailsPage(product.pId.toInt(),product.productFrom)
        }

    }

    override fun getItemCount(): Int {
        return favtList.size
    }

     class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val productImage_singleProduct:ImageView = itemView.findViewById(R.id.productImage_singleProduct)
        val productAddToFav_singleProduct:ImageView = itemView.findViewById(R.id.productAddToFav_singleProduct)
        val productRating_singleProduct:RatingBar = itemView.findViewById(R.id.productRating_singleProduct)
        val productBrandName_singleProduct:TextView = itemView.findViewById(R.id.productBrandName_singleProduct)
        val discountTv_singleProduct:TextView = itemView.findViewById(R.id.discountTv_singleProduct)
        val productName_singleProduct:TextView = itemView.findViewById(R.id.productName_singleProduct)
        val productPrice_singleProduct:TextView = itemView.findViewById(R.id.productPrice_singleProduct)
        val removeItem = itemView.findViewById<TextView>(R.id.removeItem)


    }
    fun updateList(newList: List<LikeProductEntity>){
        favtList.clear()
        favtList.addAll(newList)
        notifyDataSetChanged()
    }

    private fun goDetailsPage(position: Int,productFrom:String) {
        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductIndex", position)
        intent.putExtra("ProductFrom", productFrom)
        ctx.startActivity(intent)
    }
    interface FavItemClickAdapter{
        fun onItemDeleteClick(id: String)
        fun onItemUpdateClick(product: ProductEntity)


    }
}