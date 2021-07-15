package com.example.covidtracker.apisecond.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidData {

    @SerializedName("state_name")
    @Expose
    private String State_name;

    @SerializedName("district_name")
    @Expose
    private String District_name;

    @SerializedName("fee_type")
    @Expose
    private String Fee_type;

    @SerializedName("name")
    @Expose
    private String Name;

    public CovidData(String state_name, String district_name, String fee_type, String name) {
        State_name = state_name;
        District_name = district_name;
        Fee_type = fee_type;
        Name = name;
    }

    public String getState_name() {
        return State_name;
    }

    public void setState_name(String state_name) {
        State_name = state_name;
    }

    public String getDistrict_name() {
        return District_name;
    }

    public void setDistrict_name(String district_name) {
        District_name = district_name;
    }

    public String getFee_type() {
        return Fee_type;
    }

    public void setFee_type(String fee_type) {
        Fee_type = fee_type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
