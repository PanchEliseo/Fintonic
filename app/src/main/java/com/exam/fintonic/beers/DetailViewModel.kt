package com.exam.fintonic.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.fintonic.service.model.Beer

class DetailViewModel: ViewModel() {
    val beerLiveData = MutableLiveData<Beer>()
    val listLiveData = MutableLiveData<MutableList<Beer>>()

    fun select(beer: Beer) {
        beerLiveData.value = beer
    }

    fun setList(list: MutableList<Beer>){
        listLiveData.value = list
    }

    fun updateList(list: MutableList<Beer>){
        listLiveData.value!!.addAll(0, list)
    }

}