package com.example.daggerexample.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerexample.di.DaggerApiComponentInterface;
import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.service.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountryViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public CountryViewModel(){
        super();
        DaggerApiComponentInterface.create().injectApiService(this);
    }

    public void getCountries(){

        loading.setValue(true);
        disposable.add(
                apiService.getCountryList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        countryLoadError.setValue(false);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
