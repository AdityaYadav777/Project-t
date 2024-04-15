package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aditya.projectt.UserFeedData.userFeedAdapter
import com.aditya.projectt.databinding.FragmentMyFeedBinding
import com.aditya.projectt.feedData.myFeedData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class myFeedFragment : Fragment() {

    lateinit var binding: FragmentMyFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFeedBinding.inflate(layoutInflater, container, false)


        val db = Firebase.firestore

        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .addSnapshotListener { value, error ->

                val listOfFeedData = arrayListOf<myFeedData>()
                val data = value?.toObjects(myFeedData::class.java)
                listOfFeedData.addAll(data!!)
                if (isAdded) {
                        binding.myFeedRec.adapter =userFeedAdapter(requireContext(), listOfFeedData)
                        binding.myFeedRec.layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

                }

            }

        return binding.root
    }


}