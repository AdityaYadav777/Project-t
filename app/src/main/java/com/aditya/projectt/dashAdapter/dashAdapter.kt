package com.aditya.projectt.dashAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.views.ImagePreviewScreen

class dashAdapter(val requireContext: Context, val listOfFeedData: ArrayList<dashData>) :RecyclerView.Adapter<dashAdapter.myViewHolder>() {
    inner class myViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val img=itemView.findViewById<ImageView>(R.id.feedImg)
        val dot=itemView.findViewById<ImageView>(R.id.feedDots)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_load_my_feed,parent,false))
    }

    override fun getItemCount(): Int {
       return listOfFeedData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
  holder.img.load(listOfFeedData[position].feedUrl)
        holder.dot.visibility=View.GONE

        holder.img.setOnClickListener {
            val i=Intent(requireContext,ImagePreviewScreen::class.java)
            i.putExtra("url",listOfFeedData[position].feedUrl)
            requireContext.startActivity(i)
        }
    }
}