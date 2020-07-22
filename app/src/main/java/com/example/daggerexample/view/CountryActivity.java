package com.example.daggerexample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryActivity extends AppCompatActivity {

    @BindView(R.id.countriesList)
    RecyclerView recyclerView;

    @BindView(R.id.list_error)
    TextView listError;

    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;


    private CountryViewModel viewModel;
    private CountryListAdapter mAdapter ;

    private List<CountryModel> countryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        viewModel.getCountries();

        initRecyclerView();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getCountries();
                refreshLayout.setRefreshing(false);
            }
        });

        observerViewModel();
    }

    private void initRecyclerView() {
        mAdapter = new CountryListAdapter(countryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void observerViewModel() {
        viewModel.countries.observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if (countryModels != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.updateCountries(countryModels);
                }
            }
        });
        viewModel.countryLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) {
                    listError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });
        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        listError.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
