package com.example.daggerexample.service;

import com.example.daggerexample.di.DaggerApiComponentInterface;
import com.example.daggerexample.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkService {

    private static NetworkService instance;

    @Inject
    public RetroClientInterface api;

    private NetworkService(){
        DaggerApiComponentInterface.create().injectRetroClient(this);
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public Observable<List<CountryModel>> getCountryList(){
        return api.getCountryList();
    }
}
