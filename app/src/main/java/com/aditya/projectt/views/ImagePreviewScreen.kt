package com.aditya.projectt.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aditya.projectt.databinding.ActivityImagePreviewScreenBinding

class ImagePreviewScreen : AppCompatActivity() {
    lateinit var binding:ActivityImagePreviewScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityImagePreviewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url=intent.getStringExtra("url")
        binding.myFeedPost.load(url)

        binding.backBtn2.setOnClickListener {
            onBackPressed()

        }

    }
}