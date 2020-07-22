package com.example.daggerexample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daggerexample.R;
import com.example.daggerexample.model.FoodItem;
import com.example.daggerexample.view.rv.FoodItemRecyclerView;
import com.example.daggerexample.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodItemRecyclerView.OnFoodItemEnteredListener,LifecycleOwner {

    private RecyclerView recyclerView;
    private FoodItemRecyclerView mAdapter;
    private Button btn;
    private Button btnOrder;
    private MainViewModel viewModel;
    private MainActivity context;
    private static final String TAG = "MainActivity";

    private List<FoodItem> foodItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        initRecyclerView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * First create the bean object collecting all the data from the fields
                * and pass it to updatelist to viewmodel.
                * */
                viewModel.addNewRow();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getFoodDetails();
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv);
        btn = findViewById(R.id.button2);
        btnOrder = findViewById(R.id.btnOrder);
        context = this;
        viewModel = ViewModelProviders.of(context).get(MainViewModel.class);
        viewModel.getFoodListMutableLiveDataNewAddition().observe(this, foodListObserver);
        viewModel.getFoodOnOrder().observe(this, foodDetailsObserver);
    }

    private void initRecyclerView() {
        mAdapter = new FoodItemRecyclerView(foodItemList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setInterfaceListener(this);
    }


    Observer<List<FoodItem>> foodListObserver = new Observer<List<FoodItem>>() {
        @Override
        public void onChanged(List<FoodItem> foodItems) {
            mAdapter.notifyData(foodItems);
        }
    };

    Observer<List<FoodItem>> foodDetailsObserver = new Observer<List<FoodItem>>() {
        @Override
        public void onChanged(List<FoodItem> foodItems) {

            StringBuilder foodDisplay = new StringBuilder("Food name --- \n");
            for(FoodItem food : foodItems) {
                if(!food.getFoodName().isEmpty() && food.getDate()!=null) {
                    foodDisplay.append(food.getFoodName());
                    foodDisplay.append(" : " + food.getDate());
                    foodDisplay.append("\n");
                }
            }
            Toast.makeText(MainActivity.this, foodDisplay , Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onFoodItemEntered(String foodItem, int position) {
        viewModel.updateRowData(foodItem,position);
    }

    @Override
    public void onDateEntered(String date, int position) {
        viewModel.updateDate(date,position);
    }
}
