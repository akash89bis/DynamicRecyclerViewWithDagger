package com.example.daggerexample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerexample.R;
import com.example.daggerexample.viewmodel.CountryViewModel;

public class CountryActivity extends AppCompatActivity {

    private Button btnCallApi;
    private CountryViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        viewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        btnCallApi = findViewById(R.id.btn_callApi);

        btnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getCountries();
            }
        });
    }
}
