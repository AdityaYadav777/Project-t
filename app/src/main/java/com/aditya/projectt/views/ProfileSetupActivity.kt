package com.aditya.projectt.views

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.databinding.ActivityProfileSetupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class ProfileSetupActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileSetupBinding
     var myUri:Uri?=null
var meraKam:String?=null
    var myUrl:String?=null
    var tempProfession:String?=null
    var count:Int?=null
    private fun checkData(): Int {

        if(binding.proName1.text!!.isEmpty()){
            binding.proName1.error="Enter Name"
            return 1
        }
        if(binding.proProfession1.text!!.isEmpty()){
            binding.proProfession1.error="Enter Profession"
            return 1
        }

        if(binding.proPrePrice1.text!!.isEmpty()){
            binding.proPrePrice1.error="Enter Your Pre Price"
            return 1
        }


        if(binding.oneDayPrice1.text!!.isEmpty()){
            binding.oneDayPrice1.error="Enter One Day Price"
            return 1
        }

        if(binding.proExperience1.text!!.isEmpty()){
            binding.proExperience1.error="Enter Experience"
            return 1
        }


        if(binding.proAge1.text!!.isEmpty()){
            binding.proAge1.error="Enter Age"
            return 1
        }


        if(binding.proPlace1.text!!.isEmpty()){
            binding.proPlace1.error="Enter Your Place"
            return 1
        }

        return  0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore
        var storageRef = Firebase.storage.reference
binding.backBtn.setOnClickListener {
   onBackPressed()
}
val profession= listOf("Plumber","Painter","Electrician","Rajmistri","Carpenter","Home Cleaner","Beauty","Ac Repair","Ro-Service","Mobile Repair","Laptop Repair","Developer","Wiring","Labour","Rasoeya")

        val proAdapter= ArrayAdapter(this,R.layout.item_view_list,profession)
        binding.proProfession1.setAdapter(proAdapter)

        binding.proProfession1.onItemClickListener= AdapterView.OnItemClickListener{
                parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val itemSeletct= parent?.getItemAtPosition(position)
meraKam=itemSeletct.toString()
      Toast.makeText(this,"${itemSeletct}",Toast.LENGTH_SHORT).show()

        }


changeStatusBarColor()
        binding.addPhoto.setOnClickListener {
            uploadImg()
        }

        binding.profileImage.setOnClickListener {
            uploadImg()
        }

        binding.progressBar5.visibility = View.VISIBLE

        binding.saveBtn.setOnClickListener {
            if (checkData() == 0) {
                val name = binding.proName1.text.toString()
                val prof = binding.proProfession1.text.toString()
                val prePrise = binding.proPrePrice1.text.toString()
                val oneDay = binding.oneDayPrice1.text.toString()
                val experi = binding.proExperience1.text.toString()
                val age = binding.proAge1.text.toString()
                val place = binding.proPlace1.text.toString()

                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Wait")
                progressDialog.show()
                if (count == 1) {
                    val sd = getFileName(applicationContext, myUri!!)
                    storageRef.child("Profile/$sd").putFile(myUri!!).addOnSuccessListener {
                        storageRef.child("Profile/$sd").downloadUrl.addOnSuccessListener {
                            myUrl = it.toString()
                            val data = hashMapOf(
                                "name" to name,
                                "profession" to prof,
                                "url" to myUrl,
                                "prePrice" to prePrise,
                                "oneDayPrice" to oneDay,
                                "experience" to experi,
                                "age" to age,
                                "place" to place,
                                "uid" to FirebaseAuth.getInstance().currentUser?.uid
                            )
                            db.collection(tempProfession.toString()).document(FirebaseAuth.getInstance().currentUser!!.uid).delete()
                            db.collection(meraKam.toString()).document(FirebaseAuth.getInstance().currentUser!!.uid).set(data).addOnCompleteListener {

                                if(it.isSuccessful){
                                    Toast.makeText(this,"Profile Added",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(this,"Khab hai kuch dekh lo",Toast.LENGTH_SHORT).show()
                                }
                            }
                            db.collection("UserData")
                                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .set(data)
                                .addOnCompleteListener {
                                    db.collection("Users")
                                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                        .update("name", name).addOnCompleteListener {



                                            if (it.isSuccessful) {

                                                progressDialog.dismiss()
                                                Toast.makeText(this, "Done", Toast.LENGTH_SHORT)
                                                    .show()
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    )
                                                )

                                            } else {

                                                progressDialog.dismiss()
                                                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                        }
                                }
                        }
                    }
                } else {

                    val data = hashMapOf(
                        "name" to name,
                        "profession" to prof,
                        "url" to myUrl,
                        "prePrice" to prePrise,
                        "oneDayPrice" to oneDay,
                        "experience" to experi,
                        "age" to age,
                        "place" to place,
                        "uid" to FirebaseAuth.getInstance().currentUser?.uid
                    )
                    db.collection(tempProfession.toString()).document(FirebaseAuth.getInstance().currentUser!!.uid).delete()
                    db.collection(meraKam.toString()).document(FirebaseAuth.getInstance().currentUser!!.uid).set(data).addOnCompleteListener {

                        if(it.isSuccessful){
                            Toast.makeText(this,"Profile Added",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Khab hai kuch dekh lo",Toast.LENGTH_SHORT).show()
                        }
                    db.collection("UserData")
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .set(data)
                        .addOnCompleteListener {
                            db.collection("Users")
                                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .update("name", name).addOnCompleteListener {
                                    if (it.isSuccessful) {

                                        progressDialog.dismiss()
                                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT)
                                            .show()
                                        startActivity(
                                            Intent(
                                                this,
                                                MainActivity::class.java
                                            )
                                        )

                                    } else {

                                        progressDialog.dismiss()
                                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }

                        }
                }}

            } else {

            }

        }


        db.collection("UserData").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.get("name")
                    val pro = document.get("profession")
                    val age = document.get("age")
                    val url = document.get("url")
                    val prePrise = document.get("prePrice")
                    val place = document.get("place")
                    val oneDay = document.get("oneDayPrice")
                    val exp = document.get("experience")
tempProfession=pro.toString()
                    if (name == null || pro == null || age == null || url == null || prePrise == null || place == null || oneDay == null || exp == null) {
                        binding.proName1.setText("")
                        binding.proPlace1.setText("")
                        binding.proAge1.setText("")
                        binding.proProfession1.setText("")
                        binding.progressBar5.visibility = View.GONE
                        binding.proPrePrice1.setText("")
                        binding.oneDayPrice1.setText("")
                        binding.proExperience1.setText("")
                    } else {
                        binding.proName1.setText(name.toString())
                        binding.proPlace1.setText(place.toString())
                        binding.proAge1.setText(age.toString())
//                        binding.proProfession1.setText(pro?.toString())
                        binding.profileImage.load(url)
                        myUrl = url.toString()
                        binding.progressBar5.visibility = View.GONE
                        binding.proPrePrice1.setText(prePrise.toString())
                        binding.oneDayPrice1.setText(oneDay.toString())
                        binding.proExperience1.setText(exp.toString())
                    }

                } else {

                }
            }
            .addOnFailureListener { exception ->

            }







    }
    private fun uploadImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 123)
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
            count=1
            binding.profileImage.setImageURI(selectedImageUri)

        }
    }
    private fun changeStatusBarColor() {
        val window = this.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.red)
        window?.decorView?.let {
            WindowCompat.getInsetsController(window, it).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }
}