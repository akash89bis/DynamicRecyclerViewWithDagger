package com.example.daggerexample.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.daggerexample.R;
import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.view.rv.CountryListAdapter;
import com.example.daggerexample.viewmodel.CountryViewModel;
import com.example.daggerexample.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.Disposable;

public class CountryFragment extends DaggerFragment {

    @BindView(R.id.countriesList)
    RecyclerView recyclerView;

    @BindView(R.id.list_error)
    TextView listError;

    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.btn_click)
    Button btnClick;

    private CountryViewModel viewModel;
    private CountryListAdapter mAdapter ;
    private List<CountryModel> countryList = new ArrayList<>();
    private Disposable subscribe = null;

    @Inject
    ViewModelFactory providerFactory;

    private static final String TAG = "CountryFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), providerFactory).get(CountryViewModel.class);
        viewModel.getCountries();

        initRecyclerView();

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.getCountries();
            refreshLayout.setRefreshing(false);
        });

        observerViewModel();
        setupItemClick();
    }

    private void setupItemClick() {
        subscribe = mAdapter.getAddedCountryObservable()
                .subscribe(countryModel ->{
                        viewModel.countryAdded(countryModel);
                        Toast.makeText(getActivity(), "Country Selected --"+countryModel.getCountryName(), Toast.LENGTH_LONG).show();
                });
    }

    private void initRecyclerView() {
        mAdapter = new CountryListAdapter(countryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void observerViewModel() {
        viewModel.getCountriesListSuccess().observe(this, countryModels -> {
            if (countryModels != null) {
                recyclerView.setVisibility(View.VISIBLE);
                mAdapter.updateCountries(countryModels);
            }
        });
        viewModel.getCountriesError().observe(this, isError -> {
                if (isError != null) {
                    listError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }

        });
        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    listError.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getCountryCodeSum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e(TAG, "onChanged: integer value ---"+integer);
                btnClick.setText(String.valueOf(integer));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: called" );
        subscribe.dispose();
    }
}
