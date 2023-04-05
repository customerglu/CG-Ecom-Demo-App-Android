package com.example.customerglu.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.customerglu.sdk.CustomerGlu
import com.example.customerglu.*
import com.example.customerglu.R
import com.example.customerglu.PaymentActivity
import com.example.customerglu.ShipingAddressActivity
import com.example.customerglu.Utils.FirebaseUtils.storageReference
import com.example.customerglu.Utils.Prefs
import com.example.customerglu.db.Card.CardViewModel
import com.example.customerglu.db.CartViewModel
import com.example.customerglu.db.FavItemViewModel
import com.example.customerglu.loadingDialog


import com.google.android.gms.tasks.Continuation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

import com.google.firebase.storage.UploadTask
import com.squareup.okhttp.Dispatcher
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap


class ProfileFragment : Fragment() {

    lateinit var animationView: LottieAnimationView

    lateinit var profileImage_profileFrag: CircleImageView
    private lateinit var cartViewModel: CartViewModel
    private lateinit var favModel: FavItemViewModel
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var loadingDialog: loadingDialog

    lateinit var uploadImage_profileFrag:Button
    lateinit var profileName_profileFrag:TextView
    lateinit var profileEmail_profileFrag:TextView

    private lateinit var cardViewModel: CardViewModel

    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var cards: Int = 0

    lateinit var linearLayout2:LinearLayout
    lateinit var linearLayout3:LinearLayout
    lateinit var linearLayout4:LinearLayout
    lateinit var rewards:LinearLayout
    lateinit var language_lyt:CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
        CustomerGlu.getInstance().showEntryPoint(activity,"Account")

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        favModel = ViewModelProviders.of(this).get(FavItemViewModel::class.java)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        profileImage_profileFrag = view.findViewById(R.id.profileImage_profileFrag)
        val settingCd_profileFrag = view.findViewById<CardView>(R.id.settingCd_profileFrag)
        uploadImage_profileFrag = view.findViewById(R.id.uploadImage_profileFrag)
        profileName_profileFrag = view.findViewById(R.id.profileName_profileFrag)
        profileEmail_profileFrag = view.findViewById(R.id.profileEmail_profileFrag)
        animationView = view.findViewById(R.id.animationView)
        linearLayout2 = view.findViewById(R.id.linearLayout2)
        linearLayout3 = view.findViewById(R.id.linearLayout3)
        linearLayout4 = view.findViewById(R.id.linearLayout4)
        rewards = view.findViewById(R.id.rewards);
        language_lyt = view.findViewById(R.id.language_lyt);
        val shippingAddressCard_ProfilePage = view.findViewById<CardView>(R.id.shippingAddressCard_ProfilePage)
        val paymentMethod_ProfilePage = view.findViewById<CardView>(R.id.paymentMethod_ProfilePage)
        val cardsNumber_profileFrag:TextView = view.findViewById(R.id.cardsNumber_profileFrag)
        val hashMap:HashMap<String,Any> = HashMap<String,Any> ()
        CustomerGlu.getInstance().sendEvent(context,"viewedProfile",hashMap)
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

//        cardViewModel.allCards.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            cards = it.size
//        })
//
//        if(cards == 0){
//            cardsNumber_profileFrag.text = "You Have no Cards."
//        }
//        else{
//
//        cardsNumber_profileFrag.text = "You Have "+ cards.toString() + " Cards."
//        }

        shippingAddressCard_ProfilePage.setOnClickListener {
            startActivity(Intent(context, ShipingAddressActivity::class.java))
        }

        paymentMethod_ProfilePage.setOnClickListener {
            CustomerGlu.getInstance().openWallet(context)
           // startActivity(Intent(context, PaymentActivity::class.java))
        }


        hideLayout()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
                        showLayout()
            // do something after 1000ms
        }, 3000)



        uploadImage_profileFrag.visibility = View.GONE


        //getUserData()

        uploadImage_profileFrag.setOnClickListener {
            uploadImage()
        }
        rewards.setOnClickListener {
            CustomerGlu.getInstance().openWallet(context)
        }

        settingCd_profileFrag.setOnClickListener {
            CustomerGlu.getInstance().clearGluData(context)
            Prefs.putKey(context,"userId","")
            Prefs.clearSharedPreferences(context)
            cartViewModel.deleteAll()
            favModel.deleteAll()
            val intent = Intent(context, LoginOptionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        language_lyt.setOnClickListener {

            val intent = Intent(context, ChangeLanguageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }



        profileImage_profileFrag.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(context,profileImage_profileFrag)

            popupMenu.menuInflater.inflate(R.menu.profile_photo_storage,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.galleryMenu ->
                        launchGallery()
                    R.id.cameraMenu ->
                        uploadImage()

                }
                true
            })
            popupMenu.show()

    }

        return view
    }

    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        linearLayout2.visibility = View.GONE
        linearLayout3.visibility = View.GONE
        linearLayout4.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        animationView.visibility = View.GONE
        linearLayout2.visibility = View.VISIBLE
        linearLayout3.visibility = View.VISIBLE
        linearLayout4.visibility = View.VISIBLE
    }


    private fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
        try {

             val querySnapshot = userCollectionRef
                 .document(firebaseAuth.uid.toString())
                 .get().await()

            val userImage:String = querySnapshot.data?.get("userImage").toString()
            val userName:String = querySnapshot.data?.get("userName").toString()
            val userEmail:String = querySnapshot.data?.get("userEmail").toString()


            withContext(Dispatchers.Main){

                profileName_profileFrag.text = "Jane Doe"
                profileEmail_profileFrag.text = "xyz@gmail.com"
                Glide.with(this@ProfileFragment)
                    .load(userImage)
                    .placeholder(R.drawable.ic_profile)
                    .into(profileImage_profileFrag)

                showLayout()
            }


        }catch (e:Exception){

        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(){

        if(filePath != null){
            val ref = storageReference?.child("profile_Image/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())

                    // show save...


                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{

            Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                profileImage_profileFrag.setImageBitmap(bitmap)
                uploadImage_profileFrag.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addUploadRecordToDb(uri: String) = CoroutineScope(Dispatchers.IO).launch {

        try {

            userCollectionRef.document(firebaseAuth.uid.toString())
                .update("userImage" , uri ).await()

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, ""+e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}