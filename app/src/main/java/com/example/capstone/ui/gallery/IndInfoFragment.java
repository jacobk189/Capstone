package com.example.capstone.ui.gallery;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.capstone.databinding.FragmentIndInfoBinding;
import com.example.capstone.ui.home.TourOneFragment;

import org.w3c.dom.Text;

import java.util.List;

public class IndInfoFragment extends Fragment {

    private FragmentIndInfoBinding binding;
    private int buildingNumber = 0;
    private TextView buildingName;

    private TextView information;

    private TextView history;

    private Button continueButton;

    private ImageView image;
    private BuildingModel currentBuilding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentIndInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //set backgrounbd color
        root.setBackgroundColor(getResources().getColor(R.color.background));

        Log.d("&&&&&&&&&", "inside of indinfo fragment");

        //getting the correct building id
        if (getArguments() != null) {
            Log.d("Inside get arugment not null", "");
            Fragment callingFragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            Log.d("callingFragment class", callingFragment.getClass().getSimpleName());
            if (callingFragment instanceof com.example.capstone.ui.gallery.IndInfoFragment) {
                Log.d("Inside infofragment called this", "");
                int id = getArguments().getInt("ID");
                buildingNumber = id;

            }
        }

        //making database and making the list of buildings
        BuildingDB buildingDB = new BuildingDB(com.example.capstone.ui.gallery.IndInfoFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        //matching building id to correct building then setting it to the current building
        for (BuildingModel building : buildingList) {
            if (building.getID() == buildingNumber) {
                currentBuilding = building;
                break;
            }
        }

        //set toolbar to right name
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(""+currentBuilding.getName());

        image = root.findViewById(R.id.indinfoimage);

        //seeing if the building has an info picture, if not it uses the default picture for the building
        try{
            int imageResourceId = getResources().getIdentifier("_"+currentBuilding.getID() + "_info_image", "drawable", getActivity().getPackageName());
            image.setImageResource(imageResourceId);
        } catch (Resources.NotFoundException e){
            int imageResourceId = getResources().getIdentifier("_"+currentBuilding.getID() + "_image", "drawable", getActivity().getPackageName());
            image.setImageResource(imageResourceId);
        }


        information = root.findViewById(R.id.Info);
        history = root.findViewById(R.id.History);

        //setting all information on page to be about the correct building
        information = root.findViewById(R.id.Info);
        String info = currentBuilding.getInfo();
        String address = currentBuilding.getAddress();
        String nickName = currentBuilding.getNickname();
        String fullInfo = info + "\n\nAddress: " + address + "\n\nAlso Known As: " + nickName;
        information.setText(fullInfo);

        history.setText(currentBuilding.getHistory());

        continueButton = root.findViewById(R.id.continueButton);

        //continue listener that wil take you back to fragment gallery when pressed
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GalleryFragment galleryFragment = new GalleryFragment();
                Bundle buildingID = new Bundle();
                buildingID.putInt("ID",currentBuilding.getID());
                galleryFragment.setArguments(buildingID);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        buildingName = root.findViewById(R.id.Name);

        buildingName.setText(currentBuilding.getName());
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
