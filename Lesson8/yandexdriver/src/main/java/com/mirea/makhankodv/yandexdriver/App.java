package com.mirea.makhankodv.yandexdriver;

import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "9377f11f-1f19-4777-8781-ee1e01eb604f";
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}