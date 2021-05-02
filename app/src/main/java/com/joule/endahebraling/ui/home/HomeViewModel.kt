package com.joule.endahebraling.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
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
            val newbtn = ButtonHome(null, i, null)
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

    private val _news = MutableLiveData<RealResponseBody>().apply {
        val request = DetikApi.detikTravel().create(Travel::class.java)
        val call = request.getNewsTravel()
        call.enqueue(object : Callback<RealResponseBody> {
            override fun onResponse(
                call: Call<RealResponseBody>,
                response: Response<RealResponseBody>
            ) {
                Log.d("yy", "onResponse: " + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<RealResponseBody>, t: Throwable) {
                Log.d("yy", "onFailure: " + t.message)
            }
        })
    }

    val news: LiveData<RealResponseBody> = _news
    val listCarousel: LiveData<ArrayList<SliderHome>> = _listCarousel
    val listButton: LiveData<ArrayList<ButtonHome>> = _listButton
    val progress: LiveData<Boolean> = _progress
}

internal interface Travel {
    @Headers(
        "Accept: */*",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept-Language: en-US,en;q=0.9,id-ID;q=0.8,id;q=0.7",
        "Connection: keep-alive",
        "Cookie: uid=dcc3c55b3fe6dd8defe1599644956880",
        "Host: id.popin.cc",
        "Referer: https://travel.detik.com/",
        "sec-ch-ua-mobile: ?0",
        "Sec-Fetch-Dest: script",
        "Sec-Fetch-Mode: no-cors",
        "Sec-Fetch-Site: cross-site",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
    )
    @GET("popin_discovery/recommend?mode=new&url=https%3A%2F%2Ftravel.detik.com%2Fdomestic-destinations&&device=pc&media=travel.detik.com&extra=windows&agency=baidu-id&topn=50&ad=0&country=id&redirect=true&rid=303283&uid=dcc3c55b3fe6dd8defe1599644956880&info=eyJ1c2VyX3RkX29zIjoiV2luZG93cyIsInVzZXJfdGRfb3NfdmVyc2lvbiI6IjEwLjAuMCIsInVzZXJfdGRfYnJvd3NlciI6IkNocm9tZSIsInVzZXJfdGRfYnJvd3Nlcl92ZXJzaW9uIjoiOTAuMC40NDMwIiwidXNlcl90ZF9zY3JlZW4iOiIxOTIweDEwODAiLCJ1c2VyX3RkX3ZpZXdwb3J0IjoiNzY5eDk3OSIsInVzZXJfdGRfdXNlcl9hZ2VudCI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS85MC4wLjQ0MzAuOTMgU2FmYXJpLzUzNy4zNiIsInVzZXJfdGRfcmVmZXJyZXIiOiJodHRwczovL3RyYXZlbC5kZXRpay5jb20vZGVzdGluYXRpb25zIiwidXNlcl90ZF9wYXRoIjoiL2RvbWVzdGljLWRlc3RpbmF0aW9ucyIsInVzZXJfdGRfY2hhcnNldCI6InV0Zi04IiwidXNlcl90ZF9sYW5ndWFnZSI6ImVuLXVzIiwidXNlcl90ZF9jb2xvciI6IjI0LWJpdCIsInVzZXJfdGRfdGl0bGUiOiJkZXRpa1RyYXZlbCUyMCU3QyUyMEluc3BpcmFzaSUyMEphbGFuLWphbGFuJTIwS2UlMjBNYW5hJTIwQWphIiwidXNlcl90ZF91cmwiOiJodHRwczovL3RyYXZlbC5kZXRpay5jb20vZG9tZXN0aWMtZGVzdGluYXRpb25zIiwidXNlcl90ZF9wbGF0Zm9ybSI6IldpbjMyIiwidXNlcl90ZF9ob3N0IjoidHJhdmVsLmRldGlrLmNvbSIsInVzZXJfZGV2aWNlIjoicGMiLCJ1c2VyX3RpbWUiOjE2MTk4ODM1NzQ0OTAsImZydWl0X2JveF9wb3NpdGlvbiI6InRvcF9yaWdodCIsImZydWl0X3N0eWxlIjoiNCJ9&r_category=all")
    fun getNewsTravel(): Call<RealResponseBody>
}
