package com.joule.endahebraling.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.joule.endahebraling.R
import com.joule.endahebraling.Retrofit.DetikApi
import com.joule.endahebraling.Retrofit.TravelInterface
import com.joule.endahebraling.model.ButtonHome
import com.joule.endahebraling.model.ResponseTravelNews
import com.joule.endahebraling.model.SliderHome
import okhttp3.ResponseBody
import okhttp3.internal.http.RealResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

class HomeViewModel : ViewModel() {

    private val _listCarousel = MutableLiveData<ArrayList<SliderHome>>().apply {
        value = null
    }

    fun setListCarousel(list: ArrayList<SliderHome>) {
        _listCarousel.value = list
    }

    private val _listButton = MutableLiveData<ArrayList<ButtonHome>>().apply {
        var listBtn: ArrayList<ButtonHome> = ArrayList()
        val names = arrayOf("Destination", "Village", "Culture", "Art", "Culinary")
        for (i in names) {
            var newbtn = ButtonHome()
            if (i.equals("Destination")){
                newbtn = ButtonHome(null, i, R.drawable.ic_destination_24)
            }else if (i.equals("Village")){
                newbtn = ButtonHome(null, i, R.drawable.ic_village_24)
            }else if (i.equals("Culture")){
                newbtn = ButtonHome(null, i, R.drawable.ic_culture_24)
            }else if (i.equals("Art")){
                newbtn = ButtonHome(null, i, R.drawable.ic_art_24)
            }else if (i.equals("Culinary")){
                newbtn = ButtonHome(null, i, R.drawable.ic_culinary_24)
            }
            listBtn.add(newbtn)
        }
        value = listBtn
    }

    private val _progress = MutableLiveData<Boolean>().apply {
        value = true;
    }

    fun setProgress(condition: Boolean) {
        _progress.value = condition
    }

    private val _news = MutableLiveData<ResponseTravelNews>().apply {
        val request = DetikApi.detikTravel().create(TravelInterface::class.java)
        val call = request.getNewsTravel()
        call.enqueue(object : Callback<ResponseTravelNews> {
            override fun onResponse(
                call: Call<ResponseTravelNews>,
                response: Response<ResponseTravelNews>
            ) {
                Log.d("yy", "onResponse: " + response.body())
                value = response.body()
            }

            override fun onFailure(call: Call<ResponseTravelNews>, t: Throwable) {
                Log.d("yy", "onFailure: " + t.message)
            }
        })

    }

    val news: LiveData<ResponseTravelNews> = _news
    val listCarousel: LiveData<ArrayList<SliderHome>> = _listCarousel
    val listButton: LiveData<ArrayList<ButtonHome>> = _listButton
    val progress: LiveData<Boolean> = _progress
}

