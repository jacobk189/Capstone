package com.example.capstone.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentHomeBinding;
import com.example.capstone.databinding.FragmentTourOneBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class TourOneFragment extends Fragment {

    private FragmentTourOneBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentTourOneBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_tour_one, container, false);
        FrameLayout mapContainer = root.findViewById(R.id.m_container);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.m_container);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(mapContainer.getId(), supportMapFragment).commit();
        }

        BuildingDB buildingDB = new BuildingDB(TourOneFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();


        //upportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        supportMapFragment.getMapAsync((new OnMapReadyCallback() {
            @Override

            public void onMapReady(@NonNull GoogleMap googleMap) {
                GoogleMap mMap = googleMap;
                LatLng location = new LatLng(buildingList.get(1).getLatitude(), buildingList.get(1).getLongitude());
                String uri = "google.navigation:q=" + location.latitude + "," + location.longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

                //LatLng location = new LatLng(buildingList.get(9).getLatitude(), buildingList.get(9).getLongitude());
                float zoomLevel = 17.0f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        /*MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude+" X "+latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        googleMap.addMarker((markerOptions));*/

                    }
                });
            }
        }));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}