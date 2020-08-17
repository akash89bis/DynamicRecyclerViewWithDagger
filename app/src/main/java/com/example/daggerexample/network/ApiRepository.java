package com.example.daggerexample.network;

import com.example.daggerexample.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ApiRepository {

    private ApiInterface api;

    @Inject
    ApiRepository(ApiInterface repoService) {
        this.api = repoService;
    }

    public Observable<List<CountryModel>> getCountryList(){
        return api.getCountryList();
    }
}
