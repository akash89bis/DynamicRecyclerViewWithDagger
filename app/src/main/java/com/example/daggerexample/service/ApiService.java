package com.example.daggerexample.service;

import com.example.daggerexample.di.DaggerApiComponentInterface;
import com.example.daggerexample.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ApiService {

    private static ApiService instance;

    @Inject
    public RetroClientInterface api;

    private ApiService(){
        DaggerApiComponentInterface.create().injectRetroClient(this);
    }

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public Observable<List<CountryModel>> getCountryList(){
        return api.getCountryList();
    }
}
