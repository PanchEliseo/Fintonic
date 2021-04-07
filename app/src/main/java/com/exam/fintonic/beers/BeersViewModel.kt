package com.exam.fintonic.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.fintonic.service.BeersRepository
import com.exam.fintonic.service.ListBeers
import com.exam.fintonic.service.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeersViewModel : ViewModel()  {

    val beersLiveData = MutableLiveData<Resource<ListBeers>>()

    private val beersRepository by lazy {
        BeersRepository()
    }

    fun getBeers(page: Int, perPage: Int){
        try {
            viewModelScope.launch {
                beersLiveData.value = Resource.loading(null)
                val responseData = beersRepository.getBeers(page, perPage)
                withContext(Dispatchers.IO){
                    beersLiveData.postValue(responseData)
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}