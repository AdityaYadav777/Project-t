package com.aditya.projectt.views

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.projectt.CategoriesListDetailsAdapter.adapterDetails
import com.aditya.projectt.R
import com.aditya.projectt.databinding.ActivityListCategoriesDetailsBinding
import com.aditya.projectt.myHomeData.homeData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ListCategoriesDetails : AppCompatActivity() {
    lateinit var binding:ActivityListCategoriesDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityListCategoriesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name=intent.getStringExtra("name")
        val db=Firebase.firestore
        db.collection(name.toString()).addSnapshotListener { value, error ->

            val listDataDetails= arrayListOf<homeData>()

            val data=value?.toObjects(homeData::class.java)

            listDataDetails.addAll(data!!)
            binding.myRec.adapter= adapterDetails(this,listDataDetails)
            binding.shimmer.visibility= View.GONE
            binding.myRec.visibility=View.VISIBLE
            binding.myRec.layoutManager= LinearLayoutManager(this)


        }
    }
}