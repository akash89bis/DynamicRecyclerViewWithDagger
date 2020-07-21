package com.example.daggerexample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daggerexample.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnAddFood;
    private Button btnShowCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddFood = findViewById(R.id.btn_food);
        btnShowCountry = findViewById(R.id.btnCountry);

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(newIntent);
            }
        });

        btnShowCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(HomeActivity.this, CountryActivity.class);
                startActivity(newIntent);
            }
        });
    }
}
