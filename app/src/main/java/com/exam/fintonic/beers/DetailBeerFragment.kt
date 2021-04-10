package com.exam.fintonic.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.exam.fintonic.R
import com.exam.fintonic.service.model.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailBeerFragment : Fragment() {

    var beer: Beer? = null
    var isDataBase = false
    private val viewModel: DetailViewModel by activityViewModels()
    val args : DetailBeerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //isDataBase = arguments?.getBoolean("isDataBase")!!
        isDataBase = args.isDataBase
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.beerLiveData.observe(viewLifecycleOwner, Observer {
            beer = it
            setView()
        })
    }

    private fun setView(){
        (activity as AppCompatActivity).supportActionBar?.title = beer!!.name
        downloadImage()
        val tag = "${requireContext().getString(R.string.tag_line_label)} ${beer!!.tagline}"
        tvTagLine.text = tag
        val description = "${requireContext().getString(R.string.description_label)} ${beer!!.description}"
        tvDescription.text = description
        val date = "${requireContext().getString(R.string.date_label)} ${beer!!.firstBrewed}"
        tvDate.text = date
        if (beer!!.foodPairing != null){
            var food = ""
            for(data in beer!!.foodPairing!!){
                food += "$data "
            }
            val foodPairing = "${requireContext().getString(R.string.food_pairing_label)} $food"
            tvFood.text = foodPairing
        } else {
            val foodPairing = "${requireContext().getString(R.string.food_pairing_label)} ${beer!!.foodDataBase}"
            tvFood.text = foodPairing
        }
    }

    private fun downloadImage(){
        Picasso.with(context)
            .load(beer!!.imageUrl)
            .placeholder(R.drawable.progress_animation)
            .error(R.mipmap.ic_launcher)
            .into(ivDetailBeer)
    }

}