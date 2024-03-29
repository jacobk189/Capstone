package com.example.capstone.ui.home;

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

import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentInfoBinding;

import java.util.List;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    private TextView buildingName;

    private TextView information;

    private TextView history;

    private ImageView image;

    private Button continueButton;

    private Button quitButton;

    private int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        root.setBackgroundColor(getResources().getColor(R.color.background));

        Log.d("&&&&&&&&&", "inside of infofragment");


        //get arguments from tour fragment so we know which tour is being ran and which part of the tour we are on
        Bundle receive = getArguments();
        count = receive.getInt("Tour counter");
        int tourType = receive.getInt("tourType");

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);

        //setting toolbar name depending on which tour the user is running
        if (tourType == 1){
            toolbar.setTitle("Academics");
        }
        else if (tourType == 2){
            toolbar.setTitle("Living Areas");
        }
        else if (tourType == 3){
            toolbar.setTitle("Areas of Interest");
        }

        //receving the tour list from the tour fragment
        List<BuildingModel> tourList = receive.getParcelableArrayList("tourList");

        buildingName = root.findViewById(R.id.Name);
        buildingName.setText(tourList.get(count).getName());


        image = root.findViewById(R.id.tourimage);
        int imageResourceId = getResources().getIdentifier("_" + tourList.get(count).getID() + "_image", "drawable", getActivity().getPackageName());
        image.setImageResource(imageResourceId);

        //getting right information for the building and displaying it to the screen
        information = root.findViewById(R.id.Info);
        String info = tourList.get(count).getInfo();
        String address = tourList.get(count).getAddress();
        String nickName = tourList.get(count).getNickname();
        String fullInfo = info + "\n\nAddress: " + address + "\n\nAlso Known As: " + nickName;
        information.setText(fullInfo);

        history = root.findViewById(R.id.History);
        history.setText(tourList.get(count).getHistory());

        continueButton = root.findViewById(R.id.continueButton);
        int nextCount = count + 1;

        //changing continue button to say finish if on last part of tour
        if (nextCount >= tourList.size()){
            continueButton.setText("Finish");
        }

        //listener for the continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("List size", String.valueOf(tourList.size()));
                Log.d("Next count", String.valueOf(nextCount));

                //if tour is finished then directback to home fragment if finsih is clicked
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

                    //checking which tour you are doing so it knows which fragment to return to when clicking continue
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

        quitButton = root.findViewById(R.id.quitButton);

        //quit button listener that will bring you back to the home fragment
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String completed = "tour done";
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
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
