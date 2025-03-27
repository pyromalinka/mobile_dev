package com.mirea.makhankodv.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryViewHolder> {
    private List<Country> countries;
    private Context context;

    public CountryRecyclerViewAdapter(List<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View recyclerViewItem = LayoutInflater.from(context)
                .inflate(R.layout.country_item_view, parent, false);
        return new CountryViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        Country country = this.countries.get(position);
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(country.getFlagName(), "drawable", pkgName);

        holder.getFlagView().setImageResource(resID);
        holder.getCountryNameView().setText(country.getCountryName());
        holder.getPopulationView().setText("Population: " + country.getPopulation());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Clicked: " + country.getCountryName(),
                    Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return this.countries.size();
    }
} 