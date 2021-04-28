package com.ron.countrytableapp.data.api

import com.ron.countrytableapp.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    suspend fun getCountries(): Response<List<Country>>

}