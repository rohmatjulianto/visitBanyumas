package com.joule.endahebraling.listcontent

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.ActivityDetailDestinationBinding
import com.joule.endahebraling.dbroom.AppDatabase
import com.joule.endahebraling.model.DataListContent
import com.joule.endahebraling.model.DbClass

class DetailContentActivity : AppCompatActivity() {

    companion object{
        val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding:ActivityDetailDestinationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<DataListContent>(EXTRA_DATA)

        binding.toolbarLayout.title = data?.name
        Glide.with(this)
            .load(data?.images?.get(0)?.url)
            .centerCrop()
            .into(binding.imgDetail)

        binding.tvTextAbout.text = data?.desc
        binding.tvTextMaps.text = data?.maps?.apply {
            binding.tvTextMaps.visibility = View.VISIBLE
            binding.tvMaps.visibility = View.VISIBLE
        }

        binding.tvTextContact.text = data?.contact?.apply {
            binding.tvContact.visibility = View.VISIBLE
            binding.tvTextContact.visibility = View.VISIBLE
        }


        val rvImage = binding.rvImage;
        rvImage.layoutManager = LinearLayoutManager(this)
        rvImage.adapter = data?.images?.let { DetailImageAdapter(it) }


        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "data-list"
        ).allowMainThreadQueries().build()


        binding.fab.drawable.setTint(ContextCompat.getColor(this, R.color.yellow))
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", View.OnClickListener {
                    val dbInsert = DbClass(
                        0,
                        data?.name,
                        data?.contact,
                        data?.desc,
                        data?.maps,
                        Gson().toJson(data?.images)
                    )
                    db.dbDao().insertAll(dbInsert)

                    Log.d("yy", "onCreate: "+ Gson().toJson(db.dbDao().getAll()))
                }).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }
}