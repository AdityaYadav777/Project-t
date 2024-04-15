package com.aditya.projectt.feedData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R

class commentAdapter(val view: View, val listData: ArrayList<commentData>) :RecyclerView.Adapter<commentAdapter.myViewHolder>() {

    class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val profile=itemView.findViewById<ImageView>(R.id.profile)
        val name=itemView.findViewById<TextView>(R.id.namePro)
        val comment=itemView.findViewById<TextView>(R.id.textComment)
        val date=itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

   return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comments,parent,false)
   )  }

    override fun getItemCount(): Int {
       return listData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.comment.text= listData[position].comment
        holder.name.text=listData[position].name
        holder.profile.load(listData[position].profile)
        holder.date.text=listData[position].date


    }
}