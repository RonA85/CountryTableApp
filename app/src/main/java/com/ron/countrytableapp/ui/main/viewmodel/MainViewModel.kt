package com.ron.countrytableapp.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ron.countrytableapp.data.model.Country
import com.ron.countrytableapp.data.repository.MainRepository
import com.ron.countrytableapp.utils.NetworkHelper
import com.ron.countrytableapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _countries = MutableLiveData<Resource<List<Country>>>()
    private val _borders = MutableLiveData<List<Country>>()
    private var allCountries = ArrayList<Country>()
    private var sortByDescending = false
    private var sortByBigArea = false
    val countries: LiveData<Resource<List<Country>>>
        get() = _countries

    val borders: LiveData<List<Country>>
        get() = _borders

    init {
        fetchCountries()
    }

    fun getBorders(borders: ArrayList<String>) {
        val list = ArrayList<Country>()
        for (border in allCountries) {
            if (borders.contains(border.alpha3Code)) {
                list.add(border)
            }
        }
        _borders.postValue(list);

    }


    private fun fetchCountries() {
        viewModelScope.launch {
            _countries.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getCountries().let { it ->
                    if (it.isSuccessful) {
                        allCountries = it.body() as ArrayList<Country>
                        _countries.postValue(Resource.success(it.body()))
                    } else _countries.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _countries.postValue(Resource.error("No internet connection", null))
        }
    }

    fun sortByName() {
         if(sortByDescending){
             sortByDescending = false
             val sortedList = allCountries.sortedByDescending { country -> country.name }
             _countries.postValue(Resource.success(sortedList))
         }else{
             sortByDescending = true
             val sortedList = allCountries.sortedBy { country -> country.name }
             _countries.postValue(Resource.success(sortedList))
         }
    }

    fun sortByArea() {
        if(sortByBigArea){
            sortByBigArea = false
            val sortedList = allCountries.sortedByDescending { country -> country.area }
            _countries.postValue(Resource.success(sortedList))
        }else{
            sortByBigArea = true
            val sortedList = allCountries.sortedBy { country -> country.area }
            _countries.postValue(Resource.success(sortedList))
        }
    }
}