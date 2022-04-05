package com.example.covidtracker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilites {

    private static  Retrofit retrofit = null;

    public static Apiinterface getApiInterface() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Apiinterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Apiinterface.class);
    }
}