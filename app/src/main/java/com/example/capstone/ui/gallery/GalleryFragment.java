package com.example.capstone.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentGalleryBinding;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private int buildingNumber = 0;
    private TextView buildingName;
    private Button directionsBtn;
    private Button informationBtn;

    private ImageView image;
    private BuildingModel currentBuilding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.setBackgroundColor(getResources().getColor(R.color.background));



        if (getArguments() != null) {

            if(getArguments().containsKey("building_number")){
                String ID = getArguments().getString("building_number");
                buildingNumber = Integer.parseInt(ID);
                Log.d("From homefragment", "ID: " + buildingNumber);
            }
            else if(getArguments().containsKey("ID")){
                int id = getArguments().getInt("ID");
                buildingNumber = id;
            }
        }

        BuildingDB buildingDB = new BuildingDB(GalleryFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        for (BuildingModel building : buildingList) {
            if (building.getID() == buildingNumber) {
                currentBuilding = building;
                break;
            }
        }

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(""+currentBuilding.getName());

        buildingName = root.findViewById(R.id.BuildingName);
        buildingName.setText(currentBuilding.getName());

        image = root.findViewById(R.id.galleryimage);
        int imageResourceId = getResources().getIdentifier("_"+currentBuilding.getID() + "_image", "drawable", getActivity().getPackageName());
        image.setImageResource(imageResourceId);

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
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle info = new Bundle();
                info.putInt("ID", currentBuilding.getID());

                IndInfoFragment indInfoFragment = new IndInfoFragment();
                indInfoFragment.setArguments(info);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, indInfoFragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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