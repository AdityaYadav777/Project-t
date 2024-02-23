package com.aditya.projectt.feedData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R

class myFeedAdapter(val requireContext: Context, val listOfFeeds: ArrayList<myFeedData>) :RecyclerView.Adapter<myFeedAdapter.myViewHolder>() {
    inner class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
val name=itemView.findViewById<TextView>(R.id.myName)
        val type=itemView.findViewById<TextView>(R.id.myType)
        val desc=itemView.findViewById<TextView>(R.id.myDesc)
        val profile=itemView.findViewById<ImageView>(R.id.profile_image)
        val feedImage=itemView.findViewById<ImageView>(R.id.myFeedPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
  return myViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
  )
    }

    override fun getItemCount(): Int {
      return listOfFeeds.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text=listOfFeeds[position].name
        holder.type.text=listOfFeeds[position].type
        holder.desc.text=listOfFeeds[position].desc
        holder.profile.load(listOfFeeds[position].profileUrl)
        holder.feedImage.load(listOfFeeds[position].feedUrl)

    }
}