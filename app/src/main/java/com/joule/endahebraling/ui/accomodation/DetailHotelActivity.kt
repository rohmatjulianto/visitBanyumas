package com.joule.endahebraling.ui.accomodation

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.ActivityDetailHotelBinding
import com.joule.endahebraling.databinding.FragmentHomeBinding
import com.joule.endahebraling.model.HotelAccomodation

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class DetailHotelActivity : AppCompatActivity() {

    companion object{
        var EXTRA_DATA = "extra"
    }
    private lateinit var binding: ActivityDetailHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelBinding.inflate(layoutInflater)
        val view = binding.root
        val detail = intent.getParcelableExtra<HotelAccomodation>(EXTRA_DATA);
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.title = detail.name

        Glide.with(this)
            .load(detail.images?.get(0)?.url)
            .centerCrop()
            .into(binding.imgDetail)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }
}