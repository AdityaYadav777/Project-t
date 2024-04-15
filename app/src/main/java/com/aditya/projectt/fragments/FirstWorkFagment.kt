package com.aditya.projectt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aditya.projectt.databinding.FragmentFirstWorkFagmentBinding


class FirstWorkFagment(uid: String?) : Fragment() {

lateinit var binding:FragmentFirstWorkFagmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentFirstWorkFagmentBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment



        return binding.root
    }



}