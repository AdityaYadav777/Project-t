package com.aditya.projectt.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.databinding.FragmentProfileBinding
import com.aditya.projectt.views.ProfileSetupActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class profileFrag : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        val db = Firebase.firestore
        val myUid = FirebaseAuth.getInstance().uid.toString()


        binding.edtProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileSetupActivity::class.java))
        }

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

        db.collection("UserData").document(FirebaseAuth.getInstance().currentUser!!.uid).get() .addOnSuccessListener { document ->
            if (document != null) {
                val name= document.get("name")
                val pro=   document.get("profession")
                val age= document.get("age")
                val url=  document.get("url")
                val prePrise= document.get("prePrice")
                val place= document.get("place")
                val oneDay= document.get("oneDayPrice")
                val exp= document.get("experience")


                if(name== null || pro==null || age==null||prePrise==null || oneDay==null||exp==null){

                    binding.proName.text=name.toString().uppercase()
                    binding.proType.text=""
                    binding.proPrePrice.text=""
                    binding.proOneDay.text=""
                    binding.proExperience.text=""
                }else{
                    binding.proName.text=name.toString().uppercase()
                    binding.proType.text=pro.toString()
                    binding.profileImage.load(url)
                    binding.proPrePrice.text=prePrise.toString()
                    binding.proOneDay.text=oneDay.toString()
                    binding.proExperience.text=exp.toString()
                }



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
binding.myMenu.setOnClickListener {
    val dialog = BottomSheetDialog(requireContext())
    val view = layoutInflater.inflate(R.layout.bottom_profile_data, null)
    dialog.setContentView(view)

    dialog.show()
}





        return binding.root
    }


    private fun swapFragment(fragment: Fragment): Boolean {

        val manager = fragmentManager?.beginTransaction()
        manager?.replace(R.id.proFrame, fragment)?.detach(fragment)?.attach(fragment)
        manager?.commit()


  return true }


}