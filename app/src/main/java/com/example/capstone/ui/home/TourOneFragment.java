package com.example.capstone.ui.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

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
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;

public class TourOneFragment extends Fragment {

    private FragmentTourOneBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    BluetoothAdapter ba;


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

        String apiKey = getString(R.string.google_maps_key);

        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();

        supportMapFragment.getMapAsync((new OnMapReadyCallback() {
            @Override

            public void onMapReady(@NonNull GoogleMap googleMap) {
                GoogleMap mMap = googleMap;
                LatLng location = new LatLng(buildingList.get(0).getLatitude(), buildingList.get(0).getLongitude());

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(location)
                        .title("Destination");
                googleMap.addMarker(markerOptions);

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, get the user's current location
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (myLocation != null) {
                        // Use the user's current location to set the origin of the directions request
                        Log.d("In permission check:", "here");
                        LatLng origin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        LatLng destination = new LatLng(buildingList.get(9).getLatitude(), buildingList.get(9).getLongitude());
                        DirectionsApiRequest directions = DirectionsApi.newRequest(geoApiContext);
                        directions.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
                        directions.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));
                        directions.mode(TravelMode.DRIVING); // Set the travel mode to driving
                        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {
                            @Override
                            public void onResult(DirectionsResult result) {
                                // Handle the directions result here
                                // You can access the steps of the directions with result.routes[0].legs[0].steps
                                Log.d("inside onresult", "here");
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                // Handle the failure here
                                Log.d("inside onfailure", "here");
                            }
                        });

                        /*try {
                            directions.await();
                        } catch (ApiException | InterruptedException | IOException e) {
                            throw new RuntimeException(e);
                        }*/

                    } else {
                        // Handle the case where the user's location is not available
                        Log.d("null location: ", "null");
                    }
                } else {
                    // Permission is not granted, request it from the user
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

                float zoomLevel = 17.0f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

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