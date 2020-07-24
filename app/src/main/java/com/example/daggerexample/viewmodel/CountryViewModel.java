package com.example.daggerexample.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerexample.di.DaggerApiComponentInterface;
import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.service.NetworkService;

import java.util.List;

import javax.inject.Inject;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CountryViewModel extends ViewModel {

    private MutableLiveData<List<CountryModel>> countries;
    private MutableLiveData<Boolean> countryLoadError;
    private MutableLiveData<Boolean> loading;

    @Inject
    public NetworkService networkService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public CountryViewModel(){
        super();
        DaggerApiComponentInterface.create().injectApiService(this);
        countries = new MutableLiveData<>();
        countryLoadError = new MutableLiveData<>();
        loading = new MutableLiveData<>();
    }

    public LiveData<List<CountryModel>> getCountriesListSuccess(){
        return countries;
    }

    public LiveData<Boolean> getCountriesError(){
        return countryLoadError;
    }

    public LiveData<Boolean> getLoading(){
        return loading;
    }

    public void getCountries(){

        loading.setValue(true);
        disposable.add(
                networkService.getCountryList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<CountryModel>>() {

                    @Override
                    public void onNext(List<CountryModel> countryModels) {
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

                    @Override
                    public void onComplete() {

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
