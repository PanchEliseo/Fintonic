package com.exam.fintonic.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManagerFactory {

    fun makeRetrofitService(): ApiService {
        return Retrofit.Builder()
                .baseUrl("https://api.punkapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
    }

}