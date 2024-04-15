package com.aditya.projectt.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aditya.projectt.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging

class SignUpAct : AppCompatActivity() {
    private  lateinit var binding:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.gotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginAct::class.java))

        }

        binding.signUpBtn.setOnClickListener {

            val email = binding.getMyEmail.text.toString().trim()
            val pass = binding.getMyPassword.text.toString().trim()
            val passConfirm = binding.getMyConfirmPassword.text.toString().trim()




            if (pass != passConfirm) {
                binding.getMyPassword.error = "Password Did not Match"
                binding.getMyConfirmPassword.error = "Password Did not Match"

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
//                                updateUI()

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


FirebaseMessaging.getInstance().token.addOnCompleteListener ( OnCompleteListener {task->


  val  fcmToken=task.result

    val db = Firebase.firestore
    val myUid=FirebaseAuth.getInstance().uid.toString()
    val name=binding.getMyName.text.toString().trim()
    val email=binding.getMyEmail.text.toString().trim()
    val pass=binding.getMyPassword.text.toString().trim()



    val data = hashMapOf(
        "name" to name,
        "email" to email,
        "password" to pass,
        "tokenId" to fcmToken
    )

    db.collection("Users").document(myUid).set(data).addOnCompleteListener {

        if(it.isSuccessful){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

})




    }

    private fun updateUI() {

startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun checkData():Int{

        if(binding.getMyName.text.isEmpty()){
            binding.getMyName.error = "Enter Name"
            return 0
        }
        if(binding.getMyEmail.text.isEmpty()){
            binding.getMyEmail.error = "Enter Email"
            return 0
        }
        if(binding.getMyPassword.text.isEmpty()){
            binding.getMyPassword.error = "Enter Password"
            return 0
        }
        if(binding.confirmPasswordPlaceholder.text.isEmpty()){
            binding.getMyConfirmPassword.error = "Enter Confirm Password"
            return 0
        }


        return 1
    }


}