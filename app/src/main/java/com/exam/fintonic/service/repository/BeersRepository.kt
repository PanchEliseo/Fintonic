package com.exam.fintonic.service.repository

import com.exam.fintonic.service.mapper.BeerMapper
import com.exam.fintonic.service.model.ListBeers
import com.exam.fintonic.service.Resource
import retrofit2.HttpException

class BeersRepository: BaseRepository() {

    suspend fun getBeers(page: Int, perPage: Int) : Resource<ListBeers> {
        return try {
            val urlBeers = "v2/beers?page=$page&per_page=$perPage"
            val beers = serviceBeer.getBeers(url = urlBeers)
            if (beers.isSuccessful) {
                val mapper = beers.body()?.let { BeerMapper().map(it) }
                Resource.success(mapper)
            } else {
                throw HttpException(beers)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e)
        }
    }

}