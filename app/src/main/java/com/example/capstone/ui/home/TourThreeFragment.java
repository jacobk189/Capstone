package com.example.capstone.ui.home;

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
import com.example.capstone.databinding.FragmentHomeBinding;
import com.example.capstone.databinding.FragmentTourThreeBinding;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class TourThreeFragment extends Fragment {

    private FragmentTourThreeBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    private List<LatLng> directions = null;

    private int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentTourThreeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_tour_one, container, false);
        FrameLayout mapContainer = root.findViewById(R.id.m_container);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.m_container);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(mapContainer.getId(), supportMapFragment).commit();
        }

        Log.d("&&&&&&&&&", "inside of tourtwofragment");

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Areas of Interest");

        if (getArguments() != null) {
            Log.d("Inside get arugment not null", "");
            Fragment callingFragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            Log.d("callingFragment class", callingFragment.getClass().getSimpleName());
            if (callingFragment instanceof TourThreeFragment) {
                Log.d("Inside infofragment called this", "");
                int newCount = getArguments().getInt("Next Count");
                count = newCount;
            }
        }

        BuildingDB buildingDB = new BuildingDB(TourThreeFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        //List<BuildingModel> academicTour = new ArrayList<>(Arrays.asList(buildingList.get(28), buildingList.get(18), buildingList.get(19), buildingList.get(7), buildingList.get(3), buildingList.get(22)));
        Log.d("checking count ", "akdsflkhadslkfj:"+count);
        ArrayList<BuildingModel> areasofInterestTour = new ArrayList<>(Arrays.asList(buildingList.get(28), buildingList.get(15), buildingList.get(14), buildingList.get(10), buildingList.get(6), buildingList.get(23), buildingList.get(26)));

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

                    //MyLocationListener locationListener = new MyLocationListener(requireContext(),44.44467309202324, -88.07074847846474, "Tour", count, locationManager, getParentFragmentManager(), areasofInterestTour, 3);
                    MyLocationListener locationListener = new MyLocationListener(requireContext(), areasofInterestTour.get(count).getLatitude(), areasofInterestTour.get(count).getLongitude(), "Tour", count, locationManager, getParentFragmentManager(), areasofInterestTour, 3);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Log.d("My Location", myLocation.toString());

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16));

                    if (myLocation != null) {
                        // Use the user's current location to set the origin of the directions request
                        Log.d("Inside location not null", "here");
                        LatLng origin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        LatLng destination = new LatLng(areasofInterestTour.get(count).getLatitude(), areasofInterestTour.get(count).getLongitude());
                        //LatLng destination = new LatLng(44.444648402445374, -88.07028337312235);
                        MarkerOptions markerOptions = new MarkerOptions().position(destination).title("Marker Title");
                        googleMap.addMarker(markerOptions);
                        Log.d("Your Location", origin.toString());
                        Log.d("Your destination", "Coordinates: " + destination + " Building name: " + buildingList.get(1).getName());

                        getDirections(origin, destination, googleMap);

                    } else {
                        // Handle the case where the user's location is not available
                        Log.d("null location: ", "null");
                    }
                } else {
                    // Permission is not granted, request it from the user
                    Log.d("something wrong with location access", "here");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

                        Bundle tourOne = new Bundle();
                        tourOne.putInt("Tour counter",count);
                        tourOne.putParcelableArrayList("tourList", (ArrayList<? extends Parcelable>) new ArrayList<BuildingModel>(areasofInterestTour));
                        tourOne.putInt("tourType", 3);

                        InfoFragment infoFragment = new InfoFragment();
                        infoFragment.setArguments(tourOne);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, infoFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }
                });
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
