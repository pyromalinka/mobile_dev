package com.mirea.makhankodv.resultapifragmentapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    public void selectItem(String item) {
        selectedItem.setValue(item);
    }

    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }
} 