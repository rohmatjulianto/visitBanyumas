package com.joule.endahebraling.listcontent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.ActivityDetailDestinationBinding
import com.joule.endahebraling.dbroom.AppDatabase
import com.joule.endahebraling.model.DataListContent
import com.joule.endahebraling.model.DbClass

class DetailContentActivity : AppCompatActivity() {

    companion object {
        val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailDestinationBinding
    private lateinit var db: AppDatabase

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
        data?.address?.apply {
            binding.tvAbout.text = "Address"
            binding.tvTextAbout.text = this
        }
        data?.star?.apply {
            binding.tvRating.visibility = View.VISIBLE
            binding.ratingHotel.visibility = View.VISIBLE

            val star = data?.star?.toFloatOrNull()
                binding.ratingHotel.numStars = data.star.toInt()
            if (star != null) {
                binding.ratingHotel.rating = star
            }
        }

        binding.tvTextMaps.text = data?.maps?.apply {
            binding.tvTextMaps.visibility = View.VISIBLE
            binding.tvMaps.visibility = View.VISIBLE
            binding.tvTextMaps.setOnClickListener(View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(this)
                startActivity(intent)
            })
        }

        binding.tvTextContact.text = data?.contact?.apply {
            binding.tvContact.visibility = View.VISIBLE
            binding.tvTextContact.visibility = View.VISIBLE
        }


        val rvImage = binding.rvImage;
        rvImage.layoutManager = LinearLayoutManager(this)
        rvImage.adapter = data?.images?.let { DetailImageAdapter(it) }


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "data-list"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()


        getStatusFab(data)
        binding.fab.setOnClickListener { view ->
            val dbInsert = DbClass(
                0,
                data?.name,
                data?.contact,
                data?.desc,
                data?.star,
                data?.address,
                data?.maps,
                Gson().toJson(data?.images)
            )
            if (data?.name?.let { db.dbDao().getByName(it) } == null) {
                db.dbDao().insertAll(dbInsert)
                Snackbar.make(view, "Add ${data?.name} to Bookmark", Snackbar.LENGTH_SHORT).show()
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_bookmark_added_24
                    )
                )
            } else {
                db.dbDao().delete(data?.name)
                Snackbar.make(view, "Bookmark ${data?.name} deleted", Snackbar.LENGTH_SHORT).show()
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_bookmark_add_24
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    fun getStatusFab(data: DataListContent) {
        if (data.name?.let { db.dbDao().getByName(it) } != null) {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_added_24
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_add_24
                )
            )
        }
    }
}