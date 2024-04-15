package com.aditya.projectt.UserFeedData

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.views.UploadImageFeed
import com.aditya.projectt.feedData.myFeedData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class userFeedAdapter(val requireContext: Context,val  listOfFeedData: ArrayList<myFeedData>) :RecyclerView.Adapter<userFeedAdapter.myViewHolder>() {

    inner class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
val img=itemView.findViewById<ImageView>(R.id.feedImg)

  val dots=itemView.findViewById<ImageView>(R.id.feedDots)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
return myViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_load_my_feed,parent,false)

)
    }

    override fun getItemCount(): Int {
   return listOfFeedData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
      holder.img.load(listOfFeedData[position].feedUrl)
      val  getUid=listOfFeedData[position].docId
     val url=listOfFeedData[position].feedUrl
        val desc=listOfFeedData[position].desc

        holder.dots.setOnClickListener {

            popUpMenu(it,getUid,url,desc)


        }

    }
    fun popUpMenu(view: View, getUid: String, url: String, desc: String) {
        val popMenu=PopupMenu(requireContext,view)
        popMenu.inflate(R.menu.popup_menu)
        popMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delBtn->{
                    delFun(getUid)
                    true}
                R.id.updateBtn->{
                    Toast.makeText(requireContext, "Update", Toast.LENGTH_SHORT).show()
                    updateFun(getUid,url,desc)
                    true
                }
                else->
                    true
            }

        }
        popMenu.show()
    }

    private fun updateFun(uid: String, url: String, desc: String) {


        val i= Intent(requireContext, UploadImageFeed::class.java)
        i.putExtra("getDocID",uid)
        i.putExtra("getUri",url)
        i.putExtra("desc",desc)
        i.putExtra("sKey","fromFeed")
        requireContext.startActivity(i)
    }

    fun delFun(getUid: String) {
val db=Firebase.firestore
        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).document(getUid).delete().addOnCompleteListener {
            if(it.isSuccessful){

                db.collection("FeedPost").document(getUid).delete().addOnCompleteListener {
                    Toast.makeText(requireContext, "Deleted From Feed", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(requireContext, "Deleted From Profile", Toast.LENGTH_SHORT).show()
            }
        }

    }



}