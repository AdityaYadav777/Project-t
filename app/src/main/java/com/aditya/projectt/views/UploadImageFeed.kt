package com.aditya.projectt.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aditya.projectt.databinding.ActivityUploadImageFeedBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class UploadImageFeed : AppCompatActivity() {
    lateinit var binding: ActivityUploadImageFeedBinding
    var myUri: Uri? = null
    var i = 0
    var likes= ArrayList<String>()
    var likeCount:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var storageRef = Firebase.storage.reference
        val db = Firebase.firestore
        val getKey = intent.getStringExtra("sKey")
        if (getKey == "fromMain") {

            var uri: Uri? = intent.getParcelableExtra("key")
            myUri = uri
            binding.imgView.setImageURI(myUri)

            binding.editBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 123)
            }

            binding.backBtn.setOnClickListener {
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("key", "01")
                startActivity(i)

            }
            val name = intent.getStringExtra("name")
            val proUrl = intent.getStringExtra("proUrl")
            val type = intent.getStringExtra("type")
            val sd = getFileName(applicationContext, uri!!)
            binding.shareBtn.setOnClickListener {

                val myData = storageRef.child("Img/$sd").putFile(myUri!!)

                binding.progressBar3.visibility = View.VISIBLE
                binding.shareBtn.visibility = View.GONE
                myData.addOnSuccessListener {
                    Toast.makeText(this, "Ho gya ho", Toast.LENGTH_SHORT).show()
                    storageRef.child("Img/$sd").downloadUrl.addOnSuccessListener {
                        val docId =
                            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                                .document().id

                        likeCount="0"
                        val data = hashMapOf(
                            "feedUrl" to it.toString(),
                            "desc" to binding.getText.text.toString(),
                            "name" to name,
                            "profileUrl" to proUrl,
                            "type" to type,
                            "docId" to docId,
                            "uid" to FirebaseAuth.getInstance().currentUser?.uid.toString(),
                            "likeIds" to likes,
                            "likes" to likeCount

                        )

                        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                            .document(docId).set(data).addOnSuccessListener {
                                Firebase.firestore.collection("FeedPost").document(docId).set(data)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                    }
                                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                binding.progressBar3.visibility = View.GONE
                                binding.shareBtn.visibility = View.VISIBLE

                                onBackPressed()
                                finish()

                            }.addOnFailureListener {
                                Toast.makeText(this, "Error Hai Kuch", Toast.LENGTH_SHORT).show()
                                binding.shareBtn.visibility = View.VISIBLE
                            }
                    }
                }
            }
        }
        if (getKey == "fromFeed") {

            val getDocId = intent.getStringExtra("getDocID")
            val url = intent.getStringExtra("getUri")
            val descText = intent.getStringExtra("desc")

            binding.imgView.load(url)
            binding.getText.setText(descText)

            binding.editBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 123)
            }

            binding.shareBtn.setOnClickListener {

                binding.progressBar3.visibility = View.VISIBLE
                binding.shareBtn.visibility = View.GONE
                val getText = binding.getText.text.toString()

                if (i != 0) {
                    val sd = getFileName(applicationContext, myUri!!)
                    val myData = storageRef.child("Img/$sd").putFile(myUri!!)
                    myData.addOnSuccessListener {
                        storageRef.child("Img/$sd").downloadUrl.addOnSuccessListener {
                            val myUrl = it.toString()
                            db.collection("FeedPost").document(getDocId!!)
                                .update("feedUrl", it.toString()).addOnCompleteListener {
                                    db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                                        .document(getDocId).update("feedUrl", myUrl)
                                    binding.progressBar3.visibility = View.GONE
                                    binding.shareBtn.visibility = View.VISIBLE
                                }
                        }
                    }
                }
                db.collection("FeedPost").document(getDocId!!).update("desc", getText)
                    .addOnCompleteListener {

                        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                            .document(getDocId).update("desc", getText).addOnCompleteListener {

                                if (it.isSuccessful) {
                                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                    binding.progressBar3.visibility = View.GONE
                                    binding.shareBtn.visibility = View.VISIBLE

                                }
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
                    if (cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let {
            uri.path?.substring(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            myUri = selectedImageUri!!
            i = 1
            binding.imgView.setImageURI(selectedImageUri)
        }
    }
}