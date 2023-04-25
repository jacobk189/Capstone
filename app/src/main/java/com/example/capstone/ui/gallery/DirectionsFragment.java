package com.example.capstone.ui.gallery;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.MyLocationListener;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentDirectionsBinding;
import com.example.capstone.ui.home.HomeFragment;
import com.example.capstone.ui.home.HomeViewModel;
import com.example.capstone.ui.home.InfoFragment;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionsFragment extends Fragment {

    private FragmentDirectionsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    BluetoothAdapter ba;
    private GoogleMap mMap;

    private List<LatLng> directions = null;

    private int buildingNumber = 0;

    private BuildingModel currentBuilding;
    private final int MIN_DISTANCE = 15;
    private Runnable runnable = null;

    /*private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            float[] distance = new float[1];
            *//*Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    currentBuilding.getLatitude(), currentBuilding.getLongitude(), distance);*//*
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    44.444536, -88.070746, distance);
            Log.d("checknig", "" + distance[0]);

            if (distance[0] < 100) {
                GalleryFragment galleryFragment = new GalleryFragment();
                Bundle buildingID = new Bundle();
                buildingID.putString("building_number", "" + currentBuilding.getID());
                galleryFragment.setArguments(buildingID);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // Remove the location updates to stop the listener
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                //locationManager.unregisterLocationCallback(locationCallback);
                locationManager.removeUpdates(location1 -> {
                    LocationListener locationListener1 = locationListener;
                });

            }
        }
    };*/


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentDirectionsBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_directions, container, false);
        FrameLayout mapContainer = root.findViewById(R.id.m_container);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.m_container);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(mapContainer.getId(), supportMapFragment).commit();
        }

        Log.d("&&&&&&&&&", "inside of directions fragment");

        if (getArguments() != null) {
            Log.d("Inside get arugment not null", "");
            Fragment callingFragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            Log.d("callingFragment class", callingFragment.getClass().getSimpleName());
            if (callingFragment instanceof com.example.capstone.ui.gallery.DirectionsFragment) {
                Log.d("Inside infofragment called this", "");
                int id = getArguments().getInt("ID");
                buildingNumber = id;

            }
        }

        BuildingDB buildingDB = new BuildingDB(com.example.capstone.ui.gallery.DirectionsFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        for (BuildingModel building : buildingList) {
            if (building.getID() == buildingNumber) {
                currentBuilding = building;
                break;
            }
        }
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(""+currentBuilding.getName());


        String apiKey = getString(R.string.google_maps_key);

        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();

        supportMapFragment.getMapAsync((new OnMapReadyCallback() {
            @Override

            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, get the user's current location
                    googleMap.setMyLocationEnabled(true);
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    //MyLocationListener locationListener = new MyLocationListener(requireContext(),44.44467309202324, -88.07074847846474, "Gallery", currentBuilding.getID(), locationManager, getParentFragmentManager(), null, 0);
                    MyLocationListener locationListener = new MyLocationListener(requireContext(),currentBuilding.getLatitude(), currentBuilding.getLongitude(), "Gallery", currentBuilding.getID(), locationManager, getParentFragmentManager(), null, 0);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Log.d("My Location", myLocation.toString());

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16));

                    if (myLocation != null) {
                        // Use the user's current location to set the origin of the directions request
                        Log.d("Inside location not null", "here");
                        LatLng origin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        LatLng destination = new LatLng(currentBuilding.getLatitude(), currentBuilding.getLongitude());
                        //LatLng destination = new LatLng(44.44467309202324, -88.07074847846474);
                        Log.d("Your Location", origin.toString());
                        Log.d("Your destination", "Coordinates: " + destination + " Building name: " + buildingList.get(1).getName());

                        getDirections(origin, destination, googleMap);
                        /*locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER,0,0,location -> {
                            LocationListener locationListener1 = locationListener;
                        });*/


                       /* runnable = new Runnable() {
                            public void run() {
                                for (int i = 0; i < 120; i++) {
                                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                        return;
                                    }
                                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (lastKnownLocation != null) {
                                        double latitude = lastKnownLocation.getLatitude();
                                        double longitude = lastKnownLocation.getLongitude();
                                        Location destinationLocation = new Location("");
                                        destinationLocation.setLatitude(destination.latitude);
                                        destinationLocation.setLongitude(destination.longitude);
                                        if (lastKnownLocation != null && lastKnownLocation.distanceTo(destinationLocation) <= MIN_DISTANCE) {
                                            Log.d("Arrived", "You have arrived at the destination!");
                                            GalleryFragment galleryFragment = new GalleryFragment();
                                            Bundle buildingID = new Bundle();
                                            buildingID.putString("building_number",""+currentBuilding.getID());
                                            galleryFragment.setArguments(buildingID);
                                            FragmentManager fragmentManager = getParentFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                                            fragmentTransaction.setReorderingAllowed(true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            Thread thread = new Thread(runnable);
                                            thread.currentThread().interrupt();
                                        } else {
                                            Log.d("Location", "Latitude: " + lastKnownLocation.getLatitude() + ", Longitude: " + lastKnownLocation.getLongitude());
                                        }

                                    } else {
                                        Log.d("Location", "Last known location is null.");
                                    }
                                    try {
                                        Thread.sleep(1000); // wait for 1 second
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        Log.d("failed", e.toString());
                                    }
                                }
                            }
                        };

                        Thread thread = new Thread(runnable);
                        thread.start();*/

                    } else {
                        Log.d("null location: ", "null");
                    }
                } else {
                    // Permission is not granted, request it from the user
                    Log.d("something wrong with location access", "here");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

                /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        GalleryFragment galleryFragment = new GalleryFragment();
                        Bundle buildingID = new Bundle();
                        buildingID.putString("building_number",""+currentBuilding.getID());
                        galleryFragment.setArguments(buildingID);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });*/
            }
        }));



        return root;
    }




    private void getDirections(LatLng origin, LatLng destination, GoogleMap googleMap) {
        // Create a new instance of the Directions API
        GeoApiContext context = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build();

        PolylineOptions polylineOptions = null;

        // Create a new Directions API request
        DirectionsApiRequest request = DirectionsApi.newRequest(context)
                .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                .mode(TravelMode.WALKING);

        // Execute the request asynchronously
        request.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                // Draw the route on the map
                Log.d("in onresult of get directions", "1");
                if (result.routes != null && result.routes.length > 0) {
                    Log.d("in onresult of get directions", "2");
                    List<LatLng> path = new ArrayList<>();
                    if (result.routes[0].overviewPolyline != null) {
                        Log.d("in onresult of get directions", "3");
                        List<com.google.maps.model.LatLng> decodedPath = result.routes[0].overviewPolyline.decodePath();
                        Log.d("onResult", "decodedPath size: " + decodedPath.size());
                        if (decodedPath != null && decodedPath.size() > 0) {
                            Log.d("in onresult of get directions", "4");
                            for (com.google.maps.model.LatLng latLng : decodedPath) {
                                Log.d("in onresult of get directions", "5");
                                path.add(new LatLng(latLng.lat, latLng.lng));
                                Log.d("path", path.toString());
                            }
                        }
                    }
                    if (path.size() > 0) {
                        directions = path;
                        Log.d("directions contents in function" , directions.toString());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PolylineOptions polylineOptions = new PolylineOptions().addAll(path).color(Color.BLUE).width(5f);
                                googleMap.addPolyline(polylineOptions);
                                Log.d("on directionsreceived", "2");
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 17));
                                Log.d("on directionsreceived", "3");
                            }
                        });
                    }
                } else {
                    Log.d("onResult", "Result routes is null or has no elements.");
                }

            }

            @Override
            public void onFailure(Throwable e) {
                // Handle the error here
                Log.d("in failure of get directions", e.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Failed to get directions", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
