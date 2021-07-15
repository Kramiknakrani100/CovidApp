package com.example.covidtracker.apisecond.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class covid {

    @SerializedName("centers")
    @Expose
    private List<CovidData> Centers;

    public covid(List<CovidData> centers) {
        Centers = centers;
    }

    public List<CovidData> getCenters() {
        return Centers;
    }

    public void setCenters(List<CovidData> centers) {
        Centers = centers;
    }
}
