package com.joule.endahebraling.listcontent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.joule.endahebraling.databinding.ActivityListBinding
import com.joule.endahebraling.model.DataListContent

class ListContentActivity : AppCompatActivity() {
    companion object {
        val EXTRA_TITLE = "extra_title"
    }

    private lateinit var binding: ActivityListBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var rvList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titleName = intent.getStringExtra(EXTRA_TITLE)
        supportActionBar?.title = titleName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        database = Firebase.database;

        rvList = binding.rvList
        rvList.suppressLayout(false)
        rvList.layoutManager = GridLayoutManager(this, 2)

        when (titleName) {
            "Destination" -> {
                getList("/destination")
            }
            "Village" -> {
                getList("/village")
            }
            "Culture" ->{
                getList("/culture")
            }
            "Art" ->{
                getList("/art")
            }
            "Culinary" ->{
                getList("/culinary")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    fun getList(path: String) {
        val myRef = database.getReference(path)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listDest: ArrayList<DataListContent> = ArrayList()
                for (postSnap in snapshot.children) {
                    val slider = postSnap.getValue<DataListContent>()
                    if (slider != null) {
                        listDest.add(slider)
                    }
                }
                rvList.adapter = ListContentAdapter(items = listDest)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yy", "onCancelled: " + error.message)
            }
        })
    }

}