package com.joule.endahebraling.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class DetikApi {
    companion object{
        fun detikTravel() :Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://id.popin.cc/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
    }
}