package com.example.covidtracker.api

class CountryData(//@SerializedName("updated")
    var updated: String,
    var country: String,
    var cases: String,
    var todayCases: String,
    var deaths: String,
    var todayDeaths: String,
    var recovered: String,
    var todayRecovered: String,
    var active: String,
    var tests: String,
    var countryInfo: Map<String, String>
)