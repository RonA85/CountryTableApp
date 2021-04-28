package com.ron.countrytableapp.data.repository

import com.ron.countrytableapp.data.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCountries() =  apiService.getCountries()

}