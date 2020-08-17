package com.example.daggerexample.di.module;

import com.example.daggerexample.view.CountryActivity;
import com.example.daggerexample.view.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract HomeActivity contributesHomeActivity();

    @ContributesAndroidInjector
    abstract CountryActivity contributesCountryActivity();
}
