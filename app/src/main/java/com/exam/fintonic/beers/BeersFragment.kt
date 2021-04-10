package com.exam.fintonic.beers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.fintonic.R
import com.exam.fintonic.helper.FeedReaderDbHelper
import com.exam.fintonic.helper.InternetHelper
import com.exam.fintonic.service.Status
import com.exam.fintonic.service.model.Beer
import kotlinx.android.synthetic.main.beers_fragment.*

class BeersFragment : Fragment(), ClickItemList {

    private lateinit var viewModel: BeersViewModel
    private val modelDetail: DetailViewModel by activityViewModels()
    var page = 1
    var adapter : BeersAdapter? = null
    private var recreate = false
    private lateinit var listBeers: MutableList<Beer>
    private var isDataBase = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.beers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        viewModel = ViewModelProvider(this).get(BeersViewModel::class.java)
        subscribeLiveData()
        setListeners()
        Log.i("INTERNET", "CONNECT ${InternetHelper().isInternetConnected(requireContext())}")
        if (InternetHelper().isInternetConnected(requireContext())) {
            recreate = true
            isDataBase = false
            if (modelDetail.listLiveData.value == null)
                viewModel.getBeers(page, 20)
            else {
                listBeers = modelDetail.listLiveData.value!!
                setListView()
            }
        } else {
            isDataBase = true
            getBeersList()
            setListView()
        }
    }

    private fun setListView(){
        adapter = activity?.let { BeersAdapter(requireContext(), listBeers) }!!
        rvBeers.adapter = adapter
        rvBeers.layoutManager = LinearLayoutManager(activity)
        adapter?.setListenerClick(this)
    }

    private fun updateList(){
        adapter?.updateList(listBeers)
    }

    override fun clickButtons(view: View, position: Int, list: MutableList<Beer>) {
        modelDetail.select(list[position])
        modelDetail.setList(list)
        val action = BeersFragmentDirections.actionBeersFragmentToDetailBeer(isDataBase)
        findNavController().navigate(action)
        //val bundle = bundleOf("isDataBase" to isDataBase)
        //findNavController().navigate(R.id.action_beersFragment_to_detailBeer, bundle)
    }

    private fun setListeners() {
        swipeLayout.setOnRefreshListener {
            page++
            recreate = false
            viewModel.getBeers(page, 20)
        }
    }

    private fun subscribeLiveData(){
        viewModel.beersLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Status.LOADING -> {
                    Log.i("ON", "LOADING")
                    swipeLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    Log.i("ON", "SUCCESS")
                    swipeLayout.isRefreshing = false
                    isDataBase = false
                    listBeers = it.data!!.listBeers
                    if (modelDetail.listLiveData.value == null) {
                        if (page == 1 || recreate) {
                            deleteElementsDataBase()
                            setElementsDataBase()
                            setListView()
                        } else {
                            setElementsDataBase()
                            updateList()
                        }
                    } else {
                        if (!recreate) {
                            setElementsDataBase()
                            updateList()
                        }
                    }
                }
                Status.ERROR -> {
                    Log.i("ON", "ERROR")
                    swipeLayout.isRefreshing = false
                }
            }
        })
    }

    private fun setElementsDataBase(){
        val databaseHandler = FeedReaderDbHelper(requireContext())
        for (beer in listBeers){
            var food = ""
            for(data in beer.foodPairing!!){
                food += "$data "
            }
            val success = databaseHandler.addBeer(beer, food)
        }
        databaseHandler.close()
    }

    private fun deleteElementsDataBase(){
        val databaseHandler = FeedReaderDbHelper(requireContext())
        val success = databaseHandler.deleteBeer()
        databaseHandler.close()
    }

    private fun updateElementsDataBase(){
        val databaseHandler = FeedReaderDbHelper(requireContext())
        for (beer in listBeers){
            var food = ""
            for(data in beer.foodPairing!!){
                food += "$data "
            }
            val success = databaseHandler.updateBeer(beer, food)
        }
        databaseHandler.close()
    }

    private fun getBeersList(){
        val databaseHandler = FeedReaderDbHelper(requireContext())
        listBeers = databaseHandler.getBeers()
        databaseHandler.close()
    }

}