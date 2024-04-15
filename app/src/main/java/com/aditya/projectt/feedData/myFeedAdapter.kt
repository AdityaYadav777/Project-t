package com.aditya.projectt.feedData

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.projectt.R
import com.aditya.projectt.R.id.commentInput
import com.aditya.projectt.views.ImagePreviewScreen
import com.aditya.projectt.views.ProfileDashAct
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date

lateinit var likeCount: String

class myFeedAdapter(
    val requireContext: Context,
    val listOfFeeds: ArrayList<myFeedData>

) : RecyclerView.Adapter<myFeedAdapter.myViewHolder>() {
    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.myName)
        val type = itemView.findViewById<TextView>(R.id.myType)
        val desc = itemView.findViewById<TextView>(R.id.myDesc)
        val profile = itemView.findViewById<ImageView>(R.id.profile_image)
        val feedImage = itemView.findViewById<ImageView>(R.id.myFeedPost)
        val likeBtn = itemView.findViewById<ImageView>(R.id.likeBtn)
        val likeCount = itemView.findViewById<TextView>(R.id.likeCount)
        val cardView2 = itemView.findViewById<CardView>(R.id.cardView2)
        val comment = itemView.findViewById<ImageView>(R.id.comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listOfFeeds.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text = listOfFeeds[position].name
        holder.type.text = listOfFeeds[position].type
        holder.desc.text = listOfFeeds[position].desc
        holder.profile.load(listOfFeeds[position].profileUrl)

        holder.feedImage.load(listOfFeeds[position].feedUrl)
        holder.profile.setOnClickListener {
            val i = Intent(requireContext, ProfileDashAct::class.java)
            i.putExtra("uid", listOfFeeds[position].uid)
            requireContext.startActivity(i)
        }

        Firebase.database.reference.child("Likes")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(listOfFeeds[position].docId)
            .get().addOnSuccessListener {
            val getData = it.value
            if (getData == true) {
                holder.likeBtn.setColorFilter(Color.RED)

            } else {
                holder.likeBtn.setColorFilter(Color.GRAY)

            }

            println()

        }

        Firebase.database.reference.child("Likes")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(listOfFeeds[position].docId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value
                    if (data == true) {
                        holder.likeBtn.setColorFilter(Color.GRAY)


                    } else if (data == null) {

                        holder.likeCount.text = ""
                    } else {
                        holder.likeBtn.setColorFilter(Color.RED)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


//      Firebase.database.reference.child("Likes").child(FirebaseAuth.getInstance().currentUser!!.uid).child(listOfFeeds[position].docId).child("likes").addValueEventListener(object  :ValueEventListener{
//          override fun onDataChange(snapshot: DataSnapshot) {
//        val data=snapshot.value
//
//              getlikes=data.toString()
//              holder.likeCount.text=data.toString()
//
//          }
//
//          override fun onCancelled(error: DatabaseError) {
//              TODO("Not yet implemented")
//          }
//
//      })


        var data = listOfFeeds[position].likes

        var likes = data.toInt()
        var chLike = true
        holder.likeBtn.setOnClickListener {

            if (chLike == true) {
                chLike = false

                holder.likeBtn.setColorFilter(Color.RED)
                updateLike(true, listOfFeeds[position].docId)

            } else {


                deleteData(false, listOfFeeds[position].docId)
                holder.likeBtn.setColorFilter(Color.GRAY)
                chLike = true
            }
        }
        holder.comment.setOnClickListener {
            showComments(it, listOfFeeds[position].docId)
        }

        holder.cardView2.setOnClickListener {
            val i = Intent(requireContext, ImagePreviewScreen::class.java)
            i.putExtra("url", listOfFeeds[position].feedUrl)
            requireContext.startActivity(i)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showComments(view: View, docId: String) {
        val dialog = BottomSheetDialog(requireContext)
        val view = dialog.layoutInflater.inflate(R.layout.activity_show_comment_bottom_sheet, null)

        val db = Firebase.firestore
        val myRec = view.findViewById<RecyclerView>(R.id.myRec)
        val comment = view.findViewById<EditText>(commentInput)
        val sendBtn = view.findViewById<ImageView>(R.id.sendBtn)

        db.collection(docId).orderBy("date").addSnapshotListener { value, error ->
            val listData = arrayListOf<commentData>()
            val data = value?.toObjects(commentData::class.java)
            listData.addAll(data!!)
            myRec.adapter = commentAdapter(view, listData)
            myRec.layoutManager =
                LinearLayoutManager(requireContext, LinearLayoutManager.VERTICAL, false)
        }
        sendBtn.setOnClickListener {

            var name: String? = null
            var uid: String? = null
            var profile: String? = null
            db.collection("UserData").document(FirebaseAuth.getInstance().currentUser!!.uid).get()
                .addOnSuccessListener {
                    name = it.get("name").toString()
                    uid = it.get("uid").toString()
                    profile = it.get("url").toString()
                }.addOnSuccessListener {
                val text = comment.text.toString()
                val data = hashMapOf(
                    "name" to name,
                    "comment" to text,
                    "uid" to uid,
                    "profile" to profile,
                    "date" to SimpleDateFormat("dd-MM-yyyy \t HH:mm:ss").format(Date()).toString()
                )
                db.collection(docId).document().set(data)
                comment.text.clear()
                notifyDataSetChanged()
            }

        }

        dialog.setContentView(view)
        dialog.show()
    }

    fun deleteData(data: Boolean, docId: String) {
        val db = Firebase.database.reference
        db.child("Likes").child(FirebaseAuth.getInstance().currentUser!!.uid).child(docId)
            .setValue(data).addOnCompleteListener {
        }

//     db.child("Likes").child(FirebaseAuth.getInstance().currentUser!!.uid).child(docId).child("likes").setValue(likes)
//     Firebase.firestore.collection("FeedPost").document(docId).update("likes",likes)

    }

    fun updateLike(data: Boolean, docId: String) {
        val db = Firebase.database.reference
        db.child("Likes").child(FirebaseAuth.getInstance().currentUser!!.uid).child(docId)
            .setValue(data).addOnCompleteListener {

        }
//        Firebase.firestore.collection("FeedPost").document(docId).update("likes",likes)

    }
}
