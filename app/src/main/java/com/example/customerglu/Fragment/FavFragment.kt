package com.example.customerglu.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.customerglu.sdk.CustomerGlu

import com.example.customerglu.R
import com.example.customerglu.Adapter.FavAdapter
import com.example.customerglu.Utils.CustomerGluManager
import com.example.customerglu.db.FavItemViewModel
import com.example.customerglu.db.LikeProductEntity
import com.example.customerglu.db.ProductEntity


class FavFragment : Fragment(), FavAdapter.FavItemClickAdapter {

    lateinit var animationView: LottieAnimationView
    lateinit var favRecView: RecyclerView
    lateinit var favAdapter: FavAdapter
    lateinit var emptyBagMsgLayout: LinearLayout
    lateinit var Item: ArrayList<LikeProductEntity>
    private lateinit var favViewModel: FavItemViewModel

    override fun onResume() {
        super.onResume()
        CustomerGluManager.setClassNameForCG(requireActivity(),"Wishlist")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_fav, container, false)


        favRecView = view.findViewById(R.id.favRecyclerView)
        animationView = view.findViewById(R.id.animationViewFavPage)
        emptyBagMsgLayout = view.findViewById(R.id.emptyBagMsgLayout)
        favAdapter = FavAdapter(activity as Context,this)
        val groceryManager = GridLayoutManager(context, 2)
        favRecView.setLayoutManager(groceryManager)
        favRecView.setItemAnimator(DefaultItemAnimator())
        favRecView.setAdapter(favAdapter)

        animationView.playAnimation()
        animationView.loop(true)
        val hashMap:HashMap<String,Any> = HashMap<String,Any> ()
        CustomerGluManager.sendEventsToCG(requireContext(),"viewedWishlist",hashMap)
        Item = arrayListOf()


        favViewModel = ViewModelProviders.of(this).get(FavItemViewModel::class.java)

        favViewModel.allproducts.observe(viewLifecycleOwner, Observer {List ->
            List?.let {
          //      Toast.makeText(context, ""+it.size, Toast.LENGTH_SHORT).show()
                favAdapter.updateList(it)
//                Item.clear()
//                sum = 0
//                Item.addAll(it)
            }

            if (List.size == 0){
                favRecView.visibility = View.GONE
                animationView.playAnimation()
                animationView.loop(true)
                emptyBagMsgLayout.visibility = View.VISIBLE

            }
            else{
                emptyBagMsgLayout.visibility = View.GONE
                animationView.pauseAnimation()
                favRecView.visibility = View.VISIBLE


            }

    })
            return view



    }

    override fun onItemDeleteClick(id: String) {
        favViewModel.deleteCart(id)
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        TODO("Not yet implemented")
    }


}