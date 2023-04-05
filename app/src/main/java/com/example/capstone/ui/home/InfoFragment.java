package com.example.capstone.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        Log.d("&&&&&&&&&", "inside of infofragment");

        Bundle receive = getArguments();
        int count = receive.getInt("Tour counter");
        Toast.makeText(getContext(), "This is the count: "+count, Toast.LENGTH_SHORT).show();
        count += 1;

        BuildingDB buildingDB = new BuildingDB(InfoFragment.this);
        List<BuildingModel> buildingList = buildingDB.showbuildings();

        continueButton = root.findViewById(R.id.continueButton);
        int nextCount = count;
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle test = new Bundle();
                test.putInt("Next Count", nextCount);

                TourOneFragment tourOneFragment = new TourOneFragment();
                tourOneFragment.setArguments(test);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourOneFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        buildingName = root.findViewById(R.id.Name);

        buildingName.setText(buildingList.get(0).getName()+" "+buildingList.get(0).getID());


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
