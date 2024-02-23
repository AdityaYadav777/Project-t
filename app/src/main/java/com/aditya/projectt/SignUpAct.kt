package com.aditya.projectt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aditya.projectt.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpAct : AppCompatActivity() {
    private  lateinit var binding:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.gotoLogin.setOnClickListener {
            startActivity(Intent(this,LoginAct::class.java))

        }

        binding.signUpBtn.setOnClickListener {
//            val name = binding.getMyName.text.toString().trim()
            val email = binding.getMyEmail.text.toString().trim()
            val pass = binding.getMyPassword.text.toString().trim()
            val passConfirm = binding.getMyConfirmPassword.text.toString().trim()




            if (pass != passConfirm) {
                binding.getMyPassword.setError("Password Did not Match")
                binding.getMyConfirmPassword.setError("Password Did not Match")

            } else {


                if (checkData() == 1) {
                    binding.signUpBtn.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE



                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                binding.signUpBtn.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                saveData()
                                updateUI()

                            } else {
                                binding.signUpBtn.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }

                }


            }
        }
    }

    private fun saveData() {
        val db = Firebase.firestore
        val myUid=FirebaseAuth.getInstance().uid.toString()
        val name=binding.getMyName.text.toString().trim()
        val email=binding.getMyEmail.text.toString().trim()
        val pass=binding.getMyPassword.text.toString().trim()



        val data = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to pass
        )


        db.collection("Users").document(myUid).set(data).addOnCompleteListener {



        }.addOnFailureListener {

        }


    }

    private fun updateUI() {

startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    fun checkData():Int{

        if(binding.getMyName.text.isEmpty()){
            binding.getMyName.setError("Enter Name")
            return 0
        }
        if(binding.getMyEmail.text.isEmpty()){
            binding.getMyEmail.setError("Enter Email")
            return 0
        }
        if(binding.getMyPassword.text.isEmpty()){
            binding.getMyPassword.setError("Enter Password")
            return 0
        }
        if(binding.confirmPasswordPlaceholder.text.isEmpty()){
            binding.getMyConfirmPassword.setError("Enter Confirm Password")
            return 0
        }


        return 1
    }


}