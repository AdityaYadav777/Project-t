package com.aditya.projectt.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.core.Rive
import com.aditya.projectt.R
import com.aditya.projectt.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth


class LoginAct : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    var stateMachineName="State Machine 1"
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Rive.init(this)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
      val auth = Firebase.auth

        Rive.init(this)
      binding.gotoSingUp.setOnClickListener {
          startActivity(Intent(this, SignUpAct::class.java))
      }



        binding.logEmail.setOnFocusChangeListener{v, b->
            if (b) {

                binding.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = true
                )

            } else{


                binding.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = false
                )

            }
        }


        binding.logPass.setOnFocusChangeListener { v, b ->
            if (b) {

                binding.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "hands_up",
                    value = true
                )

            }
            else{


                binding.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "hands_up",
                    value = false
                )


            }
        }




        binding.logEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginCharacter.controller.setNumberState(stateMachineName,"Look",3*s!!.length.toFloat())

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.logPass.transformationMethod = null


        binding .logPass.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.logPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginCharacter.controller.setNumberState(stateMachineName,"Look",3*s!!.length.toFloat())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })




        binding.loginBtn.setOnClickListener {

            binding.logEmail.clearFocus()
            binding.logPass.clearFocus()
            val logEmail=binding.logEmail.text.toString()
            val logPass=binding.logPass.text.toString()


            if(checkData()==1){
             binding.loginBtn.visibility= View.GONE
                binding.progressBar2.visibility=View.VISIBLE
                auth.signInWithEmailAndPassword(logEmail,logPass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            binding.loginCharacter.controller.fireState(stateMachineName,"success")
                            binding.loginBtn.visibility= View.VISIBLE
                           binding.progressBar2.visibility=View.GONE
                            updateUI()

                        } else {
                            binding.loginCharacter.controller.fireState(stateMachineName,"fail")
                            binding.loginBtn.visibility= View.VISIBLE
                            binding.progressBar2.visibility=View.GONE


                        }
                    }

            }

        }
        val currentUser = auth.currentUser

        if (currentUser != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




        binding.googleBtn.setOnClickListener {
            signIn()
        }



    }

    private fun signIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun updateUI() {
    startActivity(Intent(this, MainActivity::class.java))
    }

    fun checkData():Int{

        if(binding.logEmail.text.isEmpty()){
            binding.logEmail.clearFocus()
            binding.loginCharacter.controller.fireState(stateMachineName,"fail")
            binding.logEmail.error = "Enter Email"

            return 0
        }
        if(binding.logPass.text.isEmpty()){
            binding.logPass.clearFocus()
            binding.loginCharacter.controller.fireState(stateMachineName,"fail")
            binding.logPass.error = "Enter Password!"

            return 0
        }

        return 1
    }

    override fun onStart() {
        super.onStart()
        val auth=Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }


    }


}