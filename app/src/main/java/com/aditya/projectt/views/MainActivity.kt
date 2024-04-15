package com.aditya.projectt.views


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aditya.projectt.R
import com.aditya.projectt.databinding.ActivityMainBinding
import com.aditya.projectt.fragments.HomeFrag
import com.aditya.projectt.fragments.chatFrag
import com.aditya.projectt.fragments.feedFrag
import com.aditya.projectt.fragments.profileFrag
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.entity.ZIMError
import im.zego.zim.enums.ZIMErrorCode
import im.zego.zpns.util.ZPNsConfig


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isStoragePermission = false
    private lateinit var db: FirebaseFirestore
    private lateinit var url: String
    lateinit var name: String


    val appId = 234860137.toLong()
    val appSign = "fd1908624ae7494ebc3767649ffe324c705ef17303697ecec5be4c112fec0dbb"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inintZegoCloud()
        swapFragment(HomeFrag())
        db = Firebase.firestore


        db.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnSuccessListener {

            var nameIc = it.get("name").toString()
            db.collection("UserData").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
                .addOnSuccessListener {
                    url = it.get("url").toString()
                    connectUser(FirebaseAuth.getInstance().currentUser?.uid.toString(), nameIc, url)
                }


        }






        binding.myNav.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.homeFicon -> {
                    swapFragment(HomeFrag())

                }

                R.id.chatFicon -> {
                    swapFragment(chatFrag())

                }

                R.id.feedFicon -> {
                    swapFragment(feedFrag())

                }

                R.id.profileFicon -> {

                    swapFragment(profileFrag())


                }

                else -> {

                }

            }
            true


        }

        val data = intent.getStringExtra("key")
        if (data == "01") {
            swapFragment(profileFrag())
        }

        val myData = intent.getStringExtra("feed")
        if (myData == "02") {
            swapFragment(feedFrag())
        }


        binding.addProButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

            val uploadFeed = view.findViewById<TextView>(R.id.upLoadFeed)
            uploadFeed.setOnClickListener {

                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"


                if (intent.resolveActivity(packageManager) != null) {

                    startActivityForResult(intent, 123)

                } else {
                    Toast.makeText(this, "Somthing wrong", Toast.LENGTH_SHORT).show()
                }


            }

            dialog.setContentView(view)

            dialog.show()

        }
        val db = Firebase.firestore
        val myUid = FirebaseAuth.getInstance().uid.toString()
        val docRef = db.collection("Users").document(myUid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    name = document.get("name").toString().uppercase()

                } else {

                }
            }
            .addOnFailureListener { exception ->

            }

    }


    private fun swapFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val manager = fragmentManager.beginTransaction()
        manager.replace(R.id.myFrame, fragment)
        manager.detach(fragment)
        manager.attach(fragment)
        manager.commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            val i = Intent(this, UploadImageFeed::class.java)
            i.putExtra("key", selectedImageUri)
            i.putExtra("name", name)
            i.putExtra("proUrl", url)
            i.putExtra("type", "Don")
            i.putExtra("sKey", "fromMain")
            startActivity(i)

        }
    }


    private fun inintZegoCloud() {
        ZIMKit.initWith(this.application, appId, appSign)
        ZIMKit.initNotifications()
        val zpnsConfig = ZPNsConfig()
        zpnsConfig.enableFCMPush =
            true // Enable the Google push notification channel. After it is enabled, the notification channels of other vendors won't be available.

//        zpnsManager.setPushConfig(zpnsConfig)
    }


    fun connectUser(userId: String?, userName: String?, userAvatar: String?) {
        // Logs in.
        ZIMKit.connectUser(
            userId, userName, userAvatar
        ) { errorInfo: ZIMError ->
            if (errorInfo.code == ZIMErrorCode.SUCCESS) {

            } else {
            }
        }
    }


}