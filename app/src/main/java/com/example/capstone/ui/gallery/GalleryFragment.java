package com.example.capstone.ui.gallery;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentGalleryBinding;
import com.example.capstone.ui.home.TourOneFragment;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private String buildingNumber = null;
    private TextView buildingName;
    private Button directionsBtn;
    private Button informationBtn;
    private BuildingModel currentBuilding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            buildingNumber = getArguments().getString("building_number");
            Log.d("here", "ID: " + buildingNumber);
        }

        BuildingDB buildingDB = new BuildingDB(GalleryFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        for (BuildingModel building : buildingList) {
            if (building.getID() == Integer.parseInt(buildingNumber)) {
                currentBuilding = building;
                break;
            }
        }

        buildingName = root.findViewById(R.id.BuildingName);
        buildingName.setText(currentBuilding.getName());

        directionsBtn = root.findViewById(R.id.Directions);
        informationBtn = root.findViewById(R.id.Information);

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle directions = new Bundle();
                directions.putInt("ID", currentBuilding.getID());

                DirectionsFragment directionsFragment = new DirectionsFragment();
                directionsFragment.setArguments(directions);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, directionsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle information = new Bundle();
                information.putInt("ID", currentBuilding.getID());

                DirectionsFragment directionsFragment = new DirectionsFragment();
                directionsFragment.setArguments(information);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}