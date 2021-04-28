package com.ron.countrytableapp.data.model

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    val name: String = "",

    @SerializedName("nativeName")
    val nativeName: String = "",

    @SerializedName("area")
    val area: Double = 0.0,


    @SerializedName("alpha3Code")
    val alpha3Code: String = "",

    @SerializedName("borders")
    val borders: ArrayList<String>
)
