package com.example.daggerexample.view.rv;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.daggerexample.R;
import com.example.daggerexample.model.CountryModel;
import com.example.daggerexample.view.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private List<CountryModel> countriesList;
    private final SparseBooleanArray array = new SparseBooleanArray();

    public CountryListAdapter(List<CountryModel> countries) {
        this.countriesList = countries;
    }

    public void updateCountries(List<CountryModel> newCountries) {
        countriesList.clear();
        countriesList.addAll(newCountries);
        array.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(countriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView countryImage;

        @BindView(R.id.name)
        TextView countryName;

        @BindView(R.id.capital)
        TextView countryCapital;

        @BindView(R.id.item_switch)
        CheckBox itemSwitch;

        @BindView(R.id.item_lin_layout)
        LinearLayout itemLinLayout;

        @BindView(R.id.txtCountryCode)
        TextView txtCountryCode;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CountryModel country) {
            countryName.setText(country.getCountryName());
            countryCapital.setText(country.getCapital());
            GlideUtil.loadImage(countryImage, country.getFlag(), GlideUtil.getProgressDrawable(countryImage.getContext()));
            txtCountryCode.setText(country.getCountryCode());

            itemLinLayout.setOnClickListener(v -> {
                if(array.get(getAdapterPosition(),false)){
                    array.put(getAdapterPosition(),false);
                }else{
                    array.put(getAdapterPosition(),true);
                }
                notifyDataSetChanged();
            });

            if (array.get(getAdapterPosition())) {
                itemSwitch.setChecked(true);
                itemLinLayout.setBackgroundColor(Color.parseColor("#D733FF"));
            } else {
                itemSwitch.setChecked(false);
                itemLinLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            }

        }
    }
}
