package com.example.daggerexample.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.daggerexample.R;
import com.example.daggerexample.view.fragment.OptionsFragment;

import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new OptionsFragment()).commit();
    }
}
