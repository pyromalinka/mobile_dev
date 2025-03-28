package com.mirea.makhankodv.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class CountryDetailsFragment extends Fragment {
    private ShareViewModel viewModel;
    private TextView nameText;
    private TextView detailsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        nameText = view.findViewById(R.id.countryNameText);
        detailsText = view.findViewById(R.id.countryDetailsText);
        
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        
        viewModel.getSelectedCountry().observe(getViewLifecycleOwner(), 
            countryName -> nameText.setText(countryName));
            
        viewModel.getCountryDetails().observe(getViewLifecycleOwner(),
            details -> detailsText.setText(details));
    }
} 