package com.aditya.projectt.views

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.databinding.ActivityProfileDashBinding
import com.aditya.projectt.viewPagerAdapter.fragmentPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType


class ProfileDashAct : AppCompatActivity() {
    lateinit var binding:ActivityProfileDashBinding
    lateinit var adapter: fragmentPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityProfileDashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBarColor()
        val uid=intent.getStringExtra("uid")
        getDataFromDB(uid)
        adapter= fragmentPageAdapter(supportFragmentManager,lifecycle,uid)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Work"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Feed"))
        binding.viewPager2.adapter=adapter
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                binding.viewPager2.currentItem=p0!!.position
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }

        })





        }
var myName:String?=null
    private fun getDataFromDB(uid: String?) {
        val db=Firebase.firestore
        db.collection("UserData").document(uid.toString()).get().addOnSuccessListener {
            binding.ImageView.load(it.get("url").toString())
            binding.myName.text=it.get("name").toString()
            myName=it.get("name").toString()
            binding.typeWork.text=it.get("profession").toString()
            binding.prePrice.text="${it.get("prePrice").toString()} Rs"
            binding.exp.text="${it.get("experience").toString()}"
            binding.oneDay.text="${it.get("oneDayPrice").toString()} Rs"
            binding.myProData.text="Age: ${it.get("age").toString()} year\t\t\t\t\t\t\t\t\t\t\t\t\t\tPlace: ${it.get("place").toString()}"


            binding.chatBtn.setOnClickListener {
//
                startSingleChat(uid.toString())
            }
        }

    }



    private fun startSingleChat(userId: String) {
        ZIMKitRouter.toMessageActivity(
this, userId, ZIMKitConversationType.ZIMKitConversationTypePeer
        )
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
