package com.example.customerglu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.Interface.DataListner
import com.customerglu.sdk.Modal.RegisterModal
import com.example.customerglu.Utils.Constants
import com.example.customerglu.Utils.CustomerGluManager
import com.example.customerglu.Utils.Extensions.toast
import com.example.customerglu.Utils.FirebaseUtils.firebaseAuth
import com.example.customerglu.Utils.Prefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var writeKeytxt: String
     var isDemoApp: Boolean = false
     var fcmToken: String = ""
    lateinit var signInBtn: Button
    lateinit var emailEt: EditText
    lateinit var passEt: EditText
    lateinit var writeKey: EditText

    lateinit var loadingDialog: loadingDialog

    lateinit var emailError:TextView
    lateinit var writekey_lyt:LinearLayout
    lateinit var passwordError:TextView
    lateinit var qr_scanner:ImageView
    lateinit var progressBar:ProgressBar




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signUpTv = findViewById<TextView>(R.id.signUpTv)
        signInBtn = findViewById(R.id.loginBtn)
        qr_scanner = findViewById(R.id.qr_scanner)
        writekey_lyt = findViewById(R.id.writeKey_lyt)
        emailEt = findViewById(R.id.userId_edt)
        passEt = findViewById(R.id.tag_edt)
        writeKey = findViewById(R.id.writeKey)
        emailError = findViewById(R.id.emailError)
        passwordError = findViewById(R.id.passwordError)
        progressBar = findViewById(R.id.pg_view)
        isDemoApp = intent.getBooleanExtra("demoApp",false)
        Prefs.putKey(applicationContext,"demoApp",""+isDemoApp)
        var key = Prefs.getKey(applicationContext,"writeKey")

        if (!key.isEmpty())
        {
            writeKey.setText(key)
        }
        getFcmToken()

        if (isDemoApp)
        {
            CustomerGlu.setWriteKey(Constants.sandbox_key)
            writekey_lyt.visibility = GONE
            CustomerGluManager.initializeSDK(applicationContext, debugMode = true)
        }

        qr_scanner.setOnClickListener {
            onClickRequestPermission()


        }


        textAutoCheck()

        loadingDialog = loadingDialog(this)

        signUpTv.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {

            checkInput()

        }


    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(OnCompleteListener<String?> { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                fcmToken = token
                println("Fcm token")
                println(token)

                // Log and toast
            })
    }

    override fun onResume() {
        super.onResume()
        CustomerGluManager.setClassNameForCG(this,"login")

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(this, QRCodeScanner::class.java)
                intent.putExtra("fromLogin",true)
                startActivity(intent)
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }
    fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(applicationContext,QRCodeScanner::class.java)
                intent.putExtra("fromLogin",true)
                startActivity(intent)
                toast("Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                toast("Granted1")

                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            else -> {
                toast("Granted2")
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }

        }


    }

    private fun textAutoCheck() {



        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (emailEt.text.isEmpty()){
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                    emailError.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                    emailError.visibility = View.GONE
                }
            }
        })

        passEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (passEt.text.isEmpty()){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (passEt.text.length > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                passwordError.visibility = View.GONE
                if (count > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)

                }
            }
        })



    }

    private fun checkInput() {
      //  progressBar.visibility = VISIBLE

//        if (emailEt.text.isEmpty()){
//            emailError.visibility = View.VISIBLE
//            emailError.text = "UserId Can't be Empty"
//            return
//        }
//        else{
        var clientWriteKey:String
            signInEmail = emailEt.text.toString().trim()
            signInPassword = passEt.text.toString().trim()
        var userData:HashMap<String,Any> = HashMap<String,Any>()

        if (!signInEmail.isEmpty())
        {
            userData.put("userId", signInEmail)
        }else{
            CustomerGlu.getInstance().allowAnonymousRegistration(true)
        }


            userData.put("firebaseToken", fcmToken)
            if (isDemoApp)
            {
                clientWriteKey = Constants.sandbox_key
            }else
            {
                clientWriteKey = writeKey.text.toString()
                CustomerGlu.setWriteKey(clientWriteKey)

            }
       // userData.put("writeKey",clientWriteKey)

        var language = Prefs.getKey(applicationContext,"language")
        if (language.isEmpty())
        {
            language = "english"
        }
        var customAttributes:HashMap<String,Any> = HashMap<String,Any>()
        customAttributes.put("tag",signInPassword)
        customAttributes.put("language",language)
        userData.put("customAttributes",customAttributes);

        if (!isDemoApp && writeKey.text.toString().trim().isEmpty())
        {
                toast("Please Enter WriteKey")
            return

        }

            Prefs.putKey(applicationContext,"writeKey",clientWriteKey)
//            CustomerGlu.getInstance().initializeSdk(applicationContext)
       CustomerGluManager.initializeSDK(applicationContext, debugMode = true)

       //    CustomerGluManager.registerUser(context = applicationContext,userData)
            CustomerGlu.getInstance()
                .registerDevice(applicationContext, userData, object : DataListner {
                    override fun onSuccess(registerModal: RegisterModal) {
                        CustomerGlu.getInstance().gluSDKDebuggingMode(applicationContext, true)
                        //    progressBar.visibility = GONE
                        intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        // toast("Registered")
                        Prefs.putKey(
                            applicationContext,
                            "userId",
                            registerModal.data.getUser().userId
                        )

                        startActivity(intent)
                        finish()
                    }

                    override fun onFail(message: String) {
                        toast(message)
                        progressBar.visibility = GONE
                    }
                })





//        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
//            emailError.visibility = View.VISIBLE
//            emailError.text = "Enter Valid Email"
//            return
//        }
//        if(passEt.text.isEmpty()){
//            passwordError.visibility = View.VISIBLE
//            passwordError.text = "Password Can't be Empty"
//            return
//        }
//
//        if ( passEt.text.isNotEmpty() && emailEt.text.isNotEmpty()){
//            emailError.visibility = View.GONE
//            passwordError.visibility = View.GONE
//            signInUser()
//        }
    }


    private fun signInUser() {

     //   loadingDialog.startLoadingDialog()


            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {

                      //  loadingDialog.dismissDialog()
                        toast("signed in successfully")
                        finish()

                        /*
                        if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
                            startActivity(Intent(this, HomeActivity::class.java))
                            loadingDialog.dismissDialog()
                            toast("signed in successfully")
                            finish()
                        }
                        else {
                            loadingDialog.dismissDialog()
                            val intent = Intent(this, EmailVerifyActivity::class.java)
                            intent.putExtra("EmailAddress", emailEt.text.toString().trim())
                            intent.putExtra("loginPassword", passEt.text.toString().trim())
                            startActivity(intent)
                        }

                        */

                    } else {
                        toast("sign in failed")
                        loadingDialog.dismissDialog()
                    }
                }
        }



}