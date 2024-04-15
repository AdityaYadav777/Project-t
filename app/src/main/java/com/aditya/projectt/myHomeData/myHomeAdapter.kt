package com.aditya.projectt.myHomeData

import android.content.Context
import android.content.Intent
import android.graphics.Color.GRAY
import android.graphics.Color.RED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.views.ProfileDashAct

class myHomeAdapter(val requireContext: Context, val listOfUserData: ArrayList<homeData>) :
    RecyclerView.Adapter<myHomeAdapter.myViewHolder>() {
    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val url = itemView.findViewById<ImageView>(R.id.myProfileImg)
        val name = itemView.findViewById<TextView>(R.id.myName)
        val title = itemView.findViewById<TextView>(R.id.myTitle)
        val prePrice = itemView.findViewById<TextView>(R.id.prePrice)
        val bookBtn = itemView.findViewById<ImageView>(R.id.bookBtn)
        val myExp = itemView.findViewById<TextView>(R.id.myExp)
        val myItem=itemView.findViewById<RelativeLayout>(R.id.myItem)
        val oneDay=itemView.findViewById<TextView>(R.id.oneDayPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_show, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listOfUserData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.url.load(listOfUserData[position].url)
        holder.name.text = listOfUserData[position].name
        holder.title.text = listOfUserData[position].profession
        holder.prePrice.text = listOfUserData[position].prePrice
        holder.myExp.text = listOfUserData[position].experience
        holder.oneDay.text=listOfUserData[position].oneDayPrice

        val uid=listOfUserData[position].uid


        holder.myItem.setOnClickListener {
            val i =Intent(requireContext, ProfileDashAct::class.java)
            i.putExtra("uid",uid)
            requireContext.startActivity(i)
        }

        var chLike = true
        holder.bookBtn.setOnClickListener {
            if (chLike == true) {
                chLike = false
                holder.bookBtn.setColorFilter(RED)
            } else {
                holder.bookBtn.setColorFilter(GRAY)
                chLike = true
            }

        }
    }
}