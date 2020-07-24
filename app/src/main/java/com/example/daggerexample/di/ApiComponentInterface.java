package com.example.daggerexample.di;

import com.example.daggerexample.service.NetworkService;
import com.example.daggerexample.viewmodel.CountryViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})

public interface ApiComponentInterface {
    void injectRetroClient(NetworkService service);

    void injectApiService(CountryViewModel viewModel);
}
