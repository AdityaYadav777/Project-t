package com.aditya.projectt.categoriesData

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.views.ListCategoriesDetails
import com.aditya.projectt.views.ShowAllCateActivity

class cateAdapter(val showAllCateActivity: ShowAllCateActivity, private  var  listData: ArrayList<dataCate>) :RecyclerView.Adapter<cateAdapter.myViewHolder>() {

    inner class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.name)
        val photo=itemView.findViewById<ImageView>(R.id.photo)
        val ll=itemView.findViewById<LinearLayout>(R.id.ll)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       return myViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_show_categories,parent,false)
       )
    }

    fun filterList(filterlist: ArrayList<dataCate>) {

        listData = filterlist

        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return listData.size
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
     holder.name.text=listData[position].name
        holder.photo.load(listData[position].url)




        holder.itemView.setOnClickListener {
        val i = Intent(showAllCateActivity,ListCategoriesDetails::class.java)
            i.putExtra("name",listData[position].name)
            showAllCateActivity.startActivity(i)

        }

    }
}