package com.example.daggerexample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerexample.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel{

    private MutableLiveData<List<FoodItem>> mutableLiveDataFoodListNewAddition;
    private List<FoodItem> foodItemList;
    private FoodItem mFoodItem;
   private MutableLiveData<List<FoodItem>> mutableLiveDataFinalFoodList;

    public MainViewModel() {
        mutableLiveDataFoodListNewAddition = new MutableLiveData<>();
        mutableLiveDataFinalFoodList = new MutableLiveData<>();
        init();
    }

    public LiveData<List<FoodItem>> getFoodListMutableLiveDataNewAddition() {
        return mutableLiveDataFoodListNewAddition;
    }

    public LiveData<List<FoodItem>> getFoodOnOrder() {
        return mutableLiveDataFinalFoodList;
    }

    private void init(){
        populateList();
        mutableLiveDataFoodListNewAddition.setValue(foodItemList);
    }

    private void populateList(){
        foodItemList = new ArrayList<>();
        mFoodItem = new FoodItem("","");
        foodItemList.add(mFoodItem);
    }

    public void addNewRow() {
        mFoodItem = new FoodItem("", "");
        foodItemList.add(mFoodItem);
        mutableLiveDataFoodListNewAddition.setValue(foodItemList);
    }

    public void updateRowData(String foodItem, int position) {
        foodItemList.get(position).setFoodName(foodItem);
    }

    public void getFoodDetails() {
        mutableLiveDataFinalFoodList.setValue(foodItemList);
    }

    public void updateDate(String date, int position) {
        foodItemList.get(position).setDate(date);
    }
}
