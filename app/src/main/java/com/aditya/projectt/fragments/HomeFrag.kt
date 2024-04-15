package com.aditya.projectt.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.databinding.FragmentHomeBinding
import com.aditya.projectt.myHomeData.homeData
import com.aditya.projectt.myHomeData.myHomeAdapter
import com.aditya.projectt.views.ListCategoriesDetails
import com.aditya.projectt.views.ShowAllCateActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore


class HomeFrag : androidx.fragment.app.Fragment() {
    lateinit var binding: FragmentHomeBinding
     var  prof:String?=null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

changeStatusBarColor()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


                val db = Firebase.firestore
                val myUid = FirebaseAuth.getInstance().uid.toString()
                val docRef = db.collection("Users").document(myUid)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val url=document.get("url").toString()
                            binding.myNameInHome.text = document.get("name").toString()



                            binding.nameDam.visibility=View.GONE
                            binding.myNameInHome.visibility=View.VISIBLE

                        } else {

                        }
                    }
                    .addOnFailureListener { exception ->

                    }



        db.collection("UserData").document(myUid).get().addOnSuccessListener {

            val url=it.get("url")
            binding.profileImage.load(url)
           prof=it.get("profession").toString()
            if(prof=="null"){
                binding.type.text=""
            }else{
                binding.type.text=prof
            }
            binding.typeDam.visibility=View.GONE
            binding.type.visibility=View.VISIBLE
            binding.proDam.visibility=View.GONE
            binding.profileImage.visibility=View.VISIBLE

        }


        db.collection("UserData").addSnapshotListener { value, error ->
            val listOfUserData = arrayListOf<homeData>()
            val data = value?.toObjects(homeData::class.java)
            listOfUserData.addAll(data!!)
            if (isAdded) {
                binding.myRecHome.layoutManager = LinearLayoutManager(requireContext())
                binding.myRecHome.adapter = myHomeAdapter(requireContext(), listOfUserData)
            }
        }



            val auth = com.google.firebase.ktx.Firebase.auth
            val user = auth.currentUser

            if (user != null) {
                val userName = user.displayName

                binding.myNameInHome.text =userName
            } else {

            }

        binding.seeAll.setOnClickListener {
           val i = Intent(requireContext(), ShowAllCateActivity::class.java)
         requireContext().startActivity(i)
        }

                binding.labourCate.load("https://th.bing.com/th/id/R.fcb3d90607958771b0efb7b477bf871c?rik=qZgbpIyGkxaKmw&riu=http%3a%2f%2fclipart-library.com%2fimages_k%2fconstruction-worker-silhouette-vector%2fconstruction-worker-silhouette-vector-3.png&ehk=QsZQw6u248Qe3WYYKKN7A8%2fpuYnijauZpmHeDBKHqtM%3d&risl=&pid=ImgRaw&r=0")
                binding.painterCate.load("https://th.bing.com/th/id/R.f39065edc685e449103ae29352fdeeca?rik=zJHYVURJmz1Z9w&riu=http%3a%2f%2fthumbs.dreamstime.com%2fz%2fcartoon-house-painter-logo-18336494.jpg&ehk=q4YsItVeQ3ZbIuEcVf75b5WNOXcwdeaPygLVOHYzuD0%3d&risl=&pid=ImgRaw&r=0")
                binding.AcRepaireCate.load("https://th.bing.com/th/id/OIP.44NkEiwFQQCbBMltXI_SeAHaHa?w=612&h=612&rs=1&pid=ImgDetMain")



        binding.labourCate.setOnClickListener {
        val i=Intent(requireContext(),ListCategoriesDetails::class.java)
        i.putExtra("name","Labour")
        startActivity(i)
    }

        binding.painterCate.setOnClickListener {
            val i=Intent(requireContext(),ListCategoriesDetails::class.java)
            i.putExtra("name","Painter")
            startActivity(i)
        }

        binding.AcRepaireCate.setOnClickListener {
            val i=Intent(requireContext(),ListCategoriesDetails::class.java)
            i.putExtra("name","Electrician")
            startActivity(i)
        }




        return binding.root
            }



    private fun changeStatusBarColor() {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)
        window?.decorView?.let {
            WindowCompat.getInsetsController(window, it).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }


    }

