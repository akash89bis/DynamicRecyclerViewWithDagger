package com.example.daggerexample.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerexample.viewmodel.ViewModelFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<VM extends ViewModel> extends DaggerFragment {

    protected VM viewModel;

    @Inject
    ViewModelFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentView(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
    }

    protected abstract int getFragmentView();

    @SuppressWarnings("unchecked")
    private VM getViewModel() {
        final Type[] types = ((ParameterizedType)
                Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments();
        return ViewModelProviders.of(this, providerFactory).get((Class<VM>) types[0]);
    }

}
