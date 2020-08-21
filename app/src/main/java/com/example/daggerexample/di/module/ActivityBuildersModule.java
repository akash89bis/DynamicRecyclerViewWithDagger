package com.example.daggerexample.di.module;

import com.example.daggerexample.view.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {FragmentBuildersModule.class}
            )
    abstract HomeActivity contributesHomeActivity();

}
