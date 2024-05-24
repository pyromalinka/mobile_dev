package com.mirea.makhankodv.mireaproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.mirea.makhankodv.mireaproject.databinding.FragmentPlacesBinding;

public class PlacesFragment extends Fragment {

    private MapView mapView = null;
    MyLocationNewOverlay locationNewOverlay;
    private FragmentPlacesBinding binding;
    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {});
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.mapView;

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE && v.getParent() != null) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            funcAll();
        } else {
            requestPermissionLauncher.launch(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION});
        }

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(requireContext(),
                PreferenceManager.getDefaultSharedPreferences(requireContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }

    private void setMarker(String nameMarker, GeoPoint point) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Snackbar.make(requireView(), nameMarker, Snackbar.LENGTH_LONG).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);
        marker.setIcon(ResourcesCompat.getDrawable(getResources(),
                org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle(nameMarker);
    }
    public void funcAll(){
        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794316, 37.553706);
        mapController.setCenter(startPoint);


        locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(requireContext()),mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);


        CompassOverlay compassOverlay = new CompassOverlay(requireContext(),
                new InternalCompassOrientationProvider(requireContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final Context context = this.requireContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        setMarker("Дизайн завод\n" +
                "Большая Новодмитровская улица, 36с2 - " +
                "Развлекательный центраренда площадок для культурных мероприятий", new GeoPoint(55.805187, 37.585028));
        setMarker("ВТБ Арена\n" +
                "Ленинградский просп., 36, Москва - " +
                "Стадион, спортивный комплекс", new GeoPoint(55.791472, 37.560465));
        setMarker("Экспериментаниум\n" +
                "Ленинградский проспект, 80к11, Москва - " +
                "Интерактивный музей", new GeoPoint(55.808756, 37.511053));
    }

}