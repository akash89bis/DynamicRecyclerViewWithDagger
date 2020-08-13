package com.example.daggerexample.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.network.ApiRepository;

import java.util.ArrayList;
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
    private List<CountryModel> listCountry;
    private static final String TAG = "CountryViewModel";
    private MutableLiveData<Integer> sumCountryCode;


    private ApiRepository apiRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    CountryViewModel(ApiRepository apiRepository){
        super();
        countries = new MutableLiveData<>();
        countryLoadError = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        listCountry = new ArrayList<>();
        sumCountryCode = new MutableLiveData<>();
        this.apiRepository = apiRepository;
        Log.e(TAG, "CountryViewModel: Constructor injected" );
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

    public LiveData<Integer> getCountryCodeSum(){
        return sumCountryCode;
    }

    public void getCountries(){

        listCountry.clear();
        loading.setValue(true);
        disposable.add(
                apiRepository.getCountryList()
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
        listCountry.clear();
    }

    public void countryAdded(CountryModel countryModel) {
        int sum = 0;
        if (listCountry.contains(countryModel))
            listCountry.remove(countryModel);
        else
            listCountry.add(countryModel);

        for(CountryModel country: listCountry){
            sum = sum +Integer.parseInt(country.getCountryCode());
        }

        sumCountryCode.setValue(sum);
    }
}
