package com.mirea.makhankodv.recyclerviewapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryViewHolder extends RecyclerView.ViewHolder {
    private ImageView flagView;
    private TextView countryNameView;
    private TextView populationView;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.flagView = itemView.findViewById(R.id.imageView);
        this.countryNameView = itemView.findViewById(R.id.textViewCountryName);
        this.populationView = itemView.findViewById(R.id.textViewPopulation);
    }

    public ImageView getFlagView() {
        return flagView;
    }

    public TextView getCountryNameView() {
        return countryNameView;
    }

    public TextView getPopulationView() {
        return populationView;
    }
}