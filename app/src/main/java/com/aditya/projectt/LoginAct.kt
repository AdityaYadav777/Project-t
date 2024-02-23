package com.aditya.projectt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aditya.projectt.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LoginAct : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
      val  auth = Firebase.auth

      binding.gotoSingUp.setOnClickListener {
          startActivity(Intent(this,SignUpAct::class.java))
      }


        binding.loginBtn.setOnClickListener {

            val logEmail=binding.logEmail.text.toString()
            val logPass=binding.logPass.text.toString()


            if(checkData()==1){
             binding.loginBtn.visibility= View.GONE
                binding.progressBar2.visibility=View.VISIBLE
                auth.signInWithEmailAndPassword(logEmail,logPass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            binding.loginBtn.visibility= View.VISIBLE
                            binding.progressBar2.visibility=View.GONE
                            updateUI()

                        } else {
                            binding.loginBtn.visibility= View.VISIBLE
                            binding.progressBar2.visibility=View.GONE

                        }
                    }

            }

        }



    }

    private fun updateUI() {
    startActivity(Intent(this,MainActivity::class.java))
    }

    fun checkData():Int{

        if(binding.logEmail.text.isEmpty()){
            binding.logEmail.setError("Enter Email")
            return 0
        }
        if(binding.logPass.text.isEmpty()){
            binding.logPass.setError("Enter Password!")
            return 0
        }

        return 1
    }

    override fun onStart() {
        super.onStart()
        val auth=Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }


    }


}