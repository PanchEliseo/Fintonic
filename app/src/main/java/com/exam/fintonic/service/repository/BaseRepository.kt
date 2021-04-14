package com.exam.fintonic.service.repository

import com.exam.fintonic.service.api.ApiManagerFactory
import com.exam.fintonic.service.ResponseHandler

open class BaseRepository {

    val serviceBeer = ApiManagerFactory.makeRetrofitService()
    val responseHandler : ResponseHandler = ResponseHandler()
}
