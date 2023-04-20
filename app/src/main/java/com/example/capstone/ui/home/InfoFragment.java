package com.example.capstone.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentInfoBinding;

import java.util.List;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    private TextView buildingName;

    private Button continueButton;

    private int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        Log.d("&&&&&&&&&", "inside of infofragment");

        Bundle receive = getArguments();
        //assert receive != null;
        count = receive.getInt("Tour counter");
        int tourType = receive.getInt("tourType");
        //Toast.makeText(getContext(), "This is the count: "+count, Toast.LENGTH_SHORT).show();

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);

        if (tourType == 1){
            toolbar.setTitle("Academics");
        }
        else if (tourType == 2){
            toolbar.setTitle("Living Areas");
        }
        else if (tourType == 3){
            toolbar.setTitle("Areas of Interest");
        }


        List<BuildingModel> tourList = receive.getParcelableArrayList("tourList");

        continueButton = root.findViewById(R.id.continueButton);
        int nextCount = count + 1;
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("List size", String.valueOf(tourList.size()));
                Log.d("Next count", String.valueOf(nextCount));

                if (nextCount >= tourList.size()){
                    count = 0;
                    String completed = "Academic Tour Completed";
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle completedTour = new Bundle();
                    completedTour.putString("Finished",completed);
                    homeFragment.setArguments(completedTour);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, homeFragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else {
                    Bundle count = new Bundle();
                    count.putInt("Next Count", nextCount);

                    if (tourType == 1){
                        TourOneFragment tourOneFragment = new TourOneFragment();
                        tourOneFragment.setArguments(count);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourOneFragment);
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if (tourType == 2){
                        TourTwoFragment tourTwoFragment = new TourTwoFragment();
                        tourTwoFragment.setArguments(count);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourTwoFragment);
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if (tourType == 3){
                        TourThreeFragment tourThreeFragment = new TourThreeFragment();
                        tourThreeFragment.setArguments(count);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourThreeFragment);
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        buildingName = root.findViewById(R.id.Name);
        buildingName.setText(tourList.get(count).getName());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
