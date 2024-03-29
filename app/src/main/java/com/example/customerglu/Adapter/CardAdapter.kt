package com.example.customerglu.Adapter//package com.example.customerglu.Adapter
//
//import android.content.Context
//import android.content.SharedPreferences
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.recyclerview.widget.RecyclerView
//import com.example.buynow.R
//import com.example.customerglu.db.Card.CardEntity
//
//
//class CardAdapter(private val ctx: Context, val listener: com.example.customerglu.Adapter.CarDItemClickAdapter) :RecyclerView.Adapter<com.example.customerglu.Adapter.CardAdapter.cardViewHolder>() {
//
//    private val cardList: ArrayList<CardEntity> = arrayListOf()
//
//
//    public class cardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//
//
//        val cardNumber: TextView = itemView.findViewById(R.id.cardNumber_singleCard)
//        val cardHName: TextView = itemView.findViewById(R.id.cardHolderName_singleCard)
//        val exp: TextView = itemView.findViewById(R.id.expiryDate_singleCard)
//        val layD : LinearLayout = itemView.findViewById(R.id.useDefault_Layout)
//        val checkbox : CheckBox = itemView.findViewById(R.id.checkBox_SingleCard)
//        val cardImage:ImageView = itemView.findViewById(R.id.cardBrandImage_singleCard)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): _root_ide_package_.com.example.customerglu.Adapter.CardAdapter.cardViewHolder {
//
//        val cardView = LayoutInflater.from(ctx).inflate(R.layout.single_card,parent,false)
//        return _root_ide_package_.com.example.customerglu.Adapter.CardAdapter.cardViewHolder(
//            cardView
//        )
//    }
//
//    override fun onBindViewHolder(holder: _root_ide_package_.com.example.customerglu.Adapter.CardAdapter.cardViewHolder, position: Int) {
//        val cardItem:CardEntity = cardList[position]
//
//
//
//        when (cardItem.brandC) {
//            "MASTERCARD" -> holder.cardImage.setImageResource(R.drawable.ic_mastercard)
//            "VISA" -> holder.cardImage.setImageResource(R.drawable.ic_visa)
//            "AMERICAN_EXPRESS" -> holder.cardImage.setImageResource(R.drawable.ic_american_express)
//            "DINERS_CLUB" -> holder.cardImage.setImageResource(R.drawable.ic_diners_club)
//            "DISCOVER" -> holder.cardImage.setImageResource(R.drawable.ic_discover)
//            "JCB" -> holder.cardImage.setImageResource(R.drawable.ic_jcb)
//            "CHINA_UNION_PAY" -> holder.cardImage.setImageResource(R.drawable.ic_unionpay)
//
//            else -> {
//                holder.cardImage.setImageResource(R.drawable.ic_mastercard)
//            }
//        }
//
//        holder.cardHName.text = cardItem.nameCH.toString()
//        holder.exp.text = cardItem.exp
//        holder.cardNumber.text = cardItem.number
//        if(cardList.size > 1){
//         //   holder.layD.visibility = View.VISIBLE
//
//        }
//        else{
//          //  holder.layD.visibility = View.GONE
//        }
//
//       // holder.checkbox.setOnClickListener {
//
////            val editor:SharedPreferences.Editor =  defaultCard.edit()
////            if(holder.checkbox.isChecked){
////                holder.checkbox.isChecked = false
////                editor.putBoolean("isHaveDefaultCard",false)
////                editor.putString("cardNumber","")
////
////            }
////            else{
////                holder.checkbox.isChecked = true
////                editor.putBoolean("isHaveDefaultCard",true)
////                editor.putString("cardNumber",cardItem.number)
////
////            }
////            editor.apply()
////            editor.commit()
//
//      //  }
//
//    }
//
//    override fun getItemCount(): Int {
//        return cardList.size
//    }
//
//    fun updateList(newList: List<CardEntity>){
//        cardList.clear()
//        cardList.addAll(newList)
//        notifyDataSetChanged()
//    }
//}
//
//
//interface CarDItemClickAdapter{
//    fun onItemDeleteClick(cardEntity: CardEntity)
//    fun onItemUpdateClick(cardEntity: CardEntity)
//}