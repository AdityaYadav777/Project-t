package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aditya.projectt.databinding.FragmentChatBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.entity.ZIMError
import im.zego.zim.enums.ZIMErrorCode


class chatFrag : Fragment() {
    lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        val db = Firebase.firestore
        db.collection("UserData").document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnSuccessListener {
            val uid = it.get("uid").toString()
            val name = it.get("name").toString()
            connectUser(uid, name, "")

        }





        return binding.root
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