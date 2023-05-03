package com.example.capstone.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.MyLocationListener;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Button tourOneButton;

    private Button tourTwoButton;

    private Button tourThreeButton;

    private CheckBox btCheckbox;

    private TextView title;

    private ImageView tiktok;

    private ImageView instagram;

    private ImageView facebook;

    private ImageView youtube;

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter bluetoothAdapter;

    private Set<BluetoothDevice> pairedDevices;

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.setBackgroundColor(getResources().getColor(R.color.background));

        Log.d("&&&&&&&&&", "inside of homefragment 1");

        title = root.findViewById(R.id.Name);
        title.setText("Norby Nav");

        tourOneButton = root.findViewById(R.id.tour1button);
        tourTwoButton = root.findViewById(R.id.tour2button);
        tourThreeButton = root.findViewById(R.id.tour3button);

        tiktok = root.findViewById(R.id.tiktok);
        RoundImage(tiktok, getResources().getDrawable(R.drawable.tiktok_logo), 50, 50);

        instagram = root.findViewById(R.id.instagram);
        RoundImage(instagram, getResources().getDrawable(R.drawable.realinstagramlogo), 300, 300);

        facebook = root.findViewById(R.id.facebook);
        RoundImage(facebook, getResources().getDrawable(R.drawable.realfacebooklogo), 200, 200);

        youtube = root.findViewById(R.id.youtube);
        RoundImage(youtube, getResources().getDrawable(R.drawable.realyoutube), 500, 500);

        //Changes the toolbar name back to home after going through any of the tours
        if (getArguments() != null) {
            Log.d("Inside get arugment not null", "");
            Fragment callingFragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            Log.d("callingFragment class", callingFragment.getClass().getSimpleName());
            if (callingFragment instanceof HomeFragment) {
                String message = getArguments().getString("Finished");
                //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Home");
            }
        }

        //asking user for location permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted do nothing
        } else {
            // Permission is not granted, request it from the user
            Log.d("something wrong with location access", "here");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        Log.d("&&&&&&&&&", "inside of homefragment 2");

        //Listeners for all of the tour buttons, each one will open the correct tour fragment and open a dialog box making sure the user is ready to take the tour

        tourOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Welcome to the Academic Tour! This tour will navigate you around the campus to see all of the academic buildings we have to offer and show you important information about the buildings as well as their history." +
                        "\n\nWe recommend starting this tour at the Ariens Family Welcome Center located at 316 3rd Street, De Pere, WI.\n\nAre you ready to begin the tour?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TourOneFragment tourOne = new TourOneFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourOne);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();                    }
                });

                builder.setNegativeButton("No", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        tourTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Welcome to the Living Areas Tour! This tour will navigate you around the campus to see most of the living areas we have to offer and show you important information about the buildings as well as their history." +
                        "\n\nWe recommend starting this tour at the Pennings Activity Center located at 290 Reid St, De Pere, WI.\n\nAre you ready to begin the tour?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TourTwoFragment tourTwo = new TourTwoFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourTwo);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();                }
                });

                builder.setNegativeButton("No", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        tourThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Welcome to the Areas of Interest Tour! This tour will navigate you around the campus to see all of the interesting areas we have located on this campus and important information about the buildings as well as their history." +
                        "\n\nWe recommend starting this tour at the Ariens Family Welcome Center located at 316 3rd Street, De Pere, WI.\n\nAre you ready to begin the tour?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TourThreeFragment tourThree = new TourThreeFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, tourThree);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();             }
                });

                builder.setNegativeButton("No", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //listeners that handle and open all the correct links for st norberts social media
        tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/@stnorbert"));
                startActivity(intent);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/stnorbert/"));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/stnorbert/"));
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@stnorbertcollege"));
                startActivity(intent);
            }
        });

        return root;
    }

    public void RoundImage(ImageView imageView, Drawable drawable, int rx, int ry){

        //Rounds the social media links

        Bitmap mMap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap mRounded = Bitmap.createBitmap(mMap.getWidth(), mMap.getHeight(), mMap.getConfig());
        Canvas canvas = new Canvas(mRounded);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(mMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mMap.getWidth(), mMap.getHeight())), rx, ry, paint); // Round Image Corner 100 100 100 100
        imageView.setImageBitmap(mRounded);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
