package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aditya.projectt.dashAdapter.dashAdapter
import com.aditya.projectt.dashAdapter.dashData
import com.aditya.projectt.databinding.FragmentSecondFeedBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class SecondFeedFragment( val uid: String?) : Fragment() {
    lateinit var binding: FragmentSecondFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondFeedBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment


        val db = Firebase.firestore

        db.collection(uid.toString()).addSnapshotListener { value, error ->

            val listOfFeedData = arrayListOf<dashData>()
            val data = value?.toObjects(dashData::class.java)
            listOfFeedData.addAll(data!!)
            if (isAdded) {
                binding.myDashFeedRec.adapter = dashAdapter(requireContext(), listOfFeedData)
                binding.myDashFeedRec.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            }


        }

        return binding.root
    }
}


