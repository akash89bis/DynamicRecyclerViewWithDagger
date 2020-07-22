package com.example.daggerexample.di;

import com.example.daggerexample.service.ApiService;
import com.example.daggerexample.viewmodel.CountryViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})

public interface ApiComponentInterface {
    void injectRetroClient(ApiService service);

    void injectApiService(CountryViewModel viewModel);
}
