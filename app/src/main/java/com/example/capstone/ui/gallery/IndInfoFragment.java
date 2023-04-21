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
    private BuildingModel currentBuilding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentIndInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("&&&&&&&&&", "inside of indinfo fragment");

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

        BuildingDB buildingDB = new BuildingDB(com.example.capstone.ui.gallery.IndInfoFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        for (BuildingModel building : buildingList) {
            if (building.getID() == buildingNumber) {
                currentBuilding = building;
                break;
            }
        }

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(""+currentBuilding.getName());

        information = root.findViewById(R.id.Info);
        history = root.findViewById(R.id.History);

        information.setText(currentBuilding.getInfo());
        history.setText(currentBuilding.getHistory());

        continueButton = root.findViewById(R.id.continueButton);
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
