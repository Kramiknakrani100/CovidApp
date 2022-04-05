package com.example.covidtracker.api

import retrofit2.http.GET
import com.example.covidtracker.api.CountryData
import retrofit2.Call

interface Apiinterface {
    @get:GET("countries")
    val countryData: Call<List<CountryData>>

    companion object {
        // URL
        const val BASE_URL = "https://corona.lmao.ninja/v2/"
    }
}