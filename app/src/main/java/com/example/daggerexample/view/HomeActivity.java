package com.example.daggerexample.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.example.daggerexample.R;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {

    private Button btnAddFood;
    private Button btnShowCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddFood = findViewById(R.id.btn_food);
        btnShowCountry = findViewById(R.id.btnCountry);

        btnAddFood.setOnClickListener(v -> {

            Intent newIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(newIntent);
        });

        btnShowCountry.setOnClickListener(v -> {
            Intent newIntent = new Intent(HomeActivity.this, CountryActivity.class);
            startActivity(newIntent);
        });
    }
}
