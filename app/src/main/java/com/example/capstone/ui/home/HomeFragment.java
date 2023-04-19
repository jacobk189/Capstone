package com.example.capstone.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.BluetoothReceiver;
import com.example.capstone.BuildingDB;
import com.example.capstone.BuildingModel;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentHomeBinding;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;

import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button tourOneButton;
    private Button tourTwoButton;
    private CheckBox btCheckbox;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private BluetoothReceiver bluetoothReceiver;

    /*private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (permissions.get(Manifest.permission.BLUETOOTH_ADMIN)) {
                    // Permission is granted, start discovery
                    bluetoothAdapter.startDiscovery();
                } else {
                    // Permission is not granted, show a message to the user
                    Toast.makeText(getContext(), "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
                }
            });*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("&&&&&&&&&", "inside of homefragment 1");

        tourOneButton = root.findViewById(R.id.tour1button);
        tourTwoButton = root.findViewById(R.id.tour2button);

        //BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BluetoothManager.class);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /*if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(root.getContext(), "inside enable check :(", Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // checking if app has bluetooth permission
            if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted, start discovery
                bluetoothAdapter.startDiscovery();
            } else {
                // Permission is not granted, request it
                //requestPermissionLauncher.launch(new String[]{Manifest.permission.BLUETOOTH_ADMIN});
            }
        }*/

        if (getArguments() != null) {
            Log.d("Inside get arugment not null", "");
            Fragment callingFragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            Log.d("callingFragment class", callingFragment.getClass().getSimpleName());
            if (callingFragment instanceof HomeFragment) {
                String message = getArguments().getString("Finished");
                //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        bluetoothReceiver = new BluetoothReceiver();
        getActivity().registerReceiver(bluetoothReceiver, filter);

        Log.d("&&&&&&&&&", "inside of homefragment 2");


        tourOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("&&&&&&&&&", "inside of homefragment 3");
                TourOneFragment tourOne = new TourOneFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourOne);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tourTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourTwoFragment tourTwo = new TourTwoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourTwo);
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
