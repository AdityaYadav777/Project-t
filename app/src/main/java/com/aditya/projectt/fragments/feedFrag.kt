package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.projectt.databinding.FragmentFeedBinding
import com.aditya.projectt.feedData.myFeedAdapter
import com.aditya.projectt.feedData.myFeedData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class feedFrag : Fragment() {

lateinit var binding:FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=FragmentFeedBinding.inflate(layoutInflater,container,false)
        val db=Firebase.firestore



        db.collection("FeedPost").addSnapshotListener { value, error ->

            val listOfFeeds= arrayListOf<myFeedData>()
            val data=value?.toObjects(myFeedData::class.java)
            listOfFeeds.addAll(data!!)

if (isAdded) {


    binding.myFeedRec.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.myFeedRec.adapter = myFeedAdapter(requireContext(), listOfFeeds)
}
        }


        return binding.root
    }


}

