package com.example.daggerexample.di;

import android.app.Application;

import com.example.daggerexample.base.BaseApplication;
import com.example.daggerexample.di.module.ActivityBuildersModule;
import com.example.daggerexample.di.module.AppModule;
import com.example.daggerexample.di.module.ViewModelFactoryModule;
import com.example.daggerexample.di.module.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class,
        ViewModelFactoryModule.class,
        ViewModelModule.class
})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
        AppComponent build();

        @BindsInstance
        Builder application(Application application);

    }

}
