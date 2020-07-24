package com.example.daggerexample.service;

import com.example.daggerexample.model.CountryModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface RetroClientInterface {

    @GET("DevTides/countries/master/countriesV2.json")
    Observable<List<CountryModel>> getCountryList();
}
