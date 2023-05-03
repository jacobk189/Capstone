package com.example.capstone;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstone.ui.gallery.GalleryFragment;
import com.example.capstone.ui.home.InfoFragment;

import java.util.ArrayList;

public class MyLocationListener implements LocationListener {

    private double destLatitude;
    private double destLongitude;
    private int id;
    private int type;
    private String fragmentId;
    private LocationManager locationManager;
    private FragmentManager fragmentManager;
    private ArrayList<BuildingModel> tour;
    private Context context;

    public MyLocationListener(Context context, double destLatitude, double destLongitude, String fragmentId, int id, LocationManager locationManager, FragmentManager fragmentManager, ArrayList<BuildingModel> tour, int type) {
        this.context = context;
        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
        this.fragmentId = fragmentId;
        this.id = id;
        this.locationManager = locationManager;
        this.fragmentManager = fragmentManager;
        this.tour = tour;
        this.type = type;
    }

    //anytime the users coordinates change at all this method is ran
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        float[] distance = new float[1];

        //check if the user is within 25 meters of the destination
        Location.distanceBetween(latitude, longitude, destLatitude, destLongitude, distance);
        if (distance[0] <= 25) { // distance in meters
            // open new fragment based on which fragment id was passed to the class
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (fragmentId) {
                case "Gallery":
                    GalleryFragment galleryFragment = new GalleryFragment();
                    Bundle buildingID = new Bundle();
                    buildingID.putInt("ID", id);;
                    galleryFragment.setArguments(buildingID);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case "Tour":
                    InfoFragment infoFragment = new InfoFragment();
                    Bundle tourCounter = new Bundle();
                    tourCounter.putInt("Tour counter", id);
                    tourCounter.putParcelableArrayList("tourList", (ArrayList<? extends Parcelable>) new ArrayList<BuildingModel>(tour));
                    //checking which tour is being ran
                    if (type == 1){
                        tourCounter.putInt("tourType", 1);
                    }
                    else if (type == 2){
                        tourCounter.putInt("tourType", 2);
                    }
                    else if (type == 3){
                        tourCounter.putInt("tourType", 3);
                    }
                    infoFragment.setArguments(tourCounter);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, infoFragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }
            stopLocationUpdates();
        }
    }

    // other methods of LocationListener

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Do nothing
    }
    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }
}
