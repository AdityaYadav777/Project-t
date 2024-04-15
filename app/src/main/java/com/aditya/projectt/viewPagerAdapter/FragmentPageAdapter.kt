package com.aditya.projectt.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aditya.projectt.fragments.FirstWorkFagment
import com.aditya.projectt.fragments.SecondFeedFragment

class fragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val uid: String?):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
     return   if(position==0){
            FirstWorkFagment(uid)
        }else{
            SecondFeedFragment(uid)
        }
    }
}