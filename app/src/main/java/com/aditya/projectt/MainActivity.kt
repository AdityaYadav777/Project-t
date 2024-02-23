package com.aditya.projectt


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aditya.projectt.databinding.ActivityMainBinding
import com.aditya.projectt.fragments.HomeFrag
import com.aditya.projectt.fragments.chatFrag
import com.aditya.projectt.fragments.feedFrag
import com.aditya.projectt.fragments.profileFrag
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var nabView:BottomNavigationView
//    lateinit var cropImage:
lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
nabView=findViewById(R.id.nav_view)
        swapFragment(HomeFrag())

           nabView.setOnItemSelectedListener {


                when (it.itemId) {
                    R.id.homeFicon ->{ swapFragment(HomeFrag())

                 }
                    R.id.chatFicon ->{ swapFragment(chatFrag())

                        }

                    R.id.feedFicon -> {swapFragment(feedFrag())

                        }
                    R.id.profileFicon ->
                        {

                                swapFragment(profileFrag())


                        }
                    else -> {

                    }

                }
                true


        }

        val data=intent.getStringExtra("key")
        if(data=="01"){
            swapFragment(profileFrag())
        }

        val myData=intent.getStringExtra("feed")
        if(myData=="02"){
            swapFragment(feedFrag())
        }


        binding.addProButton.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

            val btnClose = view.findViewById<TextView>(R.id.upLoadFeed)
            btnClose.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"

                startActivityForResult(intent, 123)

            }

            dialog.setContentView(view)

            dialog.show()

        }
        val db = Firebase.firestore
        val myUid= FirebaseAuth.getInstance().uid.toString()
        val docRef = db.collection("Users").document(myUid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                   name =document.get("name").toString().uppercase()

                } else {

                }
            }
            .addOnFailureListener { exception ->

            }

    }
    private  fun swapFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val manager=fragmentManager.beginTransaction()
        manager.replace(R.id.myFrame,fragment).detach(fragment).attach(fragment)
        manager.commitAllowingStateLoss()
//        manager.commit()




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==123 && resultCode == Activity.RESULT_OK) {
            // Handle the selected image URI (e.g., display it in an ImageView)
            val selectedImageUri = data?.data

            val i =Intent(this,UploadImageFeed::class.java)
            i.putExtra("key",selectedImageUri)
            i.putExtra("name",name)
            i.putExtra("proUrl","https://image.freepik.com/free-vector/plumber-service-logo_90508-69.jpg")
            i.putExtra("type","Don")

           startActivity(i)

        }
    }


}