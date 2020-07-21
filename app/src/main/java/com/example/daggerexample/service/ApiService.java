package com.example.daggerexample.service;

import com.example.daggerexample.model.CountryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private final static String BASE_URL = "https://raw.githubusercontent.com/";
    private static ApiService instance;
    private RetroClient api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetroClient.class);

    private ApiService(){
    }

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountryList(){
        return api.getCountryList();
    }
}
