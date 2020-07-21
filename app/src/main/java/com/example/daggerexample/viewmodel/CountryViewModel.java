package com.example.daggerexample.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.service.ApiService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountryViewModel extends ViewModel {

    private ApiService apiService = ApiService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();
    private static final String TAG = "CountryViewModel";

    public void getCountries(){
        disposable.add(
                apiService.getCountryList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        Log.e(TAG, "onSuccess: "+countryModels.size() );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e);
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
