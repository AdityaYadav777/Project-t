package com.aditya.projectt.views

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aditya.projectt.R
import com.aditya.projectt.categoriesData.cateAdapter
import com.aditya.projectt.categoriesData.dataCate
import com.aditya.projectt.databinding.ActivityShowAllCateBinding

class ShowAllCateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityShowAllCateBinding
     val listData= arrayListOf<dataCate>()
   private lateinit  var adapter:cateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowAllCateBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

binding.backBtn.setOnClickListener {
    onBackPressed()
}



        val listCate= arrayListOf("Plumber","Painter","Electrician","Rajmistri","Carpenter","Home Cleaner","Beauty","Ac Repair","Ro-Service","Mobile Repair","Laptop Repair","Developer","Wiring","Labour","Rasoeya")
        val urlList= arrayListOf("https://cdn-icons-png.flaticon.com/512/10366/10366020.png","Painter","Electrician","Rajmistri","Carpenter","Home Cleaner","Beauty","Ac Repair","Ro Service","Laptop Service","Developer","Wiring","Labor","Rasoeya")



        val i=urlList.size-1
        for( data in 0..i ) {
            listData.add(dataCate(urlList[data],listCate[data]))
        }


    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {


            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                filter(newText)
            }
            return false
        }

    })

    adapter = cateAdapter(this, listData)
    binding.myRec.adapter = adapter

    binding.myRec.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)




}


    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<dataCate> = ArrayList()

        // running a for loop to compare elements.
        for (item in listData) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {

            adapter.filterList(filteredlist)
        }
    }

 }
