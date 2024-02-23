package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class profileFrag : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        val db = Firebase.firestore
        val myUid = FirebaseAuth.getInstance().uid.toString()


        val docRef = db.collection("Users").document(myUid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    binding.proName.text = document.get("name").toString().uppercase()

                } else {

                }
            }
            .addOnFailureListener { exception ->

            }



        swapFragment(myWorkFragment())
        binding.myProTabs.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.compWork -> swapFragment(myWorkFragment())
                R.id.myFeedPost -> swapFragment(myFeedFragment())

                else -> true
            }

        }





        binding.profileImage.load("https://image.freepik.com/free-vector/plumber-service-logo_90508-69.jpg")
        return binding.root
    }


    private fun swapFragment(fragment: Fragment): Boolean {

        val manager = fragmentManager?.beginTransaction()
        manager?.replace(R.id.proFrame, fragment)?.detach(fragment)?.attach(fragment)

       manager?.commit()


  return true }


}