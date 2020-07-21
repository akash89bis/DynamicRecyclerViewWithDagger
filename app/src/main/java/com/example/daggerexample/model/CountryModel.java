package com.example.daggerexample.model;

import com.google.gson.annotations.SerializedName;

public class CountryModel {

    @SerializedName("name")
    private String countryName;

    @SerializedName("capital")
    private String capital;

    public CountryModel(String countryName, String capital) {
        this.countryName = countryName;
        this.capital = capital;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }
}
