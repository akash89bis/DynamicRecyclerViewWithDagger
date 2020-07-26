package com.example.daggerexample.model;

import com.google.gson.annotations.SerializedName;

public class CountryModel {

    @SerializedName("name")
    private String countryName;

    @SerializedName("capital")
    private String capital;

    @SerializedName("flagPNG")
    private String flag;

    @SerializedName("numericCode")
    private String countryCode;

    public CountryModel(String countryName, String capital, String flag, String countryCode) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
