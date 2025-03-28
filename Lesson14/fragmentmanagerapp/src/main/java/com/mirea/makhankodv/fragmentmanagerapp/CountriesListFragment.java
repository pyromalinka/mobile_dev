package com.mirea.makhankodv.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class CountriesListFragment extends Fragment {
    private ShareViewModel viewModel;
    private final String[] countries = {"Россия", "США", "Китай", "Япония", "Германия", "Франция"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_countries_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        
        ListView listView = view.findViewById(R.id.countriesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_list_item_1, countries);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String countryName = countries[position];
            String details = getCountryDetails(countryName);
            viewModel.setSelectedCountry(countryName, details);
        });
    }

    private String getCountryDetails(String countryName) {
        switch (countryName) {
            case "Россия":
                return "Самая большая страна в мире по территории. Столица - Москва.";
            case "США":
                return "Федеративное государство в Северной Америке. Столица - Вашингтон.";
            case "Китай":
                return "Самая населенная страна мира. Столица - Пекин.";
            case "Япония":
                return "Островное государство в Восточной Азии. Столица - Токио.";
            case "Германия":
                return "Государство в Центральной Европе. Столица - Берлин.";
            case "Франция":
                return "Государство в Западной Европе. Столица - Париж.";
            default:
                return "Информация отсутствует";
        }
    }
} 