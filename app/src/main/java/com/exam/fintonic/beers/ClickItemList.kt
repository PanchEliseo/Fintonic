package com.exam.fintonic.beers

import android.view.View
import com.exam.fintonic.service.model.Beer

interface ClickItemList {
    fun clickButtons(view: View, position: Int, list: MutableList<Beer>)
}