package com.example.daggerexample.di.module;

import androidx.lifecycle.ViewModel;

import com.example.daggerexample.di.util.ViewModelKey;
import com.example.daggerexample.viewmodel.CountryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel.class)
    abstract ViewModel bindCountryViewModel(CountryViewModel viewModel);
}
