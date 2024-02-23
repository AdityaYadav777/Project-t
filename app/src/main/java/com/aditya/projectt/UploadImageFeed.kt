package com.aditya.projectt

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aditya.projectt.databinding.ActivityUploadImageFeedBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class UploadImageFeed : AppCompatActivity() {
    lateinit var binding:ActivityUploadImageFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUploadImageFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var storageRef = Firebase.storage.reference
        val uri:Uri?=intent.getParcelableExtra("key")
        binding.imgView.setImageURI(uri)

        binding.backBtn.setOnClickListener {
      val i=Intent(this,MainActivity::class.java)
            i.putExtra("key","01")
            startActivity(i)

        }
        val name=intent.getStringExtra("name")
        val proUrl=intent.getStringExtra("proUrl")
        val type=intent.getStringExtra("type")
        val sd = getFileName(applicationContext, uri!!)
        binding.shareBtn.setOnClickListener {
            val myData = storageRef.child("Img/$sd").putFile(uri!!)
            binding.progressBar3.visibility=View.VISIBLE
            binding.shareBtn.visibility=View.GONE
            myData.addOnSuccessListener {
                Toast.makeText(this,"Ho gya ho",Toast.LENGTH_SHORT).show()
                storageRef.child("Img/$sd").downloadUrl.addOnSuccessListener {
                        val data= hashMapOf(
                            "feedUrl" to it.toString(),
                            "desc" to binding.getText.text.toString(),
                            "name" to name,
                            "profileUrl" to proUrl,
                            "type" to type
                        )

                    Firebase.firestore.collection("FeedPost").document().set(data).addOnSuccessListener {
                        Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
                        binding.progressBar3.visibility=View.GONE
                        binding.shareBtn.visibility=View.VISIBLE
                       val i=Intent(this,MainActivity::class.java)
                        i.putExtra("feed","02")
                        startActivity(i)
                        finish()

                    }.addOnFailureListener {
                        Toast.makeText(this,"Error Hai Kuch",Toast.LENGTH_SHORT).show()
                        binding.shareBtn.visibility=View.VISIBLE
                    }
                }




            }
        }






    }


    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

}