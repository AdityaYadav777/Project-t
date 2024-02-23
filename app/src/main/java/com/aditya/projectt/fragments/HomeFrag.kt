package com.aditya.projectt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.aditya.projectt.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class HomeFrag : Fragment() {
lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        val db = Firebase.firestore
        val myUid= FirebaseAuth.getInstance().uid.toString()
        val docRef = db.collection("Users").document(myUid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                  binding.myNameInHome.text=document.get("name").toString()
                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
        binding.plumberCate.load("https://image.freepik.com/free-vector/plumber-service-logo_90508-69.jpg")
        binding.mistriCate.load("https://www.clipartkey.com/mpngs/m/81-816870_child-labour-icon.png")
        binding.labourCate.load("https://th.bing.com/th/id/R.fcb3d90607958771b0efb7b477bf871c?rik=qZgbpIyGkxaKmw&riu=http%3a%2f%2fclipart-library.com%2fimages_k%2fconstruction-worker-silhouette-vector%2fconstruction-worker-silhouette-vector-3.png&ehk=QsZQw6u248Qe3WYYKKN7A8%2fpuYnijauZpmHeDBKHqtM%3d&risl=&pid=ImgRaw&r=0")
        binding.painterCate.load("https://th.bing.com/th/id/R.f39065edc685e449103ae29352fdeeca?rik=zJHYVURJmz1Z9w&riu=http%3a%2f%2fthumbs.dreamstime.com%2fz%2fcartoon-house-painter-logo-18336494.jpg&ehk=q4YsItVeQ3ZbIuEcVf75b5WNOXcwdeaPygLVOHYzuD0%3d&risl=&pid=ImgRaw&r=0")
        binding.AcRepaireCate.load("https://th.bing.com/th/id/OIP.44NkEiwFQQCbBMltXI_SeAHaHa?w=612&h=612&rs=1&pid=ImgDetMain")
        return binding.root
    }

    }
