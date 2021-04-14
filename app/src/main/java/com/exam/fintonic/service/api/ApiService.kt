package com.exam.fintonic.service.api

import com.exam.fintonic.service.model.Beer
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //https://api.punkapi.com/v2/beers
    @GET
    suspend fun getBeers(@Url url: String = "v2/beers"): Response<MutableList<Beer>>

}