package com.joule.endahebraling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.joule.endahebraling.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title= "About"

        val logo = "https://firebasestorage.googleapis.com/v0/b/wecrash-f8914.appspot.com/o/about%2FIMG_8014.PNG?alt=media&token=e6a6e1fa-b9e5-45de-a275-20aa2075abf9"
        val sourceImgWriter = "https://firebasestorage.googleapis.com/v0/b/wecrash-f8914.appspot.com/o/about%2FIMG_8093.JPG?alt=media&token=6fb89ba5-2262-417c-9128-c3175a2b37e6"
        val sourceImgEnginer = "https://firebasestorage.googleapis.com/v0/b/wecrash-f8914.appspot.com/o/about%2F1606972700054.jfif?alt=media&token=62aa61e3-4600-46c5-bb4a-feb963292d14"
        val sourceImgPubdar = "https://firebasestorage.googleapis.com/v0/b/wecrash-f8914.appspot.com/o/about%2FIMG_8019.JPG?alt=media&token=6e9d1eb1-ec30-4a26-b2d6-7e0bd96eeaba"


        Glide.with(this)
            .load(logo)
            .centerCrop()
            .into(binding.imgLogo)

        Glide.with(this)
            .load(sourceImgWriter)
            .centerCrop()
            .into(binding.imgWriter)

        Glide.with(this)
            .load(sourceImgEnginer)
            .centerCrop()
            .into(binding.imgEnginer)

        Glide.with(this)
            .load(sourceImgPubdar)
            .centerCrop()
            .into(binding.imgPubdar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }
}