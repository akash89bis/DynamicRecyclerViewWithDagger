package com.example.daggerexample.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.daggerexample.R;
import com.example.daggerexample.base.BaseFragment;
import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.view.rv.CountryListAdapter;
import com.example.daggerexample.viewmodel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class CountryFragment extends BaseFragment<CountryViewModel> {

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

    private CountryListAdapter mAdapter ;
    private List<CountryModel> countryList = new ArrayList<>();
    private Disposable subscribe = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        viewModel.getCountries();

        initRecyclerView();

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.getCountries();
            refreshLayout.setRefreshing(false);
        });

        observerViewModel();
        setupItemClick();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_country;
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

        viewModel.getCountryCodeSum().observe(this, integer -> btnClick.setText(String.valueOf(integer)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscribe.dispose();
    }
}
