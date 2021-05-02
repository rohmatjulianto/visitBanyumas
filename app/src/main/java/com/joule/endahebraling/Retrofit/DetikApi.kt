package com.joule.endahebraling.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetikApi {
    companion object{
        fun detikTravel() :Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://id.popin.cc/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
            return retrofit
        }
    }
}