package com.example.daggerexample.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.example.daggerexample.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule  {

    @Binds
    public abstract ViewModelProvider.Factory bindsFactory(ViewModelFactory factory);
}
