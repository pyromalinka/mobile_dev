package com.mirea.makhankodv.fragmentmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ShareViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ShareViewModel.class);

        if (savedInstanceState == null) {
            CountriesListFragment listFragment = new CountriesListFragment();
            CountryDetailsFragment detailsFragment = new CountryDetailsFragment();
            
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.listContainer, listFragment)
                    .add(R.id.detailsContainer, detailsFragment)
                    .commit();
        }
    }
} 