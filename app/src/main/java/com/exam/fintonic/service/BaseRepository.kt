package com.exam.fintonic.service

import retrofit2.HttpException
import retrofit2.Response

open class BaseRepository {

    val serviceBeer = ApiManagerFactory.makeRetrofitService()
    val responseHandler : ResponseHandler = ResponseHandler()
}
