package com.example.covidtracker.apisecond;

import com.example.covidtracker.api.CountryData;
import com.example.covidtracker.apisecond.Models.covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSecondInterface {

    @GET("calendarByPin")
    Call<covid> getCentre(
            @Query("pincode") String pincode,
            @Query("date") String date
    );
}
