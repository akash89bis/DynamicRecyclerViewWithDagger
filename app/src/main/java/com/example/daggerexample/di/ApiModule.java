package com.example.daggerexample.di;

import com.example.daggerexample.service.ApiService;
import com.example.daggerexample.service.RetroClientInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiModule {

    private final static String BASE_URL = "https://raw.githubusercontent.com/";

    @Provides
    public RetroClientInterface providesCountriesApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RetroClientInterface.class);
    }

    @Provides
    public ApiService providesApiService(){
        return ApiService.getInstance();
    }

}
