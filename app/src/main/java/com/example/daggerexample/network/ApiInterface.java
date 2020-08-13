package com.example.daggerexample.network;

import com.example.daggerexample.model.CountryModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("DevTides/countries/master/countriesV2.json")
    Observable<List<CountryModel>> getCountryList();
}
