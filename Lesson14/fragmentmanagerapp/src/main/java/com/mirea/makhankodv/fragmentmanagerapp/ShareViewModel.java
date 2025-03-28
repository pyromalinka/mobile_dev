package com.mirea.makhankodv.fragmentmanagerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<String> selectedCountry = new MutableLiveData<>();
    private final MutableLiveData<String> countryDetails = new MutableLiveData<>();

    public void setSelectedCountry(String country, String details) {
        selectedCountry.setValue(country);
        countryDetails.setValue(details);
    }

    public LiveData<String> getSelectedCountry() {
        return selectedCountry;
    }

    public LiveData<String> getCountryDetails() {
        return countryDetails;
    }
} 