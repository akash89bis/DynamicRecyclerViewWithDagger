package com.example.daggerexample.di.module;

import com.example.daggerexample.view.fragment.CountryFragment;
import com.example.daggerexample.view.fragment.OptionsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract OptionsFragment contributesOptionsFragment();

    @ContributesAndroidInjector
    abstract CountryFragment contributesCountryFragment();

}
