package com.example.capstone.ui.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentTourOneBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;

public class TourOneFragment extends Fragment {

    private FragmentTourOneBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    BluetoothAdapter ba;
    private GoogleMap mMap;

    private List<LatLng> directions = null;




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

        Log.d("&&&&&&&&&", "inside of touronefragment");

        BuildingDB buildingDB = new BuildingDB(TourOneFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        String apiKey = getString(R.string.google_maps_key);

        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();

        supportMapFragment.getMapAsync((new OnMapReadyCallback() {
            @Override

            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                /*Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(-35.016, 143.321),
                                new LatLng(-34.747, 145.592),
                                new LatLng(-34.364, 147.891),
                                new LatLng(-33.501, 150.217),
                                new LatLng(-32.306, 149.248),
                                new LatLng(-32.491, 147.309)));

                // Position the map's camera near Alice Springs in the center of Australia,
                // and set the zoom factor so most of Australia shows on the screen.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));*/

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, get the user's current location
                    googleMap.setMyLocationEnabled(true);
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 15));

                    if (myLocation != null) {
                        // Use the user's current location to set the origin of the directions request
                        Log.d("In permission check:", "here");
                        //LatLng origin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        LatLng origin = new LatLng(buildingList.get(6).getLatitude(), buildingList.get(6).getLongitude());
                        LatLng destination = new LatLng(buildingList.get(1).getLatitude(), buildingList.get(1).getLongitude());
                        Log.d("origin", origin.toString());
                        Log.d("destination", destination.toString());
                        getDirections(origin, destination, new DirectionsCallback() {
                            @Override
                            public void onDirectionsReceived(List<LatLng> directions) {
                                Log.d("Directions returned", directions.toString());
                                PolylineOptions polylineOptions = new PolylineOptions().addAll(directions).color(Color.BLUE).width(5f);
                                Log.d("polyline options", polylineOptions.getPoints().toString());
                                Log.d("on directionsreceived", "1");
                                Log.d("mmap", mMap.toString());
                                googleMap.addPolyline(polylineOptions);
                                Log.d("on directionsreceived", "2");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14));
                                Log.d("on directionsreceived", "3");
                                /*Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                                        .clickable(true)
                                        .add(
                                                new LatLng(-35.016, 143.321),
                                                new LatLng(-34.747, 145.592),
                                                new LatLng(-34.364, 147.891),
                                                new LatLng(-33.501, 150.217),
                                                new LatLng(-32.306, 149.248),
                                                new LatLng(-32.491, 147.309)));

                                // Position the map's camera near Alice Springs in the center of Australia,
                                // and set the zoom factor so most of Australia shows on the screen.
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));*/
                            }
                        });



                    } else {
                        // Handle the case where the user's location is not available
                        Log.d("null location: ", "null");
                    }
                } else {
                    // Permission is not granted, request it from the user
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }



               /* Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(-35.016, 143.321),
                                new LatLng(-34.747, 145.592),
                                new LatLng(-34.364, 147.891),
                                new LatLng(-33.501, 150.217),
                                new LatLng(-32.306, 149.248),
                                new LatLng(-32.491, 147.309)));

                // Position the map's camera near Alice Springs in the center of Australia,
                // and set the zoom factor so most of Australia shows on the screen.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));*/

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

                    }
                });
            }
        }));



        return root;
    }

    public interface DirectionsCallback {
        void onDirectionsReceived(List<com.google.android.gms.maps.model.LatLng> directions);
    }

    private void getDirections(LatLng origin, LatLng destination, DirectionsCallback callback) {
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
                        callback.onDirectionsReceived(path); // invoke the callback with the path
                        directions = path;
                        Log.d("directions contents in function" , directions.toString());
                    }
                } else {
                    Log.d("onResult", "Result routes is null or has no elements.");
                }

            }

            @Override
            public void onFailure(Throwable e) {
                // Handle the error here
                Log.d("in failure of get directions", ";(");
                Log.d("in on failure", e.toString());
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